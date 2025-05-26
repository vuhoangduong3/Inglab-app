package unicorns.backend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.CreateUserRequest;
import unicorns.backend.dto.request.UpdateProfileRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateUserResponse;
import unicorns.backend.dto.response.UpdateProfileResponse;
import unicorns.backend.entity.User;
import unicorns.backend.repository.UserRepository;
import unicorns.backend.service.UserService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public BaseResponse<CreateUserResponse> createUser(BaseRequest<CreateUserRequest> request) {
        CreateUserRequest createUserRequest = request.getWsRequest();
        Optional<User> userOptional = userRepository.findByUsername(createUserRequest.getUsername());
        if (userOptional.isPresent()) {
            User userExists = userOptional.get();
            if (Const.STATUS.DEACTIVATE.equals(userExists.getStatus())) {
                throw new ApplicationException(ApplicationCode.USER_DEACTIVATE);
            }
            throw new ApplicationException(ApplicationCode.USER_EXITS);
        }
        User user = new User();
        BeanUtils.copyProperties(createUserRequest, user);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setStatus(Const.STATUS.ACTIVE);
        userRepository.save(user);
        BaseResponse baseResponse = BaseResponse.success();
        CreateUserResponse createUserResponse = new CreateUserResponse();
        BeanUtils.copyProperties(user, createUserResponse);
        baseResponse.setWsResponse(createUserResponse);
        return baseResponse;
    }

    @Override
    public BaseResponse<CreateUserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<CreateUserResponse> createUserResponseList = new ArrayList<>();
        userList.forEach(
                i -> {
                    CreateUserResponse createUserResponse = new CreateUserResponse();
                    BeanUtils.copyProperties(i, createUserResponse);
                    createUserResponseList.add(createUserResponse);
                }
        );
        BaseResponse baseResponse = BaseResponse.success();
        baseResponse.setWsResponse(createUserResponseList);
        return baseResponse;
    }

    @Override
    public BaseResponse<UpdateProfileResponse> updateProfile(BaseRequest<UpdateProfileRequest> updateprofilerequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UpdateProfileRequest request = updateprofilerequest.getWsRequest();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (request.getUsername() != null && !request.getUsername().equals(username)) {
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        if (userOptional.isEmpty()) {
            throw new ApplicationException(ApplicationCode.USER_NOT_FOUND);
        }
        User user = userOptional.get();
        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userRepository.save(user);
        BaseResponse<UpdateProfileResponse> response = new BaseResponse<>(ApplicationCode.SUCCESS);
        response.setWsResponse(UpdateProfileResponse.from(user));
        return response;
    }
}

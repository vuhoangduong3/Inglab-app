package unicorns.backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.CreateUserRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateUserResponse;
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
        Optional<User> userOptional = userRepository.findByUsername(createUserRequest.  getUsername());
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
}

package unicorns.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.CreateUserRequest;
import unicorns.backend.dto.request.GetStudentsByNameRequest;
import unicorns.backend.dto.request.UpdateProfileRequest;
import unicorns.backend.dto.response.*;

import java.util.List;

public interface UserService {
    BaseResponse<CreateUserResponse> createUser(@RequestHeader("Authorization") String AuthHeader,
                                                BaseRequest<CreateUserRequest> request);
    BaseResponse<CreateUserResponse> getAllUser(@RequestHeader("Authorization") String AuthHeader);
    BaseResponse<UpdateProfileResponse> updateProfile(BaseRequest<UpdateProfileRequest> request);
    BaseResponse<GetProfileResponse> getProfile(@RequestHeader("Authorization") String AuthHeader);
    BaseResponse<List<GetStudentsByNameResponse>> getStudentsByName(BaseRequest<GetStudentsByNameRequest> request);
}

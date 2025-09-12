package unicorns.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.CreateUserRequest;
import unicorns.backend.dto.request.UpdateProfileRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateUserResponse;
import unicorns.backend.dto.response.GetProfileResponse;
import unicorns.backend.dto.response.UpdateProfileResponse;

public interface UserService {
    BaseResponse<CreateUserResponse> createUser(@RequestHeader("Authorization") String AuthHeader,
                                                BaseRequest<CreateUserRequest> request);
    BaseResponse<CreateUserResponse> getAllUser(@RequestHeader("Authorization") String AuthHeader);
    BaseResponse<UpdateProfileResponse> updateProfile(BaseRequest<UpdateProfileRequest> request);
    BaseResponse<GetProfileResponse> getProfile(@RequestHeader("Authorization") String AuthHeader);
}

    package unicorns.backend.service;
    import org.springframework.web.bind.annotation.RequestHeader;
    import unicorns.backend.dto.request.*;
    import unicorns.backend.dto.response.BaseResponse;
    import unicorns.backend.dto.response.GetCurrentUserResponse;
    import unicorns.backend.dto.response.LoginResponse;


    public interface AuthService {

        BaseResponse<LoginResponse> login(BaseRequest<LoginRequest> loginRequest);
        BaseResponse<LoginResponse> refreshToken(BaseRequest<RefreshTokenRequest> RefreshTokenRequest);
        BaseResponse<?> logout(BaseRequest<LogoutRequest> logoutRequest);
        BaseResponse<GetCurrentUserResponse> getCurrentUser(@RequestHeader("Authorization")  String AuthHeader);
    }

    package unicorns.backend.service;
    import org.springframework.web.bind.annotation.RequestHeader;
    import unicorns.backend.dto.request.*;
    import unicorns.backend.dto.response.BaseResponse;
    import unicorns.backend.dto.response.GetCurrentUserResponse;
    import unicorns.backend.dto.response.LoginResponse;
    import unicorns.backend.util.ApplicationException;


    public interface AuthService {

        BaseResponse<LoginResponse> login(BaseRequest<LoginRequest> loginRequest);
        BaseResponse<LoginResponse> refreshToken(BaseRequest<RefreshTokenRequest> RefreshTokenRequest);
        BaseResponse<?> logout(@RequestHeader("Authorization") String AuthHeader,
                               BaseRequest<LogoutRequest> logoutRequest) throws ApplicationException;

        BaseResponse<GetCurrentUserResponse> getCurrentUser(@RequestHeader("Authorization")  String AuthHeader);
    }

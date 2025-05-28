    package unicorns.backend.service;
    import unicorns.backend.dto.request.BaseRequest;
    import unicorns.backend.dto.request.LoginRequest;
    import unicorns.backend.dto.request.LogoutRequest;
    import unicorns.backend.dto.response.BaseResponse;
    import unicorns.backend.dto.response.LoginResponse;


    public interface AuthService {

        BaseResponse<LoginResponse> login(BaseRequest<LoginRequest> loginRequest);
        BaseResponse<?> logout(BaseRequest<LogoutRequest> logoutRequest);
    }

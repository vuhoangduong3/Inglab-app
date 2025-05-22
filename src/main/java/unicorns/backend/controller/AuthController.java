package unicorns.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.LoginRequest;
import unicorns.backend.dto.request.LogoutRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.LoginResponse;
import unicorns.backend.service.AuthService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.Const;

@RestController
@RequestMapping(Const.PREFIX_AUTH_V1)
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody BaseRequest<LoginRequest> loginRequest) throws ApplicationException {
        BaseResponse<LoginResponse> response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@RequestBody BaseRequest<LogoutRequest> logoutRequest, HttpServletRequest request) throws ApplicationException {
        String authHeader = request.getHeader("Authorization");
        logoutRequest.getWsRequest().setAuthHeader(authHeader);
        authService.logout(logoutRequest);
        return ResponseEntity.ok(new BaseResponse<>(ApplicationCode.SUCCESS));
    }
}
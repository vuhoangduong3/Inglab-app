package unicorns.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.LoginRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.LoginResponse;
import unicorns.backend.entity.TokenBlacklist;
import unicorns.backend.repository.TokenBlacklistRepository;
import unicorns.backend.service.AuthService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final TokenBlacklistRepository blacklistRepository;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService,
                          JwtUtil jwtUtil,
                          TokenBlacklistRepository blacklistRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.blacklistRepository = blacklistRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws ApplicationException {
        LoginResponse response = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(HttpServletRequest request) throws ApplicationException {
        String authHeader = request.getHeader("Authorization");
        authService.logout(authHeader);
        return ResponseEntity.ok(new BaseResponse<>(ApplicationCode.SUCCESS, "Logout Succesfully"));
    }
}
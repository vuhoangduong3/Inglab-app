package unicorns.backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.LoginRequest;
import unicorns.backend.dto.request.LogoutRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.LoginResponse;
import unicorns.backend.entity.TokenBlacklist;
import unicorns.backend.entity.User;
import unicorns.backend.repository.TokenBlacklistRepository;
import unicorns.backend.repository.UserRepository;
import unicorns.backend.service.AuthService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.JwtUtil;

import java.util.Objects;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public BaseResponse<LoginResponse> login(BaseRequest<LoginRequest> loginRequest) throws ApplicationException {
        LoginRequest request = loginRequest.getWsRequest();
        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new ApplicationException(ApplicationCode.INPUT_INVALID);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException(ApplicationCode.INPUT_INVALID));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ApplicationCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.generateToken(username);
        LoginResponse loginResponse = new LoginResponse(token);
        BaseResponse<LoginResponse> response = new BaseResponse<>(ApplicationCode.SUCCESS);
        response.setWsResponse(loginResponse);

        return response;
    }
    @Override
    public BaseResponse<?> logout(BaseRequest<LogoutRequest> logoutRequest) throws ApplicationException {

        LogoutRequest request = logoutRequest.getWsRequest();
        String username = request.getUsername();
        String authHeader = request.getAuthHeader();

        if (username == null || username.isEmpty()||authHeader == null || authHeader.isEmpty()) {
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        String token = jwtUtil.extractToken(authHeader);
        if (token == null || token.isEmpty()) {
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        if(!Objects.equals(username, jwtUtil.getUsernameFromToken(token))){
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(jwtUtil.getExpiryDate(token));
        tokenBlacklistRepository.save(blacklistedToken);

        BaseResponse<?> response = new BaseResponse<>(ApplicationCode.SUCCESS);
        response.setWsResponse(null);
        return response;
    }
}


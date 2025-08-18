package unicorns.backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import unicorns.backend.dto.request.*;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.GetCurrentUserResponse;
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
import java.util.Optional;

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

        String accessToken = jwtUtil.generateAccessToken(username, user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(username, user.getRole());

        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken, user.getRole());
        BaseResponse<LoginResponse> response = new BaseResponse<>(ApplicationCode.SUCCESS);
        response.setWsResponse(loginResponse);

        return response;
    }

    @Override
    public BaseResponse<LoginResponse> refreshToken(BaseRequest<RefreshTokenRequest> RefreshTokenRequest)  {
        String refreshToken = RefreshTokenRequest.getWsRequest().getRefreshToken();
            if (refreshToken == null || refreshToken.isBlank()) {
                throw new ApplicationException(ApplicationCode.INPUT_INVALID);
            }

            if (!jwtUtil.validateToken(refreshToken)) {
                throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
            }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String role = jwtUtil.extractRole(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(username, role);
        String newRefreshToken = jwtUtil.generateRefreshToken(username,role);

        LoginResponse loginResponse = new LoginResponse(newAccessToken, newRefreshToken, role);
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

    @Override
    public BaseResponse<GetCurrentUserResponse> getCurrentUser(@RequestHeader("Authorization") String AuthHeader) throws ApplicationException {
        if(AuthHeader == null || AuthHeader.isBlank()){
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }
        String accessToken = jwtUtil.extractToken(AuthHeader);
        String username = jwtUtil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException(ApplicationCode.USER_NOT_FOUND));
       GetCurrentUserResponse getCurrentUserResponse = new GetCurrentUserResponse(user.getUsername(),user.getRole());
       BaseResponse<GetCurrentUserResponse> response = new BaseResponse<>(ApplicationCode.SUCCESS);
       response.setWsResponse(getCurrentUserResponse);
       return response;
    }
}




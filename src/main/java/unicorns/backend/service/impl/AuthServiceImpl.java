package unicorns.backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unicorns.backend.dto.response.LoginResponse;
import unicorns.backend.entity.TokenBlacklist;
import unicorns.backend.entity.User;
import unicorns.backend.repository.TokenBlacklistRepository;
import unicorns.backend.repository.UserRepository;
import unicorns.backend.service.AuthService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.JwtUtil;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public LoginResponse login(String username, String password) throws ApplicationException {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new ApplicationException(ApplicationCode.INPUT_INVALID);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException(ApplicationCode.INPUT_INVALID));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ApplicationCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.generateToken(username);
        return new LoginResponse(token);
    }
    @Override
    public void logout(String authHeader) throws ApplicationException {
        if (authHeader == null || authHeader.isEmpty()) {
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        String token = jwtUtil.extractToken(authHeader);
        if (token == null || token.isEmpty()) {
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(jwtUtil.getExpiryDate(token));
        tokenBlacklistRepository.save(blacklistedToken);
    }
}


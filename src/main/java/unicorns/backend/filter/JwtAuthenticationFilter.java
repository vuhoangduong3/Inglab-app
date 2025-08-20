package unicorns.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.repository.TokenBlacklistRepository;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.JwtUtil;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TokenBlacklistRepository tokenBlacklistRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, TokenBlacklistRepository tokenBlacklistRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    private void handleError(HttpServletResponse response) throws IOException {
        BaseResponse<Object> errorResponse = new BaseResponse<>();
        errorResponse.setCode(Integer.valueOf(String.valueOf(ApplicationCode.INVALID_TOKEN)));
        errorResponse.setMessage(ApplicationCode.getMessage(ApplicationCode.INVALID_TOKEN));
        errorResponse.setWsResponse(null);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                if (token == null || token.trim().isEmpty()) {
                    handleError(response);
                    return;
                }
                if (tokenBlacklistRepository.existsByToken(token)){
                    handleError(response);
                    return;
                }
                if (!jwtUtil.validateToken(token)){
                    handleError(response);
                    return;
                }
                username = jwtUtil.getUsernameFromToken(token);
            } catch (ExpiredJwtException | MalformedJwtException e) {
                handleError(response);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new User(username, "", new ArrayList<>());
            try {
                if (jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    handleError(response);
                    return;
                }
            } catch (Exception e) {
                handleError(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
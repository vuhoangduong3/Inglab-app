package unicorns.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.LoginRequest;
import unicorns.backend.dto.request.LogoutRequest;
import unicorns.backend.dto.request.RefreshTokenRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.GetCurrentUserResponse;
import unicorns.backend.dto.response.LoginResponse;
import unicorns.backend.service.AuthService;
import unicorns.backend.util.ApplicationCode;
import unicorns.backend.util.ApplicationException;
import unicorns.backend.util.Const;

import java.util.Map;

@RestController
@RequestMapping(Const.PREFIX_AUTH_V1)
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "User login", description = "Logs in a user with provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody BaseRequest<LoginRequest> loginRequest) throws ApplicationException {
        BaseResponse<LoginResponse> response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Refresh access token", description = "Generates a new access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/refresh-token")
    public BaseResponse<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ApplicationException {
        BaseRequest<RefreshTokenRequest> baseRequest = new BaseRequest<>();
        baseRequest.setWsRequest(request);
        return authService.refreshToken(baseRequest);
    }


    @Operation(summary = "User logout", description = "Logs out the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@RequestBody BaseRequest<LogoutRequest> logoutRequest, HttpServletRequest request) throws ApplicationException {
        String authHeader = request.getHeader("Authorization");
        logoutRequest.getWsRequest().setAuthHeader(authHeader);
        authService.logout(logoutRequest);
        return ResponseEntity.ok(new BaseResponse<>(ApplicationCode.SUCCESS));
    }

    @Operation(summary = "Get current user", description = "Retrieves the currently authenticated user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired access token",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<GetCurrentUserResponse>> getCurrentUser(@RequestHeader("Authorization") String AuthHeader) throws ApplicationException{
            BaseResponse<GetCurrentUserResponse> response = authService.getCurrentUser(AuthHeader);
            return ResponseEntity.ok(response);
    }
}
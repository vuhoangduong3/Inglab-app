package unicorns.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import unicorns.backend.dto.request.BaseRequest;
import unicorns.backend.dto.request.CreateUserRequest;
import unicorns.backend.dto.request.UpdateProfileRequest;
import unicorns.backend.dto.response.BaseResponse;
import unicorns.backend.dto.response.CreateUserResponse;
import unicorns.backend.dto.response.UpdateProfileResponse;
import unicorns.backend.service.UserService;
import unicorns.backend.util.Const;

@RestController
@RequestMapping(Const.PREFIX_USER_V1)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    @Operation(summary = "get all user", description = "Get all user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully")
    })
    @GetMapping(value = "getAll")
    public BaseResponse<CreateUserResponse> getAllUsers(@RequestHeader("Authorization") String AuthHeader) {
        return userService.getAllUser(AuthHeader);
    }

    @Operation(summary = "Create new user", description = "Creates a new user using the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping(value = "createUser")
    public BaseResponse<CreateUserResponse> createUser(@RequestHeader("Authorization") String AuthHeader,
            @Valid @RequestBody BaseRequest<CreateUserRequest> request) {
        return userService.createUser(AuthHeader,request);
    }

    @Operation(summary = "Update user profile", description = "Updates profile of the currently logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PutMapping("me/updateProfile")
    public BaseResponse<UpdateProfileResponse> updateProfile(@Valid @RequestBody BaseRequest<UpdateProfileRequest> request) {
        return userService.updateProfile(request);
    }


}


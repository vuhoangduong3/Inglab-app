package unicorns.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for create User")
public class CreateUserRequest {

    @Schema(description = "Username can not blank")
    @NotBlank(message = "Username can not blank")
    String username;

    @Schema(description = "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special character")
    @NotBlank(message = "Password can not blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special character"
    )
    String password;
    @Schema(description = "Name can not blank")
    @NotBlank(message = "Name can not blank")
    String name;

    @Schema(description = "Email can not blank")
    @NotBlank(message = "Email can not blank")
    String email;

    @Schema(description = "Role can not blank")
    @NotBlank(message = "Role can not blank")
    String role;

    @Schema(description = "Gender can not blank")
    @NotBlank(message = "Gender can not blank")
    String gender;
}

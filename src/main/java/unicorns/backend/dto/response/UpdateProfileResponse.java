package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import unicorns.backend.entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileResponse {
    String name;
    String email;
    LocalDateTime dateOfBirth;


    public static UpdateProfileResponse from(User user) {
        return new UpdateProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getDateOfBirth()
        );
    };
}

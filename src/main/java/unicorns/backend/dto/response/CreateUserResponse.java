package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserResponse {
    Long id;
    String username;
    String name;
    String email;
    String role;
    LocalDateTime createdAt;
}

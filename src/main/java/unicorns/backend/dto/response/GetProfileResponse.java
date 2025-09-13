package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetProfileResponse {
    String username;
    String email;
    String name;
    LocalDateTime dateOfBirth;
    Long id;
    String role;
    String gender;
    LocalDateTime createdAt;

}

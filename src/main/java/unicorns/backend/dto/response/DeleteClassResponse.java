package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteClassResponse {
    private Long id;
    private String name;
    private String description;
}

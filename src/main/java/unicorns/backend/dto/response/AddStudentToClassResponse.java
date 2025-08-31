package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddStudentToClassResponse {
    Long classId;
    String className;
    int addedCount;
}

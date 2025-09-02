package unicorns.backend.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetMemberInClassResponse {
    Long id;
    String name;
    String description;
    List<StudentInfo> students;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StudentInfo {
        Long id;
        String fullName;
    }
}


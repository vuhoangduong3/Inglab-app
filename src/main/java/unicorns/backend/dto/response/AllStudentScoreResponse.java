package unicorns.backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AllStudentScoreResponse {
    private Long classId;
    private Long exerciseId;
    private List<StudentScoreResponse> answers;
}

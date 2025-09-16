package unicorns.backend.dto.response;

import lombok.Data;

@Data
public class StudentScoreResponse {
    private long id;
    private Long studentId;
    private String studentName;
    private Integer correctAnswer;
}

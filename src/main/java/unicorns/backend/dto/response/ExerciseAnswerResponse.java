package unicorns.backend.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ExerciseAnswerResponse {
    private Long studentId;
    private String studentName;
    private Long exerciseId;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private List<StudentAnswerResponse> answers;
}

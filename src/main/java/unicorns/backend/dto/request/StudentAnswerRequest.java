package unicorns.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request DTO for submitting a student's answer")
public class StudentAnswerRequest {

    @Schema(description = "ID of the student submitting the answer", example = "1")
    Long studentId;

    @Schema(description = "ID of the quiz the question belongs to", example = "10")
    Long quizId;

    @Schema(description = "ID of the question being answered", example = "5")
    Long questionId;

    @Schema(description = "The student's selected answer", example = "A")
    String studentAnswer;
}

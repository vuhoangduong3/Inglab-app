package unicorns.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Response DTO representing a student's answer")
public class StudentAnswerResponse {

    @Schema(description = "ID of the StudentAnswer record", example = "100")
    Long id;

    @Schema(description = "ID of the student", example = "1")
    Long studentId;

    @Schema(description = "Name of the student", example = "Nguyen Van A")
    String studentName;

    @Schema(description = "ID of the quiz", example = "10")
    Long quizId;

    @Schema(description = "ID of the question", example = "5")
    Long questionId;

    @Schema(description = "The text of the question", example = "What is 2+2?")
    String questionText;

    @Schema(description = "The student's answer", example = "A")
    String studentAnswer;

    @Schema(description = "Whether the student's answer is correct", example = "true")
    Boolean isCorrect;
}

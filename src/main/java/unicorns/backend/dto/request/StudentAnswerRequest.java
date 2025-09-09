package unicorns.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request DTO for submitting a student's answer")
public class StudentAnswerRequest {
    @NotNull
    @Schema(description = "ID of the student submitting the answer", example = "1")
    Long studentId;

    @Schema(description = "ID of the exercise the question belongs to", example = "10")
    Long exerciseId;

    @Schema(description = "ID of the question being answered", example = "5")
    Long quizQuestionId;

    @Schema(description = "Id of the student's selected answer", example = "6")
    Long quizOptionId;
}

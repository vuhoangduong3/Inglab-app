package unicorns.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ExerciseCreateRequest {
    @NotBlank
    private String title;

    @Size(max = 500)
    private String description;

    private Integer durationMinutes;

    @NotEmpty
    private List<QuestionCreateRequest> questions;

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<QuestionCreateRequest> getQuestions() { return questions; }
    public void setQuestions(List<QuestionCreateRequest> questions) { this.questions = questions; }
}

package unicorns.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public class OptionCreateRequest {
    @NotBlank
    private String text;
    private boolean correct;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
}

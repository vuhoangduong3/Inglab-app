package unicorns.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class QuestionCreateRequest {
    @NotBlank
    private String text;

    @NotEmpty
    private List<OptionCreateRequest> options;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<OptionCreateRequest> getOptions() { return options; }
    public void setOptions(List<OptionCreateRequest> options) { this.options = options; }
}

package unicorns.backend.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationException extends RuntimeException {
    private int code;
    private String message;
    private String language;

    public ApplicationException(int code){
        this.code = code;
    }
    @Override
    public String getMessage() {
        if(!StringUtils.hasText(message)){
            return ApplicationCode.getMessage(code);
        }
        return message;
    }

    public String getMessage(String language) {
        if(!StringUtils.hasText(message)){
            return ApplicationCode.getMessage(code, language);
        }
        return message;
    }

}

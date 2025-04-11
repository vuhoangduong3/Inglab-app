package unicorns.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.ApplicationCode;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Base response wrapper")
public class BaseResponse<T> {
    @Schema(description = "Response code")
    Integer code;
    @Schema(description = "Response message")
    String message;
    @Schema(description = "Response payload")
    T wsResponse;

    public BaseResponse() {

    }

    public BaseResponse(Integer code) {
        this.code = code;
        this.message = ApplicationCode.getMessage(code);
    }

    public BaseResponse(Integer errorCode, String message) {
        this.code = errorCode;
        this.message = message;
    }

    public static BaseResponse success() {
        return new BaseResponse(ApplicationCode.SUCCESS);
    }
}

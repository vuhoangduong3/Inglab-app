package unicorns.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Base request wrapper")
public class BaseRequest<T> {

    @Schema(description = "baseInfo request")
    BaseInfo baseInfo;

    @Schema(description = "Request Data")
    @Valid
    T wsRequest;
}

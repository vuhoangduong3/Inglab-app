package unicorns.backend.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.Const;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseInfo {
    String appCode;
    String language = Const.LANGUAGE_DEFAULT;
    String deviceId;
    String deviceName;
    String appVersion;
    String deviceOsVersion;
}

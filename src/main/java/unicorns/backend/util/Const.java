package unicorns.backend.util;

public class Const {
    public static final String LANGUAGE_DEFAULT = "en";
    public static final String ISO_8859 = "ISO-8859-1";

    public static class STATUS {
        public static final Integer ACTIVE = 1;
        public static final Integer DEACTIVATE = 0;
    }

    public static final String FORMAT_NAME_V1 = "/v1/api/";
    public static final String PREFIX_UNICORNS_SERVICE_V1 = FORMAT_NAME_V1;
    public static final String PREFIX_USER_V1 = PREFIX_UNICORNS_SERVICE_V1 + "user/";
    public static final String PREFIX_AUTH_V1 = PREFIX_UNICORNS_SERVICE_V1 + "auth/";
    public static final String PREFIX_STUDENTANSWER_V1 = PREFIX_UNICORNS_SERVICE_V1 + "studentanswer/";
}

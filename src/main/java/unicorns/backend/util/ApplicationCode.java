package unicorns.backend.util;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ApplicationCode {

    public static final int SUCCESS = 0;
    public static final int UNKNOWN_ERROR = 1;
    public static final int USER_DEACTIVATE = 2;
    public static final int USER_EXIST = 3;
    public static final int INPUT_INVALID = 4;
    public static final int INVALID_PASSWORD = 5;
    public static final int INVALID_TOKEN = 6;
    public static final int USER_NOT_FOUND = 7;
    public static final int INVALID_ROLE = 8;

    private static final Map<Integer, String> msg = new HashMap<>();

    static {
        msg.put(SUCCESS, "SUCCESS");
        msg.put(UNKNOWN_ERROR, "UNKNOWN_ERROR");
        msg.put(USER_DEACTIVATE, "USER_DEACTIVATE");
        msg.put(USER_EXIST, "USER_EXIST");
        msg.put(INPUT_INVALID, "INPUT_INVALID");
        msg.put(INVALID_PASSWORD, "INVALID_PASSWORD");
        msg.put(INVALID_TOKEN, "INVALID_TOKEN");
        msg.put(USER_NOT_FOUND, "USER_NOT_FOUND");
        msg.put(INVALID_ROLE, "INVALID_ROLE");
    }

    public static String getMessage(int code, String language) {
        if (StringUtils.isEmpty(language)) {
            language = Const.LANGUAGE_DEFAULT;
        }

        return getMsg(code, language);
    }


    public static String getMessage(int code) {
        return getMsg(code, Const.LANGUAGE_DEFAULT);
    }

    private static String getMsg(int code, String language) {
        if (msg.containsKey(code)) {
            String message = getProperty(code, language);
            try {
                String msg;
                msg = new String(message.getBytes(Const.ISO_8859), StandardCharsets.UTF_8);
                return msg;
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }

        return "";
    }

    public static String getProperty(int code, String language) {
        return getBundle(language).getString(msg.get(code));

    }

    public static ResourceBundle getBundle(String language) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + language);
        return bundle;
    }
}

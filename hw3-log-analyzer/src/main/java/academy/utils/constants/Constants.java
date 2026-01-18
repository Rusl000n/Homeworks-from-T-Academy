package academy.utils.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final int SUCCESS_CODE = 200;

    public static final String REGEX_HGIX_TYPE =
            "^(\\S+)\\s-\\s([^\\s]+)\\s\\[([^\\]]+)\\]\\s\"([^\"]+)\"\\s(\\d{3})\\s(\\d+)\\s\"([^\"]*)\"\\s\"([^\"]*)\"$";

    public static final String REGEX_TIME = "d/MMM/yyyy:HH:mm:ss Z";

    public static final String REGEX_REQUESTS = "([A-Z]+)\\s+(\\S+)\\s+(.+)";

    public static final String HTTP_REGEX = "^https?://.*";

    public static final double TO_95_PERCENTAGE = 0.95;

    public static final String REGEX_FILE_TYPE = ".*\\*.*\\.(log|txt)";

    public static final String REGEX_FILE = ".*\\.(log|txt)(\\?.*)?$";

    public static final String REGEX_SMALL_FILE_TYPE = ".*\\.(log|txt)$";

    public static final String EXT_JSON = ".json";

    public static final String EXT_MARKDOWN = ".md";

    public static final String EXT_ASCIIDOC = ".adoc";

    public static final String REGEX_JSON = "^(?i)(json)$";

    public static final String REGEX_MARKDOWN = "^(?i)(markdown|md)$";

    public static final String REGEX_ASCIIDOC = "^(?i)(adoc|asciidoc)$";

    public static final String REGEX_FILE_NAME = ".*\\.(log|txt)$";

    public static final int EXIT_FAILURE = 1;

    public static final int EXIT_MISUSE = 2;
}

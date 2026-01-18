package academy.dto.parse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NgixConstants {
    public static final int IP = 1;
    public static final int USERNAME = 2;
    public static final int TIME = 3;
    public static final int REQUEST = 4;
    public static final int STATUS_CODE = 5;
    public static final int BYTES_SIZE = 6;
    public static final int URL_REFERER = 7;
    public static final int USER_AGENT = 8;

    public static final int TYPE_REQUEST = 1;
    public static final int RESOURCES = 2;
    public static final int URL = 3;
}

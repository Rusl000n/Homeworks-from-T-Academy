package academy.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final int LEFT_BOARDER_INT = -1000;
    public static final int RIGHT_BOARDER_INT = 1000;
    public static final double LEFT_BOARDER_DOUBLE = -3.0d;
    public static final double RIGHT_BOARDER_DOUBLE = 3.0d;
    public static final long WHITE = 0xFFFFFFFF;

    public static final double CONVERT_DOUBLE = 1000.0d;
}

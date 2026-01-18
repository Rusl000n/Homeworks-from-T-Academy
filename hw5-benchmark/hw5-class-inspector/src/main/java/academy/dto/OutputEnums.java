package academy.dto;

public enum OutputEnums {
    TEXT,
    JSON;

    public static OutputEnums fromString(String text) {
        if (text == null) {
            return null;
        }
        for (OutputEnums format : OutputEnums.values()) {
            if (format.name().equals(text.toUpperCase().trim())) {
                return format;
            }
        }
        return null;
    }

    public static boolean isValid(String format) {
        return fromString(format) != null;
    }
}
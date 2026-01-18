package academy.io;

import academy.dto.OutputEnums;

public class WriterFactory {
    
    public static Writer creoteWriter(String format) {
        OutputEnums outputEnums = OutputEnums.fromString(format);
        if (outputEnums == null) {
            throw new IllegalArgumentException(
                String.format("Unsupported format: %s. Supported: %s",
                    format, String.join(", ", 
                        outputEnums.TEXT.getValue(), 
                        outputEnums.JSON.getValue()))
            );
        }
        return switch (outputEnums) {
            case TEXT -> new TextWriter();
            case JSON -> new JsonWriter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
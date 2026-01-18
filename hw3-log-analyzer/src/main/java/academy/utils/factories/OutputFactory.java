package academy.utils.factories;

import static academy.utils.constants.Constants.EXT_ASCIIDOC;
import static academy.utils.constants.Constants.EXT_JSON;
import static academy.utils.constants.Constants.EXT_MARKDOWN;
import static academy.utils.constants.Constants.REGEX_ASCIIDOC;
import static academy.utils.constants.Constants.REGEX_JSON;
import static academy.utils.constants.Constants.REGEX_MARKDOWN;

import academy.io.AdocModule;
import academy.io.JacksonModule;
import academy.io.MarkdownModule;
import academy.io.OutputInterface;

public class OutputFactory {

    public OutputInterface createOutputModule(String format) {
        if (format == null || format.trim().isEmpty()) {
            throw new IllegalArgumentException("Format cannot be null or empty");
        }
        String normalizedFormat = format.trim().toLowerCase();
        if (normalizedFormat.matches(REGEX_JSON)) {
            return new JacksonModule();
        }
        if (normalizedFormat.matches(REGEX_MARKDOWN)) {
            return new MarkdownModule();
        }
        if (normalizedFormat.matches(REGEX_ASCIIDOC)) {
            return new AdocModule();
        }
        throw new IllegalArgumentException(
                String.format("Unsupported format: '%s'. Use: json, markdown, adoc", format));
    }

    public String getFileExtension(String format) {
        if (format == null || format.trim().isEmpty()) {
            throw new IllegalArgumentException("Format cannot be null or empty");
        }
        String normalizedFormat = format.trim().toLowerCase();
        if (normalizedFormat.matches(REGEX_JSON)) {
            return EXT_JSON;
        }
        if (normalizedFormat.matches(REGEX_MARKDOWN)) {
            return EXT_MARKDOWN;
        }
        if (normalizedFormat.matches(REGEX_ASCIIDOC)) {
            return EXT_ASCIIDOC;
        }
        throw new IllegalArgumentException(
                String.format("Unsupported format: '%s'. Use: json, markdown, adoc", format));
    }

    public boolean isValidExtension(String fileName, String format) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }
        try {
            String expectedExtension = getFileExtension(format);
            String lowerFileName = fileName.toLowerCase();
            String lowerExtension = expectedExtension.toLowerCase();
            return lowerFileName.endsWith(lowerExtension);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

package academy.utils;

import static academy.utils.constants.Constants.REGEX_FILE_NAME;

import academy.utils.factories.OutputFactory;
import java.nio.file.Files;
import java.nio.file.Path;

public class Validation {
    public void validateOutputFile(String outputPath, String format) {
        Path path = Path.of(outputPath);
        if (Files.exists(path)) {
            throw new IllegalArgumentException("Output file already exists");
        }
        Path parentDir = path.getParent();
        if (parentDir != null && !Files.isDirectory(parentDir)) {
            throw new IllegalArgumentException("Parent directory does not exist");
        }
        if (parentDir != null && !Files.isWritable(parentDir)) {
            throw new IllegalArgumentException("Directory is not writable");
        }
        OutputFactory outputFactory = new OutputFactory();
        Path fileNamePath = path.getFileName();
        if (fileNamePath == null) {
            throw new IllegalArgumentException("Output path must contain a file name");
        }
        String fileName = fileNamePath.toString();
        if (!outputFactory.isValidExtension(fileName, format)) {
            throw new IllegalArgumentException("Unexpected file extension");
        }
    }

    public void validateInputFiles(String[] paths) {
        for (String path : paths) {
            if (path.contains("*")) {
                continue;
            }

            Path filePath = Path.of(path);
            if (!Files.exists(filePath)) {
                throw new IllegalArgumentException("Input file does not exist: " + path);
            }
            Path fileNamePath = filePath.getFileName();
            if (fileNamePath == null) {
                throw new IllegalArgumentException("Input file path must contain a file name: " + path);
            }
            String fileName = fileNamePath.toString();
            if (!fileName.matches(REGEX_FILE_NAME)) {
                throw new IllegalArgumentException("Input file must have .log or .txt extension: " + path);
            }
        }
    }
}

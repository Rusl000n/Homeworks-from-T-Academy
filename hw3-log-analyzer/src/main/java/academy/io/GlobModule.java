package academy.io;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobModule extends FileModule implements InputInterface {
    private static final Logger logger = LogManager.getLogger(GlobModule.class);

    @Override
    public List<String> getFile(String input) throws IOException {
        Path inputPath = Path.of(input);
        String inputString = inputPath.toString();
        int lastSeparator = Math.max(inputString.lastIndexOf('/'), inputString.lastIndexOf('\\'));
        String pattern;
        Path directory;
        if (lastSeparator >= 0) {
            pattern = inputString.substring(lastSeparator + 1);
            directory = Path.of(inputString.substring(0, lastSeparator));
        } else {
            pattern = inputString;
            directory = Path.of(".");
        }
        if (!Files.exists(directory)) {
            logger.error("Directory does not exist: {}", directory);
            throw new IOException("Directory does not exist: " + directory);
        }
        if (!Files.isDirectory(directory)) {
            logger.error("Path is not a directory: {}", directory);
            throw new IOException("Path is not a directory: " + directory);
        }

        List<String> stringList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, pattern)) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    try {
                        List<String> lines = Files.readAllLines(path);
                        stringList.addAll(lines);
                        logger.debug("Read {} lines from {}", lines.size(), path);
                    } catch (IOException e) {
                        logger.error("Error reading file: {}", path, e);
                        throw new IOException("Error reading file: " + path, e);
                    }
                }
            }
        }
        if (stringList.isEmpty()) {
            logger.error("No files found matching pattern: {}", input);
            throw new IOException("No files found: " + input);
        }
        return stringList;
    }

    @Override
    public List<String> getFileNames(String input) throws IOException {
        Path inputPath = Path.of(input);
        String inputString = inputPath.toString();
        int lastSeparator = Math.max(inputString.lastIndexOf('/'), inputString.lastIndexOf('\\'));
        String pattern;
        Path directory;
        if (lastSeparator >= 0) {
            pattern = inputString.substring(lastSeparator + 1);
            directory = Path.of(inputString.substring(0, lastSeparator));
        } else {
            pattern = inputString;
            directory = Path.of(".");
        }
        logger.debug("Directory: {}, Pattern: {}", directory, pattern);
        if (!Files.exists(directory)) {
            logger.error("Directory does not exist: {}", directory);
            throw new IOException("Directory does not exist: " + directory);
        }
        if (!Files.isDirectory(directory)) {
            logger.error("Path is not a directory: {}", directory);
            throw new IOException("Path is not a directory: " + directory);
        }
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, pattern)) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    Path fileName = path.getFileName();
                    if (fileName != null) {
                        fileNames.add(fileName.toString());
                        logger.debug("Found file: {}", fileName);
                    }
                }
            }
        }
        if (fileNames.isEmpty()) {
            logger.error("No files found matching pattern: {}", input);
            throw new IOException("No files found: " + input);
        }
        List<String> sortedNames = fileNames.stream().distinct().sorted().collect(Collectors.toList());
        return sortedNames;
    }
}

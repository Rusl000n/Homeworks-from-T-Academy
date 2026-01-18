package academy.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileModule implements InputInterface {
    private static final Logger logger = LogManager.getLogger(FileModule.class);

    @Override
    public List<String> getFile(String input) throws IOException {
        List<String> lines = new ArrayList<>();
        Path path = Path.of(input);
        if (!Files.exists(path)) {
            logger.error("File does not exist: {}", input);
            throw new IOException("File does not exist: " + input);
        }
        if (!Files.isRegularFile(path)) {
            logger.error("Path is not a regular file: {}", input);
            throw new IOException("Path is not a regular file: " + input);
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            logger.error("Error reading file: {}", input, e);
            throw new IOException("Error processing input file: " + input, e);
        }
        return lines;
    }

    @Override
    public List<String> getFileNames(String input) throws IOException {
        Path path = Path.of(input);
        if (!Files.exists(path)) {
            logger.error("File does not exist: {}", input);
            throw new IOException("File does not exist: " + input);
        }
        if (!Files.isRegularFile(path)) {
            logger.error("Path is not a regular file: {}", input);
            throw new IOException("Path is not a regular file: " + input);
        }
        Path fileName = path.getFileName();
        if (fileName == null) {
            logger.error("Cannot extract file name from path: {}", input);
            throw new IOException("Cannot extract file name from path: " + input);
        }
        String fileNameStr = fileName.toString();
        return List.of(fileNameStr);
    }
}

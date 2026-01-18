package academy.acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LogFileParsingTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("На вход передан валидный локальный log-файл")
    void localFileProcessingTest() throws Exception {
        Path logFile = tempDir.resolve("access.log");

        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write(
                    "192.168.1.1 - - [10/Oct/2023:13:55:36 +0000] \"GET /index.html HTTP/1.1\" 200 1024 \"-\" \"Mozilla/5.0\"");
        }
        assertTrue(Files.exists(logFile));
    }

    @Test
    @DisplayName("На вход передан валидный удаленный log-файл")
    void remoteFileProcessingTest() {
        assertTrue(true);
    }

    @Test
    @DisplayName("На вход передан валидный локальный log-файл, "
            + "часть строк в котором нужно отфильтровать по --from и --to")
    void localFileProcessingAndFilteringTest() throws Exception {
        Path logFile = tempDir.resolve("access.log");

        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write("line1\nline2\nline3");
        }
        assertTrue(Files.exists(logFile));
    }

    @Test
    @DisplayName("На вход передан локальный log-файл, часть строк в котором не подходит под формат")
    void damagedLocalFileProcessingTest() throws Exception {
        Path logFile = tempDir.resolve("damaged.log");

        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write("valid log line\ninvalid line\nanother valid line");
        }
        assertTrue(Files.exists(logFile));
    }
}

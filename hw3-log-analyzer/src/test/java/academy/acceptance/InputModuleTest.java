package academy.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.io.FileModule;
import academy.io.GlobModule;
import academy.io.InputInterface;
import academy.utils.factories.InputModuleFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputModuleTest {

    @TempDir
    Path tempDir;

    @Test
    void inputModuleFactory_CreatesFileModule_ForLocalLogFile() throws Exception {
        InputModuleFactory factory = new InputModuleFactory();
        Path testFile = tempDir.resolve("test.log");
        Files.writeString(testFile, "Test log content");

        InputInterface module = factory.createModule(testFile.toString());

        assertNotNull(module);
        assertTrue(module instanceof FileModule);
    }

    @Test
    void inputModuleFactory_CreatesFileModule_ForLocalTxtFile() throws Exception {
        InputModuleFactory factory = new InputModuleFactory();
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "Test text content");

        InputInterface module = factory.createModule(testFile.toString());

        assertNotNull(module);
        assertTrue(module instanceof FileModule);
    }

    @Test
    void inputModuleFactory_CreatesGlobModule_ForPatternWithAsterisk() throws Exception {
        InputModuleFactory factory = new InputModuleFactory();
        Files.createFile(tempDir.resolve("access1.log"));
        Files.createFile(tempDir.resolve("access2.log"));

        InputInterface module = factory.createModule(tempDir.toString() + "/*.log");

        assertNotNull(module);
        assertTrue(module instanceof GlobModule);
    }

    @Test
    void inputModuleFactory_CreatesGlobModule_ForPatternWithAsteriskAndTxt() throws Exception {
        InputModuleFactory factory = new InputModuleFactory();
        Files.createFile(tempDir.resolve("access1.txt"));
        Files.createFile(tempDir.resolve("access2.txt"));

        InputInterface module = factory.createModule(tempDir.toString() + "/*.txt");

        assertNotNull(module);
        assertTrue(module instanceof GlobModule);
    }

    @Test
    void inputModuleFactory_CreatesFileModule_ForFileWithAnyExtension() throws Exception {
        InputModuleFactory factory = new InputModuleFactory();
        Path testFile = tempDir.resolve("test.pdf");
        Files.writeString(testFile, "Some content");

        InputInterface module = factory.createModule(testFile.toString());

        assertNotNull(module);
        assertTrue(module instanceof FileModule);
    }

    @Test
    void inputModuleFactory_CreatesGlobModule_ForPatternWithAnyExtension() throws Exception {
        InputModuleFactory factory = new InputModuleFactory();
        Files.createFile(tempDir.resolve("test1.pdf"));
        Files.createFile(tempDir.resolve("test2.pdf"));

        InputInterface module = factory.createModule(tempDir.toString() + "/*.pdf");

        assertNotNull(module);
        assertTrue(module instanceof GlobModule);
    }

    @Test
    void fileModule_ReadsTextFileContent() throws Exception {
        FileModule fileModule = new FileModule();
        Path testFile = tempDir.resolve("test.txt");
        List<String> expectedLines = List.of("Line 1: Test content", "Line 2: More content", "Line 3: Final content");
        Files.write(testFile, expectedLines);

        List<String> actualLines = fileModule.getFile(testFile.toString());

        assertEquals(expectedLines.size(), actualLines.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), actualLines.get(i));
        }
    }

    @Test
    void fileModule_ReadsAnyFileContent() throws Exception {
        FileModule fileModule = new FileModule();
        Path testFile = tempDir.resolve("test.csv");
        List<String> expectedLines = List.of("header1,header2", "value1,value2");
        Files.write(testFile, expectedLines);

        List<String> actualLines = fileModule.getFile(testFile.toString());

        assertEquals(expectedLines.size(), actualLines.size());
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void fileModule_ThrowsExceptionForNonExistentFile() {
        FileModule fileModule = new FileModule();

        IOException exception = assertThrows(IOException.class, () -> {
            fileModule.getFile(tempDir.resolve("nonexistent.log").toString());
        });

        assertTrue(exception.getMessage().contains("File does not exist"));
    }

    @Test
    void globModule_FindsFilesByPattern() throws Exception {
        GlobModule globModule = new GlobModule();
        Path file1 = tempDir.resolve("access1.log");
        Path file2 = tempDir.resolve("access2.log");
        Path otherFile = tempDir.resolve("other.txt");

        Files.write(file1, List.of("Log entry from file 1", "Another line from file 1"));
        Files.write(file2, List.of("Log entry from file 2"));
        Files.write(otherFile, List.of("This should not be read"));

        List<String> lines = globModule.getFile(tempDir.toString() + "/*.log");
        assertTrue(lines.size() >= 3);
        assertTrue(lines.stream().anyMatch(line -> line.contains("file 1")));
        assertTrue(lines.stream().anyMatch(line -> line.contains("file 2")));
        assertTrue(lines.stream().noneMatch(line -> line.contains("should not be read")));
    }

    @Test
    void globModule_ThrowsExceptionForNonExistentDirectory() {
        GlobModule globModule = new GlobModule();

        IOException exception = assertThrows(IOException.class, () -> {
            globModule.getFile("/non/existent/directory/*.log");
        });

        assertTrue(exception.getMessage().contains("Directory does not exist")
                || exception.getMessage().contains("No files found"));
    }
}

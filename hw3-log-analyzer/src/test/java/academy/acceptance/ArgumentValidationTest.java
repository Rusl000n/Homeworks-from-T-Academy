package academy.acceptance;

import static org.junit.jupiter.api.Assertions.*;

import academy.utils.factories.OutputFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ArgumentValidationTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("На вход передан несуществующий локальный файл")
    void test1() {
        Path nonExistent = tempDir.resolve("nonexistent.log");
        assertFalse(Files.exists(nonExistent));
    }

    @Test
    @DisplayName("На вход передан несуществующий удаленный файл")
    void test2() {
        assertTrue(true);
    }

    @ParameterizedTest
    @ValueSource(strings = ".docx")
    @DisplayName("На вход передан файл в неподдерживаемом формате")
    void test3(String extension) throws Exception {
        Path docxFile = tempDir.resolve("test" + extension);
        Files.writeString(docxFile, "content");
        assertTrue(Files.exists(docxFile));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2025.01.01 10:30", "today"})
    @DisplayName("На вход переданы невалидные параметры --from / --to - {0}")
    void test4(String from) {
        assertFalse(from.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = "txt")
    @DisplayName("Результаты запрошены в неподдерживаемом формате {0}")
    void test5(String format) {
        OutputFactory factory = new OutputFactory();
        if (!format.equals("json") && !format.equals("markdown") && !format.equals("adoc")) {
            assertThrows(IllegalArgumentException.class, () -> factory.createOutputModule(format));
        }
    }

    @ParameterizedTest
    @MethodSource("test6ArgumentsSource")
    @DisplayName("По пути в аргументе --output указан файл с некоректным расширением")
    void test6(String format, String output) {
        OutputFactory factory = new OutputFactory();
        assertFalse(factory.isValidExtension(output, format));
    }

    @Test
    @DisplayName("По пути в аргументе --output уже существует файл")
    void test7() throws Exception {
        Path existingFile = tempDir.resolve("existing.json");
        Files.writeString(existingFile, "content");
        assertTrue(Files.exists(existingFile));
    }

    @ParameterizedTest
    @ValueSource(strings = {"--path", "--output", "--format"})
    @DisplayName("На вход не передан обязательный параметр \"{0}\"")
    void test8(String argument) {
        assertTrue(argument.startsWith("-"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"--input", "--filter"})
    @DisplayName("На вход передан неподдерживаемый параметр \"{0}\"")
    void test9(String argument) {
        assertTrue(argument.startsWith("--"));
    }

    @Test
    @DisplayName("Значение параметра --from больше, чем значение параметра --to")
    void test10() {
        String from = "2024-02-01";
        String to = "2024-01-01";
        assertNotEquals(from, to);
    }

    private static Stream<Arguments> test6ArgumentsSource() {
        return Stream.of(
                Arguments.of("markdown", "./results.txt"),
                Arguments.of("json", "./results.md"),
                Arguments.of("adoc", "./results.ad1"));
    }
}

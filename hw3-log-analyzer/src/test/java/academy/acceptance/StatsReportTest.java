package academy.acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.dto.analytics.AnalyticsDate;
import academy.dto.analytics.ResponseSize;
import academy.io.AdocModule;
import academy.io.JacksonModule;
import academy.io.MarkdownModule;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StatsReportTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Сохранение статистики в формате JSON")
    void jsonTest() throws Exception {
        Path outputFile = tempDir.resolve("report.json");

        AnalyticsDate analytics = createTestAnalytics();
        JacksonModule module = new JacksonModule();

        module.writeFile(outputFile.toString(), analytics);
        assertTrue(outputFile.toFile().exists());
    }

    @Test
    @DisplayName("Сохранение статистики в формате MARKDOWN")
    void markdownTest() throws Exception {
        Path outputFile = tempDir.resolve("report.md");

        AnalyticsDate analytics = createTestAnalytics();
        MarkdownModule module = new MarkdownModule();

        module.writeFile(outputFile.toString(), analytics);
        assertTrue(outputFile.toFile().exists());
    }

    @Test
    @DisplayName("Сохранение статистики в формате ADOC")
    void adocTest() throws Exception {
        Path outputFile = tempDir.resolve("report.ad");

        AnalyticsDate analytics = createTestAnalytics();
        AdocModule module = new AdocModule();

        module.writeFile(outputFile.toString(), analytics);
        assertTrue(outputFile.toFile().exists());
    }

    private AnalyticsDate createTestAnalytics() {
        List<String> fileNames = Arrays.asList("test.log");

        return new AnalyticsDate(
                fileNames,
                100,
                new ResponseSize(1024.5, 2048.0, 1536.2),
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList("HTTP/1.1"));
    }
}

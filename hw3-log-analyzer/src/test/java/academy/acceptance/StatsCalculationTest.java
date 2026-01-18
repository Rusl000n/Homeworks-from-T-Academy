package academy.acceptance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import academy.dto.ParseFiles;
import academy.dto.analytics.AnalyticsDate;
import academy.dto.parse.NgixTypes;
import academy.dto.parse.RequestsTypes;
import academy.logAnalyzer.Analyzer;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StatsCalculationTest {

    @Test
    @DisplayName("Расчет статистики на основании локального log-файла")
    void happyPathTest() {
        NgixTypes log = new NgixTypes(
                "192.168.1.1",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/index.html", "HTTP/1.1"),
                200,
                1024,
                "-",
                "Mozilla");

        ParseFiles parseFiles = new ParseFiles("test.log", Arrays.asList(log));
        Analyzer analyzer = new Analyzer();

        AnalyticsDate analytics = analyzer.analyze(Arrays.asList(parseFiles));

        assertNotNull(analytics);
    }
}

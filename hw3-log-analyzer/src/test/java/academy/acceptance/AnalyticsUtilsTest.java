package academy.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.dto.analytics.Resources;
import academy.dto.parse.NgixTypes;
import academy.dto.parse.RequestsTypes;
import academy.logAnalyzer.AnalyticsUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnalyticsUtilsTest {

    private AnalyticsUtils analyticsUtils;
    private List<NgixTypes> testLogs;

    @BeforeEach
    void setUp() {
        analyticsUtils = new AnalyticsUtils();
        testLogs = new ArrayList<>();
    }

    @Test
    void calculateResources_EmptyList_ReturnsEmptyList() {
        List<Resources> result = analyticsUtils.calculateResources(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void calculateResources_DuplicateRequests_ReturnsSortedByCount() {
        testLogs.add(new NgixTypes(
                "192.168.1.1",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/index.html", "HTTP/1.1"),
                200,
                1024,
                "-",
                "Mozilla"));
        testLogs.add(new NgixTypes(
                "192.168.1.2",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/index.html", "HTTP/1.1"),
                200,
                2048,
                "-",
                "Chrome"));
        testLogs.add(new NgixTypes(
                "192.168.1.3",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/about.html", "HTTP/1.1"),
                200,
                512,
                "-",
                "Safari"));

        List<Resources> result = analyticsUtils.calculateResources(testLogs);

        assertEquals(2, result.size());
        assertEquals("/index.html", result.get(0).getResource());
        assertEquals(2, result.get(0).getTotalRequestsCount());
        assertEquals("/about.html", result.get(1).getResource());
        assertEquals(1, result.get(1).getTotalRequestsCount());
    }

    @Test
    void calculateProtocols_VariousUrls_ReturnsUniqueProtocols() {
        testLogs.add(new NgixTypes(
                "192.168.1.1",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/index.html", "HTTP/1.1"),
                200,
                1024,
                "-",
                "Mozilla"));
        testLogs.add(new NgixTypes(
                "192.168.1.2",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/secure.html", "HTTPS/2.0"),
                200,
                2048,
                "-",
                "Chrome"));
        testLogs.add(new NgixTypes(
                "192.168.1.3",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/index.html", "HTTP/1.1"),
                200,
                512,
                "-",
                "Safari"));
        testLogs.add(new NgixTypes(
                "192.168.1.4",
                "-",
                LocalDateTime.now(),
                new RequestsTypes("GET", "/api/data", "HTTP/2.0"),
                200,
                1024,
                "-",
                "Firefox"));

        List<String> result = analyticsUtils.calculateProtocols(testLogs);

        assertEquals(3, result.size());
        assertTrue(result.contains("HTTP/1.1"));
        assertTrue(result.contains("HTTPS/2.0"));
        assertTrue(result.contains("HTTP/2.0"));
    }
}

package academy.io;

import academy.dto.analytics.AnalyticsDate;
import academy.dto.analytics.RequestsPerDate;
import academy.dto.analytics.Resources;
import academy.dto.analytics.ResponseCodes;
import academy.dto.analytics.ResponseSize;
import academy.utils.Utils;
import academy.utils.constants.AdocConstants;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdocModule implements OutputInterface {
    private static final Logger logger = LogManager.getLogger(AdocModule.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(AdocConstants.DECIMAL_FORMAT_PATTERN);
    private final Utils utils = new Utils();

    @Override
    public void writeFile(String output, AnalyticsDate analyticsDate) throws Exception {
        Path path = Path.of(output);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path))) {
            writeCompleteReport(writer, analyticsDate);
        } catch (Exception e) {
            logger.error("Error writing AsciiDoc to file: {}", output, e);
            throw e;
        }
    }

    private void writeCompleteReport(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeDocumentTitle(writer, AdocConstants.REPORT_TITLE);
        writeSummary(writer, analyticsDate);
        writeResponseSize(writer, analyticsDate);
        writeTopResources(writer, analyticsDate);
        writeResponseCodes(writer, analyticsDate);
        writeRequestsPerDate(writer, analyticsDate);
        writeUniqueProtocols(writer, analyticsDate);
    }

    private void writeSummary(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeSection(writer, AdocConstants.SUMMARY_HEADING);
        writeBoldText(
                writer,
                "Total Requests",
                utils.formatNumber(analyticsDate.getTotalRequestsCount(), AdocConstants.NUMBER_FORMAT_PATTERN));
        writeBoldText(
                writer,
                "Files Processed",
                String.valueOf(analyticsDate.getFiles().size()));
        writer.println();
    }

    private void writeResponseSize(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeSection(writer, AdocConstants.RESPONSE_SIZE_HEADING);
        ResponseSize size = analyticsDate.getResponseSize();
        writeBoldTextWithUnits(writer, "Average Size", utils.formatDecimal(size.getAverage(), DECIMAL_FORMAT), "bytes");
        writeBoldTextWithUnits(writer, "Maximum Size", utils.formatDecimal(size.getMaxSize(), DECIMAL_FORMAT), "bytes");
        writeBoldTextWithUnits(writer, "95th Percentile", utils.formatDecimal(size.getP95(), DECIMAL_FORMAT), "bytes");
        writer.println();
    }

    private void writeTopResources(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeSection(writer, AdocConstants.TOP_RESOURCES_HEADING);
        List<Resources> topResources =
                utils.getTopResources(analyticsDate.getResourcesList(), AdocConstants.TOP_RESOURCES_LIMIT);
        writeResourcesTable(writer, topResources);
        writer.println();
    }

    private void writeResponseCodes(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeSection(writer, AdocConstants.RESPONSE_CODES_HEADING);
        List<ResponseCodes> sortedCodes = utils.getSortedResponseCodes(analyticsDate.getResponseCodesList());
        writeResponseCodesTable(writer, sortedCodes, analyticsDate.getTotalRequestsCount());
        writer.println();
    }

    private void writeRequestsPerDate(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeSection(writer, AdocConstants.REQUESTS_PER_DATE_HEADING);
        List<RequestsPerDate> sortedDates = utils.getSortedDates(analyticsDate.getRequestsPerDatesList());
        writeRequestsPerDateTable(writer, sortedDates);
        writer.println();
    }

    private void writeUniqueProtocols(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeSection(writer, AdocConstants.UNIQUE_PROTOCOLS_HEADING);
        List<String> protocols = analyticsDate.getUniqueProtocols();
        if (!protocols.isEmpty()) {
            protocols.forEach(protocol -> writer.printf("* %s%n", protocol));
        } else {
            writer.printf("* %s%n", AdocConstants.NO_PROTOCOLS_MESSAGE);
        }
    }

    private void writeDocumentTitle(PrintWriter writer, String title) {
        writer.println(String.format(AdocConstants.DOCUMENT_TITLE_FORMAT, title));
        writer.println();
    }

    private void writeSection(PrintWriter writer, String sectionName) {
        writer.println(String.format(AdocConstants.SECTION_FORMAT, sectionName));
        writer.println();
    }

    private void writeBoldText(PrintWriter writer, String label, String value) {
        writer.println(String.format(AdocConstants.BOLD_TEXT_FORMAT, label, value));
    }

    private void writeBoldTextWithUnits(PrintWriter writer, String label, String value, String units) {
        writer.println(String.format("%s %s", String.format(AdocConstants.BOLD_TEXT_FORMAT, label, value), units));
    }

    private void writeResourcesTable(PrintWriter writer, List<Resources> resources) {
        writer.println(AdocConstants.TABLE_OPTIONS);
        writer.println(AdocConstants.TABLE_START);
        writer.println(AdocConstants.RESOURCE_TABLE_HEADER);

        for (Resources resource : resources) {
            String resourceName = utils.truncateText(
                    resource.getResource(),
                    AdocConstants.RESOURCE_TRUNCATE_LENGTH,
                    AdocConstants.TRUNCATE_ELLIPSIS_SIZE);
            String count = utils.formatNumber(resource.getTotalRequestsCount(), AdocConstants.NUMBER_FORMAT_PATTERN);
            writer.println(String.format(AdocConstants.TABLE_ROW_FORMAT, resourceName, count));
        }

        writer.println(AdocConstants.TABLE_END);
    }

    private void writeResponseCodesTable(PrintWriter writer, List<ResponseCodes> codes, long totalRequests) {
        writer.println(AdocConstants.TABLE_OPTIONS);
        writer.println(AdocConstants.TABLE_START);
        writer.println(AdocConstants.RESPONSE_CODES_TABLE_HEADER);

        for (ResponseCodes code : codes) {
            String count = utils.formatNumber(code.getTotalResponsesCount(), AdocConstants.NUMBER_FORMAT_PATTERN);
            String percentage = utils.formatPercentage(
                    utils.calculatePercentage(code.getTotalResponsesCount(), totalRequests), DECIMAL_FORMAT);
            writer.println(String.format(AdocConstants.TABLE_ROW_THREE_COL_FORMAT, code.getCode(), count, percentage));
        }

        writer.println(AdocConstants.TABLE_END);
    }

    private void writeRequestsPerDateTable(PrintWriter writer, List<RequestsPerDate> dates) {
        writer.println(AdocConstants.TABLE_OPTIONS);
        writer.println(AdocConstants.TABLE_START);
        writer.println(AdocConstants.REQUESTS_PER_DATE_TABLE_HEADER);

        for (RequestsPerDate date : dates) {
            String count = utils.formatNumber(date.getTotalRequestsCount(), AdocConstants.NUMBER_FORMAT_PATTERN);
            String percentage = utils.formatPercentage(date.getTotalRequestsPercentage(), DECIMAL_FORMAT);
            writer.println(String.format(
                    AdocConstants.TABLE_ROW_FOUR_COL_FORMAT, date.getDate(), date.getWeekday(), count, percentage));
        }

        writer.println(AdocConstants.TABLE_END);
    }
}

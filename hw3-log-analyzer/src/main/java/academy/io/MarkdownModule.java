package academy.io;

import academy.dto.analytics.AnalyticsDate;
import academy.dto.analytics.RequestsPerDate;
import academy.dto.analytics.Resources;
import academy.dto.analytics.ResponseCodes;
import academy.dto.analytics.ResponseSize;
import academy.utils.Utils;
import academy.utils.constants.MarkdownConstants;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarkdownModule implements OutputInterface {
    private static final Logger logger = LogManager.getLogger(MarkdownModule.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(MarkdownConstants.DECIMAL_FORMAT_PATTERN);
    private final Utils utils = new Utils();

    @Override
    public void writeFile(String output, AnalyticsDate analyticsDate) throws Exception {
        Path path = Path.of(output);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path))) {
            writeCompleteReport(writer, analyticsDate);
        } catch (Exception e) {
            logger.error("Error writing Markdown to file: {}", output, e);
            throw e;
        }
    }

    private void writeCompleteReport(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.REPORT_TITLE, 1);
        writeSummarySection(writer, analyticsDate);
        writeResponseSizeSection(writer, analyticsDate);
        writeTopResourcesSection(writer, analyticsDate);
        writeResponseCodesSection(writer, analyticsDate);
        writeRequestsPerDateSection(writer, analyticsDate);
        writeUniqueProtocolsSection(writer, analyticsDate);
    }

    private void writeSummarySection(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.SUMMARY_HEADING, 2);
        writeBoldListItem(
                writer,
                "Total Requests",
                utils.formatNumber(analyticsDate.getTotalRequestsCount(), MarkdownConstants.NUMBER_FORMAT_PATTERN));
        writeBoldListItem(
                writer,
                "Files Processed",
                String.valueOf(analyticsDate.getFiles().size()));
        writer.println();
    }

    private void writeResponseSizeSection(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.RESPONSE_SIZE_HEADING, 2);
        ResponseSize size = analyticsDate.getResponseSize();
        writeBoldListItemWithUnits(
                writer, "Average Size", utils.formatDecimal(size.getAverage(), DECIMAL_FORMAT), "bytes");
        writeBoldListItemWithUnits(
                writer, "Maximum Size", utils.formatDecimal(size.getMaxSize(), DECIMAL_FORMAT), "bytes");
        writeBoldListItemWithUnits(
                writer, "95th Percentile", utils.formatDecimal(size.getP95(), DECIMAL_FORMAT), "bytes");
        writer.println();
    }

    private void writeTopResourcesSection(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.TOP_RESOURCES_HEADING, 2);
        List<Resources> topResources =
                utils.getTopResources(analyticsDate.getResourcesList(), MarkdownConstants.TOP_RESOURCES_LIMIT);
        writeResourcesTable(writer, topResources);
        writer.println();
    }

    private void writeResponseCodesSection(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.RESPONSE_CODES_HEADING, 2);
        List<ResponseCodes> sortedCodes = utils.getSortedResponseCodes(analyticsDate.getResponseCodesList());
        writeResponseCodesTable(writer, sortedCodes, analyticsDate.getTotalRequestsCount());
        writer.println();
    }

    private void writeRequestsPerDateSection(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.REQUESTS_PER_DATE_HEADING, 2);
        List<RequestsPerDate> sortedDates = utils.getSortedDates(analyticsDate.getRequestsPerDatesList());
        writeRequestsPerDateTable(writer, sortedDates);
        writer.println();
    }

    private void writeUniqueProtocolsSection(PrintWriter writer, AnalyticsDate analyticsDate) {
        writeHeading(writer, MarkdownConstants.UNIQUE_PROTOCOLS_HEADING, 2);
        List<String> protocols = analyticsDate.getUniqueProtocols();
        protocols.forEach(protocol -> writer.println("- " + protocol));
    }

    private void writeHeading(PrintWriter writer, String text, int countRepeat) {
        writer.println("#".repeat(countRepeat) + " " + text);
        writer.println();
    }

    private void writeBoldListItem(PrintWriter writer, String label, String value) {
        writer.println(String.format(MarkdownConstants.BOLD_ITEM_FORMAT, label, value));
    }

    private void writeBoldListItemWithUnits(PrintWriter writer, String label, String value, String units) {
        writer.println(String.format(MarkdownConstants.BOLD_ITEM_WITH_UNITS_FORMAT, label, value, units));
    }

    private void writeResourcesTable(PrintWriter writer, List<Resources> resources) {
        writer.println("| Resource | Request Count |");
        writer.println("|----------|--------------|");
        for (Resources resource : resources) {
            String resourceName = utils.truncateText(
                    resource.getResource(),
                    MarkdownConstants.RESOURCE_TRUNCATE_LENGTH,
                    MarkdownConstants.TRUNCATE_ELLIPSIS_SIZE);
            String count =
                    utils.formatNumber(resource.getTotalRequestsCount(), MarkdownConstants.NUMBER_FORMAT_PATTERN);
            writer.println(String.format(MarkdownConstants.TABLE_ROW_FORMAT, resourceName, count));
        }
    }

    private void writeResponseCodesTable(PrintWriter writer, List<ResponseCodes> codes, long totalRequests) {
        writer.println("| Code | Count | Percentage |");
        writer.println("|------|-------|------------|");
        for (ResponseCodes code : codes) {
            String count = utils.formatNumber(code.getTotalResponsesCount(), MarkdownConstants.NUMBER_FORMAT_PATTERN);
            String percentage = utils.formatPercentage(
                    utils.calculatePercentage(code.getTotalResponsesCount(), totalRequests), DECIMAL_FORMAT);
            writer.println(
                    String.format(MarkdownConstants.TABLE_ROW_THREE_COL_FORMAT, code.getCode(), count, percentage));
        }
    }

    private void writeRequestsPerDateTable(PrintWriter writer, List<RequestsPerDate> dates) {
        writer.println("| Date | Weekday | Count | Percentage |");
        writer.println("|------|---------|-------|------------|");
        for (RequestsPerDate date : dates) {
            String count = utils.formatNumber(date.getTotalRequestsCount(), MarkdownConstants.NUMBER_FORMAT_PATTERN);
            String percentage = utils.formatPercentage(date.getTotalRequestsPercentage(), DECIMAL_FORMAT);
            writer.println(String.format(
                    MarkdownConstants.TABLE_ROW_FOUR_COL_FORMAT, date.getDate(), date.getWeekday(), count, percentage));
        }
    }
}

package academy.utils.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkdownConstants {

    public static final String REPORT_TITLE = "Nginx Log Analysis Report";
    public static final String SUMMARY_HEADING = "Summary";
    public static final String RESPONSE_SIZE_HEADING = "Response Size Statistics";
    public static final String TOP_RESOURCES_HEADING = "Top Resources";
    public static final String RESPONSE_CODES_HEADING = "Response Codes";
    public static final String REQUESTS_PER_DATE_HEADING = "Requests Per Date";
    public static final String UNIQUE_PROTOCOLS_HEADING = "Unique Protocols";

    public static final String DECIMAL_FORMAT_PATTERN = "#.##";
    public static final String NUMBER_FORMAT_PATTERN = "%,d";

    public static final int TOP_RESOURCES_LIMIT = 10;
    public static final int RESOURCE_TRUNCATE_LENGTH = 50;
    public static final int TRUNCATE_ELLIPSIS_SIZE = 3;

    public static final String BOLD_ITEM_FORMAT = "- **%s**: %s";
    public static final String BOLD_ITEM_WITH_UNITS_FORMAT = "- **%s**: %s %s";
    public static final String TABLE_ROW_FORMAT = "| %s | %s |";
    public static final String TABLE_ROW_THREE_COL_FORMAT = "| %s | %s | %s |";
    public static final String TABLE_ROW_FOUR_COL_FORMAT = "| %s | %s | %s | %s |";
}

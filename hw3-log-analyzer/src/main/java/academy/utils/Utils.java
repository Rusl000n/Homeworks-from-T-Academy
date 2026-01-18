package academy.utils;

import academy.dto.analytics.RequestsPerDate;
import academy.dto.analytics.Resources;
import academy.dto.analytics.ResponseCodes;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public List<Resources> getTopResources(List<Resources> resources, int limit) {
        return resources.stream()
                .sorted(Comparator.comparingLong(Resources::getTotalRequestsCount)
                        .reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<ResponseCodes> getSortedResponseCodes(List<ResponseCodes> codes) {
        return codes.stream()
                .sorted(Comparator.comparingLong(ResponseCodes::getTotalResponsesCount)
                        .reversed())
                .collect(Collectors.toList());
    }

    public List<RequestsPerDate> getSortedDates(List<RequestsPerDate> dates) {
        return dates.stream()
                .sorted(Comparator.comparing(RequestsPerDate::getDate))
                .collect(Collectors.toList());
    }

    public double calculatePercentage(long part, long total) {
        if (total == 0) return 0.0;
        return part * 100.0 / total;
    }

    public String formatNumber(long number, String pattern) {
        return String.format(pattern, number);
    }

    public String formatDecimal(double number, DecimalFormat formatter) {
        return formatter.format(number);
    }

    public String formatPercentage(double percentage, DecimalFormat formatter) {
        return String.format("%s%%", formatter.format(percentage));
    }

    public String truncateText(String text, int maxLength, int ellipsisSize) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - ellipsisSize) + "...";
    }
}

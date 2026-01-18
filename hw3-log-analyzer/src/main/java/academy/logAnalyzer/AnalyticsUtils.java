package academy.logAnalyzer;

import static academy.utils.constants.Constants.TO_95_PERCENTAGE;

import academy.dto.ParseFiles;
import academy.dto.analytics.RequestsPerDate;
import academy.dto.analytics.Resources;
import academy.dto.analytics.ResponseCodes;
import academy.dto.analytics.ResponseSize;
import academy.dto.parse.NgixTypes;
import academy.parser.FileParser;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AnalyticsUtils {

    public List<Resources> calculateResources(List<NgixTypes> ngixTypesList) {
        return ngixTypesList.stream()
                .collect(Collectors.groupingBy(
                        ngixTypes -> ngixTypes.getRequest().getResources(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(10)
                .map(entry -> new Resources(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<ResponseCodes> calculateResponses(List<NgixTypes> ngixTypesList) {
        return ngixTypesList.stream()
                .collect(Collectors.groupingBy(NgixTypes::getStatusCode, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new ResponseCodes(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<RequestsPerDate> calculateRequests(List<NgixTypes> ngixTypesList) {
        return ngixTypesList.stream()
                .collect(Collectors.groupingBy(ngix -> ngix.getTime().toLocalDate(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> calculateFormattedDate(entry.getKey(), entry.getValue(), ngixTypesList.size()))
                .collect(Collectors.toList());
    }

    public ResponseSize calculateResponseSize(List<NgixTypes> ngixTypesList) {
        DoubleSummaryStatistics statistics = getSummaryStatistic(ngixTypesList);
        return new ResponseSize(statistics.getAverage(), statistics.getMax(), findPercentage(ngixTypesList));
    }

    public List<String> calculateProtocols(List<NgixTypes> ngixTypesList) {
        return ngixTypesList.stream()
                .map(ngixTypes -> ngixTypes.getRequest().getUrl())
                .distinct()
                .collect(Collectors.toList());
    }

    private double findPercentage(List<NgixTypes> ngixTypesList) {
        List<Integer> list = ngixTypesList.stream()
                .map(ngixTypes -> ngixTypes.getBytesSize())
                .sorted()
                .collect(Collectors.toList());
        int lowerIndex = (int) Math.floor(TO_95_PERCENTAGE * (list.size() - 1));
        int upperIndex = (int) Math.ceil(TO_95_PERCENTAGE * (list.size() - 1));
        if (lowerIndex == upperIndex) {
            return list.get(lowerIndex);
        }
        double weight = (TO_95_PERCENTAGE * (list.size() - 1)) - lowerIndex;
        return list.get(lowerIndex) + weight * (list.get(upperIndex) - list.get(lowerIndex));
    }

    private RequestsPerDate calculateFormattedDate(LocalDate localDate, long count, int sizeList) {
        return new RequestsPerDate(
                localDate,
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                count,
                toPercentage(sizeList, count));
    }

    private double toPercentage(long a, long b) {
        try {
            if (a == 0) {
                return 0;
            }
            return b * 100.0 / a;
        } catch (Exception e) {
            return 0;
        }
    }

    private DoubleSummaryStatistics getSummaryStatistic(List<NgixTypes> ngixTypesList) {
        return ngixTypesList.stream()
                .mapToDouble(ngix -> (double) ngix.getBytesSize())
                .summaryStatistics();
    }

    public List<String> parseFilesList(List<ParseFiles> parseFilesList) {
        return parseFilesList.stream()
                .map(ParseFiles::getFile)
                .flatMap(filePath -> {
                    try {
                        FileParser fileParser = new FileParser();
                        List<String> fileNames = fileParser.getFileNames(filePath);
                        return fileNames.stream();
                    } catch (Exception e) {
                        String fileNameOnly;
                        if (filePath.contains("/")) {
                            fileNameOnly = filePath.substring(filePath.lastIndexOf("/") + 1);
                        } else if (filePath.contains("\\")) {
                            fileNameOnly = filePath.substring(filePath.lastIndexOf("\\") + 1);
                        } else {
                            fileNameOnly = filePath;
                        }
                        return List.of(fileNameOnly).stream();
                    }
                })
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

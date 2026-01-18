package academy.logAnalyzer;

import academy.dto.ParseFiles;
import academy.dto.analytics.AnalyticsDate;
import academy.dto.parse.NgixTypes;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Analyzer {

    private AnalyticsUtils utils;

    public Analyzer() {
        utils = new AnalyticsUtils();
    }

    public AnalyticsDate analyze(List<ParseFiles> parseFilesList) {
        List<NgixTypes> ngixTypesList = toNgixTypes(parseFilesList);
        return new AnalyticsDate(
                utils.parseFilesList(parseFilesList),
                ngixTypesList.size(),
                utils.calculateResponseSize(ngixTypesList),
                utils.calculateResources(ngixTypesList),
                utils.calculateResponses(ngixTypesList),
                utils.calculateRequests(ngixTypesList),
                utils.calculateProtocols(ngixTypesList));
    }

    private List<NgixTypes> toNgixTypes(List<ParseFiles> parseFilesList) {
        return parseFilesList.stream()
                .map(ParseFiles::getNgixTypesList)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}

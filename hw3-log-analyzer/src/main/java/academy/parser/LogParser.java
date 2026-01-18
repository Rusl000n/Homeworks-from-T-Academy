package academy.parser;

import academy.dto.parse.NgixTypes;
import academy.utils.Converter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogParser {

    Converter converter = new Converter();

    public List<NgixTypes> parseList(List<String> inputList) {
        return inputList.stream()
                .map(line -> converter.stringToNgix(line))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

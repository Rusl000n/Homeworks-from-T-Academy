package academy.utils;

import static academy.dto.parse.NgixConstants.*;
import static academy.utils.constants.Constants.REGEX_HGIX_TYPE;
import static academy.utils.constants.Constants.REGEX_REQUESTS;
import static academy.utils.constants.Constants.REGEX_TIME;

import academy.dto.parse.NgixTypes;
import academy.dto.parse.RequestsTypes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Converter {
    private static final Logger logger = LogManager.getLogger(Converter.class);

    public NgixTypes stringToNgix(String line) {
        try {
            Pattern pattern = Pattern.compile(REGEX_HGIX_TYPE);
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                logger.warn("Log line does not match pattern: {}", line);
                return null;
            }
            return new NgixTypes(
                    matcher.group(IP),
                    matcher.group(USERNAME),
                    stringToTime(matcher.group(TIME)),
                    stringToRequestType(matcher.group(REQUEST)),
                    Integer.parseInt(matcher.group(STATUS_CODE)),
                    Integer.parseInt(matcher.group(BYTES_SIZE).equals("-") ? "0" : matcher.group(BYTES_SIZE)),
                    matcher.group(URL_REFERER),
                    matcher.group(USER_AGENT));
        } catch (Exception e) {
            logger.error("Error parsing log line: {}", line, e);
            return null;
        }
    }

    private LocalDateTime stringToTime(String string) throws IllegalArgumentException {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(REGEX_TIME, Locale.ENGLISH);
            return LocalDateTime.parse(string, dateTimeFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Impossible to parse string to time: " + string);
        }
    }

    private RequestsTypes stringToRequestType(String string) throws IllegalArgumentException {
        try {
            Pattern pattern = Pattern.compile(REGEX_REQUESTS);
            Matcher matcher = pattern.matcher(string);
            matcher.find();
            return new RequestsTypes(matcher.group(TYPE_REQUEST), matcher.group(RESOURCES), matcher.group(URL));
        } catch (Exception e) {
            throw new IllegalArgumentException("Impossible to parse request in string " + string);
        }
    }
}

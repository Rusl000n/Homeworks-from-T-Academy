package academy.dto.analytics;

import academy.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestsPerDate {
    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("weekday")
    private String weekday;

    @JsonProperty("totalRequestsCount")
    private long totalRequestsCount;

    @JsonProperty("totalRequestsPercentage")
    @JsonSerialize(using = DoubleSerializer.class)
    private double totalRequestsPercentage;
}

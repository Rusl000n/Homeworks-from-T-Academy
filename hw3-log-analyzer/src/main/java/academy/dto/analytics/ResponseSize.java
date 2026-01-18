package academy.dto.analytics;

import academy.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseSize {
    @JsonSerialize(using = DoubleSerializer.class)
    private double average;

    @JsonProperty("max")
    @JsonSerialize(using = DoubleSerializer.class)
    private double maxSize;

    @JsonSerialize(using = DoubleSerializer.class)
    private double p95;
}

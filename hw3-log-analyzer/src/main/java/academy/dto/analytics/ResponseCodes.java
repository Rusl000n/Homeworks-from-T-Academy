package academy.dto.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseCodes {
    @JsonProperty("code")
    private int code;

    @JsonProperty("totalResponsesCount")
    private long totalResponsesCount;
}

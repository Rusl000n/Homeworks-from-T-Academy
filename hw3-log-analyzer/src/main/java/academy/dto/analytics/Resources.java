package academy.dto.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Resources {
    @JsonProperty("resource")
    private String resource;

    @JsonProperty("totalRequestsCount")
    private long totalRequestsCount;
}

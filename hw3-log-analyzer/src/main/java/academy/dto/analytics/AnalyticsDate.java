package academy.dto.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsDate {
    @JsonProperty("files")
    private List<String> files;

    @JsonProperty("totalRequestsCount")
    private int totalRequestsCount;

    @JsonProperty("responseSizeInBytes")
    private ResponseSize responseSize;

    @JsonProperty("resources")
    private List<Resources> resourcesList;

    @JsonProperty("responseCodes")
    private List<ResponseCodes> responseCodesList;

    @JsonProperty("requestsPerDate")
    private List<RequestsPerDate> requestsPerDatesList;

    @JsonProperty("uniqueProtocols")
    private List<String> uniqueProtocols;
}

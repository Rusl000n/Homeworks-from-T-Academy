package academy.dto.parse;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NgixTypes {
    private String ip;
    private String userName;
    private LocalDateTime time;
    private RequestsTypes request;
    private int statusCode;
    private int bytesSize;
    private String urlReferer;
    private String userAgent;
}

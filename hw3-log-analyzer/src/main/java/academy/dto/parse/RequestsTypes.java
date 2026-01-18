package academy.dto.parse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestsTypes {
    private String typeRequest;
    private String resources;
    private String url;
}

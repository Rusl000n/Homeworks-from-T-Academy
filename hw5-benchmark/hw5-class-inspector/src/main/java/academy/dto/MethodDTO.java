package academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodDTO {
    private String access;
    private String name;
    @JsonProperty("returnType")
    private String returnType;
    @JsonProperty("params")
    private List<String> parameters;
    private List<String> annotations;
}
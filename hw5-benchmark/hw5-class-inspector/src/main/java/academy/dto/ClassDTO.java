package academy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    @JsonProperty("class")
    private String className;
    private String superclass;
    private List<String> interfaces;
    private List<FieldDTO> fields;
    private List<MethodDTO> methods;
    private List<String> annotations;
    private Map<String, Object> hierarchy;
}
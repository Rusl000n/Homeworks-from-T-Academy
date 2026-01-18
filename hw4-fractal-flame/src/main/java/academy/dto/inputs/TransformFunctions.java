package academy.dto.inputs;

import static academy.utils.ConstantsConfig.TRANSFORM_FUNCTIONS_ENUM;
import static academy.utils.ConstantsConfig.WEIGHT;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransformFunctions {

    @JsonProperty("name")
    private TransformFunctionsEnum transformFunctionsEnum = TRANSFORM_FUNCTIONS_ENUM;

    @JsonProperty("weight")
    private double weight = WEIGHT;
}

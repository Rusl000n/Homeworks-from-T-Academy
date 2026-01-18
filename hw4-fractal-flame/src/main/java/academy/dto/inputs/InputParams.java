package academy.dto.inputs;

import static academy.utils.ConstantsConfig.AFFINE_PARAMS_LIST;
import static academy.utils.ConstantsConfig.ITERATION_COUNT;
import static academy.utils.ConstantsConfig.OUTPUT_PATH;
import static academy.utils.ConstantsConfig.SEED;
import static academy.utils.ConstantsConfig.SIZE_PARAMS;
import static academy.utils.ConstantsConfig.THREADS;
import static academy.utils.ConstantsConfig.TRANSFORM_FUNCTIONS_LIST;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class InputParams {

    @JsonProperty("size")
    private SizeParams size = SIZE_PARAMS;

    private long seed = SEED;

    @JsonProperty("iteration_count")
    private int iterationCount = ITERATION_COUNT;

    @JsonProperty("output_path")
    private String outputPath = OUTPUT_PATH;

    private int threads = THREADS;

    @JsonProperty("affine_params")
    private List<AffineParams> affineParamsList = AFFINE_PARAMS_LIST;

    @JsonProperty("functions")
    private List<TransformFunctions> transformFunctionsList = TRANSFORM_FUNCTIONS_LIST;
}

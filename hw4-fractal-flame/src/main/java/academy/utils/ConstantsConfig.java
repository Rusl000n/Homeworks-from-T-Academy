package academy.utils;

import academy.dto.inputs.AffineParams;
import academy.dto.inputs.SizeParams;
import academy.dto.inputs.TransformFunctions;
import academy.dto.inputs.TransformFunctionsEnum;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsConfig {
    public static final double A = 1.0d;
    public static final double B = 1.0d;
    public static final double C = 1.0d;
    public static final double D = 1.0d;
    public static final double E = 1.0d;
    public static final double F = 1.0d;
    public static final long BLACK = 0xFF000000;

    public static final List<AffineParams> AFFINE_PARAMS_LIST = List.of(new AffineParams());

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public static final SizeParams SIZE_PARAMS = new SizeParams();

    public static final TransformFunctionsEnum TRANSFORM_FUNCTIONS_ENUM = TransformFunctionsEnum.LINEAR;
    public static final double WEIGHT = 1.0;

    public static final List<TransformFunctions> TRANSFORM_FUNCTIONS_LIST = List.of(new TransformFunctions());

    public static final long SEED = 5L;
    public static final int ITERATION_COUNT = 2500;
    public static final String OUTPUT_PATH = "result.png";
    public static final int THREADS = 1;
}

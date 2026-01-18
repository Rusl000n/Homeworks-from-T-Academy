package academy.affineCalculation;

import static academy.utils.ConstantsConfig.BLACK;

import academy.dto.*;
import academy.dto.inputs.AffineParams;
import academy.dto.inputs.InputParams;
import academy.dto.inputs.TransformFunctions;
import academy.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountingPoints {

    private final Utils utils = new Utils();
    private final CalculatingTransformFunctions calculatingFunctions = new CalculatingTransformFunctions();

    public DoublePoints calculateField(InputParams inputParams, int iterations, long threadSeed) {
        Random random = new Random(threadSeed);
        List<DoublePoint> points = new ArrayList<>(iterations);
        DoublePoint currentPoint = new DoublePoint(0.0, 0.0, BLACK);

        for (int i = 0; i < iterations; i++) {
            AffineParams affine = utils.getRandomAffine(inputParams.getAffineParamsList(), random);
            DoublePoint afterAffine = calculateAffine(currentPoint, affine);
            TransformFunctions function = utils.getRandomFunction(inputParams.getTransformFunctionsList(), random);
            DoublePoint afterLinear =
                    calculatingFunctions.calculateFunction(afterAffine, function.getTransformFunctionsEnum());
            points.add(new DoublePoint(afterLinear.x(), afterLinear.y(), affine.getColor()));
            currentPoint = new DoublePoint(afterLinear.x(), afterLinear.y(), BLACK);
        }
        return new DoublePoints(points);
    }

    private static DoublePoint calculateAffine(DoublePoint point, AffineParams affine) {
        double x = affine.getA() * point.x() + affine.getB() * point.y() + affine.getC();
        double y = affine.getD() * point.x() + affine.getE() * point.y() + affine.getF();
        return new DoublePoint(x, y, affine.getColor());
    }
}

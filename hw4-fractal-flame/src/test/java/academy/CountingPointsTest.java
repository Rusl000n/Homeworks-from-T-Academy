package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.affineCalculation.CountingPoints;
import academy.dto.*;
import academy.dto.inputs.AffineParams;
import academy.dto.inputs.InputParams;
import academy.dto.inputs.TransformFunctions;
import academy.dto.inputs.TransformFunctionsEnum;
import java.util.List;
import org.junit.jupiter.api.Test;

class CountingPointsTest {

    @Test
    void testCalculateField() {
        CountingPoints counter = new CountingPoints();

        List<AffineParams> affineList = List.of(new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0, 0xFFFF0000));

        List<TransformFunctions> funcList = List.of(new TransformFunctions(TransformFunctionsEnum.LINEAR, 1.0));

        InputParams params = new InputParams();
        params.setAffineParamsList(affineList);
        params.setTransformFunctionsList(funcList);

        DoublePoints result = counter.calculateField(params, 100, 123L);

        assertNotNull(result);
        assertEquals(100, result.getDoublePointList().size());

        for (DoublePoint point : result.getDoublePointList()) {
            assertEquals(0xFFFF0000, point.color());
        }
    }

    @Test
    void testCalculateFieldWithMultipleAffines() {
        CountingPoints counter = new CountingPoints();

        List<AffineParams> affineList = List.of(
                new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0, 0xFFFF0000),
                new AffineParams(0.5, 0.0, 1.0, 0.0, 0.5, 1.0, 0xFF00FF00));

        List<TransformFunctions> funcList = List.of(new TransformFunctions(TransformFunctionsEnum.LINEAR, 1.0));

        InputParams params = new InputParams();
        params.setAffineParamsList(affineList);
        params.setTransformFunctionsList(funcList);

        DoublePoints result = counter.calculateField(params, 200, 456L);

        assertNotNull(result);
        assertEquals(200, result.getDoublePointList().size());

        boolean hasRed = false;
        boolean hasGreen = false;

        for (DoublePoint point : result.getDoublePointList()) {
            if (point.color() == 0xFFFF0000) hasRed = true;
            if (point.color() == 0xFF00FF00) hasGreen = true;
        }

        assertTrue(hasRed || hasGreen);
    }

    @Test
    void testCalculateFieldWithDifferentFunctions() {
        CountingPoints counter = new CountingPoints();

        List<AffineParams> affineList = List.of(new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0, 0xFFFF0000));

        List<TransformFunctions> funcList = List.of(
                new TransformFunctions(TransformFunctionsEnum.LINEAR, 0.5),
                new TransformFunctions(TransformFunctionsEnum.SINUSOIDAL, 0.5));

        InputParams params = new InputParams();
        params.setAffineParamsList(affineList);
        params.setTransformFunctionsList(funcList);

        DoublePoints result = counter.calculateField(params, 50, 789L);

        assertNotNull(result);
        assertEquals(50, result.getDoublePointList().size());
    }

    @Test
    void testCalculateAffine() {
        try {
            var method =
                    CountingPoints.class.getDeclaredMethod("calculateAffine", DoublePoint.class, AffineParams.class);
            method.setAccessible(true);

            DoublePoint point = new DoublePoint(1.0, 2.0, 0xFF000000);
            AffineParams affine = new AffineParams(2.0, 3.0, 1.0, 4.0, 5.0, 2.0, 0xFFFF0000);

            DoublePoint result = (DoublePoint) method.invoke(null, point, affine);

            double expectedX = 2.0 * 1.0 + 3.0 * 2.0 + 1.0;
            double expectedY = 4.0 * 1.0 + 5.0 * 2.0 + 2.0;

            assertEquals(expectedX, result.x(), 0.001);
            assertEquals(expectedY, result.y(), 0.001);
            assertEquals(0xFFFF0000, result.color());

        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }
}

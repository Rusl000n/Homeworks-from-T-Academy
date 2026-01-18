package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.affineCalculation.CalculatingTransformFunctions;
import academy.dto.DoublePoint;
import academy.dto.inputs.TransformFunctionsEnum;
import org.junit.jupiter.api.Test;

public class CalculatingTransformFunctionsTest {

    @Test
    void testLinearFunction() {
        CalculatingTransformFunctions calculator = new CalculatingTransformFunctions();
        DoublePoint point = new DoublePoint(2.0, 3.0, 0xFF000000);
        DoublePoint result = calculator.calculateFunction(point, TransformFunctionsEnum.LINEAR);
        assertEquals(2.0, result.x(), 0.0001);
        assertEquals(3.0, result.y(), 0.0001);
    }

    @Test
    void testSinusoidalFunction() {
        CalculatingTransformFunctions calculator = new CalculatingTransformFunctions();
        DoublePoint point = new DoublePoint(1.0, 2.0, 0xFF000000);
        DoublePoint result = calculator.calculateFunction(point, TransformFunctionsEnum.SINUSOIDAL);
        assertEquals(Math.sin(1.0), result.x(), 0.0001);
        assertEquals(Math.sin(2.0), result.y(), 0.0001);
    }

    @Test
    void testSphericalFunction() {
        CalculatingTransformFunctions calculator = new CalculatingTransformFunctions();
        DoublePoint point = new DoublePoint(1.0, 2.0, 0xFF000000);
        DoublePoint result = calculator.calculateFunction(point, TransformFunctionsEnum.SPHERICAL);
        double r = Math.sqrt(1.0 * 1.0 + 2.0 * 2.0);
        double expectedX = 1.0 / (r * r);
        double expectedY = 2.0 / (r * r);
        assertEquals(expectedX, result.x(), 0.0001);
        assertEquals(expectedY, result.y(), 0.0001);
    }

    @Test
    void testSwirlFunction() {
        CalculatingTransformFunctions calculator = new CalculatingTransformFunctions();
        DoublePoint point = new DoublePoint(1.0, 2.0, 0xFF000000);
        DoublePoint result = calculator.calculateFunction(point, TransformFunctionsEnum.SWIRL);
        double r = 1.0 * 1.0 + 2.0 * 2.0;
        double sinR2 = Math.sin(r * r);
        double cosR2 = Math.cos(r * r);
        double expectedX = 1.0 * sinR2 - 2.0 * cosR2;
        double expectedY = 1.0 * cosR2 + 2.0 * sinR2;
        assertEquals(expectedX, result.x(), 0.0001);
        assertEquals(expectedY, result.y(), 0.0001);
    }

    @Test
    void testHorseshoeFunction() {
        CalculatingTransformFunctions calculator = new CalculatingTransformFunctions();
        DoublePoint point = new DoublePoint(3.0, 4.0, 0xFF000000);
        DoublePoint result = calculator.calculateFunction(point, TransformFunctionsEnum.HORSESHOE);
        double r = Math.sqrt(3.0 * 3.0 + 4.0 * 4.0);
        double expectedX = ((3.0 - 4.0) * (3.0 + 4.0)) / r;
        double expectedY = (2 * 3.0 * 4.0) / r;
        assertEquals(expectedX, result.x(), 0.0001);
        assertEquals(expectedY, result.y(), 0.0001);
    }

    @Test
    void testPopcornFunction() {
        CalculatingTransformFunctions calculator = new CalculatingTransformFunctions();
        DoublePoint point = new DoublePoint(0.5, 0.5, 0xFF000000);
        DoublePoint result = calculator.calculateFunction(point, TransformFunctionsEnum.POPCORN);
        double expectedX = 0.5 + 1.0 * Math.sin(Math.tan(3 * 0.5));
        double expectedY = 0.5 + 1.0 * Math.sin(Math.tan(3 * 0.5));
        assertEquals(expectedX, result.x(), 0.0001);
        assertEquals(expectedY, result.y(), 0.0001);
    }
}

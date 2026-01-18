package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.dto.DoublePoint;
import academy.dto.DoublePoints;
import academy.dto.inputs.AffineParams;
import academy.dto.inputs.TransformFunctions;
import academy.dto.inputs.TransformFunctionsEnum;
import academy.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class UtilsTest {

    private final Utils utils = new Utils();

    @Test
    void testGetRandomAffine() {
        List<AffineParams> affineList = new ArrayList<>();
        affineList.add(new AffineParams(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0xFFFF0000));
        affineList.add(new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0, 0xFF00FF00));
        Random random = new Random(123);
        AffineParams result = utils.getRandomAffine(affineList, random);
        assertNotNull(result);
        assertTrue(affineList.contains(result));
    }

    @Test
    void testGetRandomFunction() {
        List<TransformFunctions> functions = new ArrayList<>();
        functions.add(new TransformFunctions(TransformFunctionsEnum.LINEAR, 0.5));
        functions.add(new TransformFunctions(TransformFunctionsEnum.SINUSOIDAL, 0.3));
        functions.add(new TransformFunctions(TransformFunctionsEnum.SPHERICAL, 0.2));
        Random random = new Random(123);
        TransformFunctions result = utils.getRandomFunction(functions, random);
        assertNotNull(result);
        assertTrue(functions.contains(result));
    }

    @Test
    void testGetRandomFunctionWithWeights() {
        List<TransformFunctions> functions = new ArrayList<>();
        functions.add(new TransformFunctions(TransformFunctionsEnum.LINEAR, 0.8));
        functions.add(new TransformFunctions(TransformFunctionsEnum.SINUSOIDAL, 0.2));
        Random random = new Random(123);
        int linearCount = 0;
        int sinusoidalCount = 0;
        for (int i = 0; i < 100; i++) {
            TransformFunctions result = utils.getRandomFunction(functions, new Random(i));
            if (result.getTransformFunctionsEnum() == TransformFunctionsEnum.LINEAR) {
                linearCount++;
            } else {
                sinusoidalCount++;
            }
        }
        assertTrue(linearCount > sinusoidalCount);
    }

    @Test
    void testConvertToImageParams() {
        List<DoublePoints> pointsList = new ArrayList<>();
        List<DoublePoint> points = new ArrayList<>();
        points.add(new DoublePoint(0.0, 0.0, 0xFFFF0000));
        points.add(new DoublePoint(1.0, 1.0, 0xFF00FF00));
        points.add(new DoublePoint(-1.0, -1.0, 0xFF0000FF));
        pointsList.add(new DoublePoints(points));
        var field = utils.convertToImageParams(pointsList, 100, 100);
        assertNotNull(field);
        assertEquals(100, field.points().length);
        assertEquals(100, field.points()[0].length);
        boolean foundRed = false;
        boolean foundGreen = false;
        boolean foundBlue = false;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                long color = field.points()[i][j].getColor();
                if (color == 0xFFFF0000) foundRed = true;
                if (color == 0xFF00FF00) foundGreen = true;
                if (color == 0xFF0000FF) foundBlue = true;
            }
        }
        assertTrue(foundRed || foundGreen || foundBlue);
    }

    @Test
    void testConvertToImageParamsEmpty() {
        List<DoublePoints> pointsList = new ArrayList<>();
        var field = utils.convertToImageParams(pointsList, 50, 50);
        assertNotNull(field);
        assertEquals(50, field.points().length);
        assertEquals(50, field.points()[0].length);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                assertEquals(0xFFFFFFFF, field.points()[i][j].getColor());
            }
        }
    }
}

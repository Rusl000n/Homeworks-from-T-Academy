package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.dto.*;
import academy.dto.inputs.AffineParams;
import academy.dto.inputs.InputParams;
import academy.dto.inputs.SizeParams;
import academy.dto.inputs.TransformFunctions;
import academy.dto.inputs.TransformFunctionsEnum;
import java.util.List;
import org.junit.jupiter.api.Test;

class FractalFlameRunnerTest {

    @Test
    void testSingleThread() {
        InputParams params = createTestParams();
        params.setThreads(1);
        params.setIterationCount(100);
        long startTime = System.currentTimeMillis();
        runFractalTest(params);
        long endTime = System.currentTimeMillis();
        System.out.println("1 поток: " + (endTime - startTime) + " мс");
        assertTrue(endTime - startTime >= 0);
    }

    @Test
    void testTwoThreads() {
        InputParams params = createTestParams();
        params.setThreads(2);
        params.setIterationCount(100);
        long startTime = System.currentTimeMillis();
        runFractalTest(params);
        long endTime = System.currentTimeMillis();
        System.out.println("2 потока: " + (endTime - startTime) + " мс");
        assertTrue(endTime - startTime >= 0);
    }

    @Test
    void testFourThreads() {
        InputParams params = createTestParams();
        params.setThreads(4);
        params.setIterationCount(100);
        long startTime = System.currentTimeMillis();
        runFractalTest(params);
        long endTime = System.currentTimeMillis();
        System.out.println("4 потока: " + (endTime - startTime) + " мс");
        assertTrue(endTime - startTime >= 0);
    }

    @Test
    void testEightThreads() {
        InputParams params = createTestParams();
        params.setThreads(8);
        params.setIterationCount(100);
        long startTime = System.currentTimeMillis();
        runFractalTest(params);
        long endTime = System.currentTimeMillis();
        System.out.println("8 потоков: " + (endTime - startTime) + " мс");
        assertTrue(endTime - startTime >= 0);
    }

    @Test
    void testThreadRunner() {
        try {
            FractalFlameRunner runner = new FractalFlameRunner();
            InputParams params = createTestParams();
            params.setIterationCount(100);
            params.setThreads(2);
            var method = FractalFlameRunner.class.getDeclaredMethod("threadRunner", InputParams.class);
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<DoublePoints> result = (List<DoublePoints>) method.invoke(runner, params);
            assertNotNull(result);
            assertEquals(2, result.size());
            int totalPoints = result.stream()
                    .mapToInt(dp -> dp.getDoublePointList().size())
                    .sum();
            assertEquals(100, totalPoints);

        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }

    private InputParams createTestParams() {
        InputParams params = new InputParams();
        params.setSize(new SizeParams(800, 600));
        params.setSeed(12345L);
        params.setIterationCount(1000);
        params.setOutputPath("test_output.png");
        List<AffineParams> affineList = List.of(
                new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0, 0xFFFF0000),
                new AffineParams(0.5, 0.0, 1.0, 0.0, 0.5, 1.0, 0xFF00FF00));
        params.setAffineParamsList(affineList);
        List<TransformFunctions> funcList = List.of(
                new TransformFunctions(TransformFunctionsEnum.LINEAR, 0.7),
                new TransformFunctions(TransformFunctionsEnum.SINUSOIDAL, 0.3));
        params.setTransformFunctionsList(funcList);
        return params;
    }

    private void runFractalTest(InputParams params) {
        try {
            FractalFlameRunner runner = new FractalFlameRunner();
            java.io.File tempFile = java.io.File.createTempFile("test_", ".png");
            params.setOutputPath(tempFile.getAbsolutePath());
            runner.run(params);
            assertTrue(tempFile.exists());
            assertTrue(tempFile.length() > 0);
            tempFile.delete();

        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }
}

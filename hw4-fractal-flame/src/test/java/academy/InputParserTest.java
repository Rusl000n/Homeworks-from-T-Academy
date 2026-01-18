package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.dto.inputs.AffineParams;
import academy.dto.inputs.InputParams;
import academy.dto.inputs.TransformFunctions;
import academy.dto.inputs.TransformFunctionsEnum;
import academy.ioModule.InputParser;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.junit.jupiter.api.Test;

class InputParserTest {

    private final InputParser parser = new InputParser();

    @Test
    void testParseAffineParams() {
        String input = "1.0,2.0,3.0,4.0,5.0,6.0/0.5,0.5,0.5,0.5,0.5,0.5";
        List<AffineParams> result = parser.parseAffineParams(input);

        assertEquals(2, result.size());

        assertEquals(1.0, result.get(0).getA(), 0.001);
        assertEquals(2.0, result.get(0).getB(), 0.001);
        assertEquals(3.0, result.get(0).getC(), 0.001);
        assertEquals(4.0, result.get(0).getD(), 0.001);
        assertEquals(5.0, result.get(0).getE(), 0.001);
        assertEquals(6.0, result.get(0).getF(), 0.001);

        assertEquals(0.5, result.get(1).getA(), 0.001);
        assertEquals(0.5, result.get(1).getF(), 0.001);
    }

    @Test
    void testParseAffineParamsEmpty() {
        String input = "";
        List<AffineParams> result = parser.parseAffineParams(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseAffineParamsNull() {
        List<AffineParams> result = parser.parseAffineParams(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseTransformFunctions() {
        String input = "linear:0.5,sinusoidal:0.3,spherical:0.2";
        List<TransformFunctions> result = parser.parseTransformFunctions(input);
        assertEquals(3, result.size());
        assertEquals(TransformFunctionsEnum.LINEAR, result.get(0).getTransformFunctionsEnum());
        assertEquals(0.5, result.get(0).getWeight(), 0.001);
        assertEquals(TransformFunctionsEnum.SINUSOIDAL, result.get(1).getTransformFunctionsEnum());
        assertEquals(0.3, result.get(1).getWeight(), 0.001);
        assertEquals(TransformFunctionsEnum.SPHERICAL, result.get(2).getTransformFunctionsEnum());
        assertEquals(0.2, result.get(2).getWeight(), 0.001);
    }

    @Test
    void testParseTransformFunctionsWithSpaces() {
        String input = "linear : 0.5 , sinusoidal : 0.5";
        List<TransformFunctions> result = parser.parseTransformFunctions(input);

        assertEquals(2, result.size());
        assertEquals(TransformFunctionsEnum.LINEAR, result.get(0).getTransformFunctionsEnum());
        assertEquals(TransformFunctionsEnum.SINUSOIDAL, result.get(1).getTransformFunctionsEnum());
    }

    @Test
    void testParseTransformFunctionsEmpty() {
        String input = "";
        List<TransformFunctions> result = parser.parseTransformFunctions(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseJsonConfigMissingFields() throws Exception {
        String json = "{}";
        File tempFile = File.createTempFile("test_config", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(json);
        }

        try {
            InputParams params = parser.parseJsonConfig(tempFile);
            assertNotNull(params);
        } finally {
            tempFile.delete();
        }
    }
}

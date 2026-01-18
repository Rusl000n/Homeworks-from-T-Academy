package academy.ioModule;

import static academy.utils.ConstantsConfig.BLACK;

import academy.dto.inputs.AffineParams;
import academy.dto.inputs.InputParams;
import academy.dto.inputs.TransformFunctions;
import academy.dto.inputs.TransformFunctionsEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public InputParams parseJsonConfig(File configFile) throws IOException {
        return objectMapper.readValue(configFile, InputParams.class);
    }

    public List<AffineParams> parseAffineParams(String input) {
        if (input == null || input.isEmpty()) return new ArrayList<>();
        List<AffineParams> list = new ArrayList<>();
        String[] blocks = input.split("/");
        for (String block : blocks) {
            String[] parts = block.split(",");
            list.add(new AffineParams(
                    Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]),
                    Double.parseDouble(parts[5]),
                    BLACK));
        }
        return list;
    }

    public List<TransformFunctions> parseTransformFunctions(String input) {
        if (input == null || input.isEmpty()) return new ArrayList<>();
        List<TransformFunctions> list = new ArrayList<>();
        String[] pairs = input.split(",");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            if (parts.length == 2) {
                String name = parts[0].trim().toUpperCase();
                double weight = Double.parseDouble(parts[1].trim());
                list.add(new TransformFunctions(TransformFunctionsEnum.valueOf(name), weight));
            }
        }
        return list;
    }
}

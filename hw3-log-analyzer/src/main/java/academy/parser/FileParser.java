package academy.parser;

import academy.io.InputInterface;
import academy.utils.factories.InputModuleFactory;
import java.io.IOException;
import java.util.List;

public class FileParser {

    public List<String> parseFile(String input) throws IOException {
        try {
            InputModuleFactory factory = new InputModuleFactory();
            InputInterface inputModule = factory.createModule(input);
            return inputModule.getFile(input);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    public List<String> getFileNames(String input) throws IOException {
        try {
            InputModuleFactory factory = new InputModuleFactory();
            InputInterface inputModule = factory.createModule(input);
            return inputModule.getFileNames(input);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}

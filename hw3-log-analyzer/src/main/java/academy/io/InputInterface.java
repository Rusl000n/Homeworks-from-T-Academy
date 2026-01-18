package academy.io;

import java.io.IOException;
import java.util.List;

public interface InputInterface {
    List<String> getFile(String input) throws IOException;

    List<String> getFileNames(String input) throws IOException;
}

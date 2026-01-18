package academy.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FormatCandidates implements Iterable<String> {

    private static final List<String> SUPPORTED_FORMATS = Arrays.asList("json", "markdown", "adoc");

    @Override
    public Iterator<String> iterator() {
        return SUPPORTED_FORMATS.iterator();
    }
}

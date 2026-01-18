package academy.utils.factories;

import academy.io.FileModule;
import academy.io.GlobModule;
import academy.io.HttpModule;
import academy.io.InputInterface;

public class InputModuleFactory {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final String GLOB_CHARS = "*?";

    public InputInterface createModule(String input) {
        if (isHttp(input)) {
            return new HttpModule();
        } else if (isGlobPattern(input)) {
            return new GlobModule();
        } else {
            return new FileModule();
        }
    }

    private boolean isHttp(String input) {
        return input.startsWith(HTTP_PREFIX) || input.startsWith(HTTPS_PREFIX);
    }

    private boolean isGlobPattern(String input) {
        if (isHttp(input)) {
            return false;
        }
        int lastSlash = input.lastIndexOf('/');
        int lastBackslash = input.lastIndexOf('\\');
        int lastSeparator = Math.max(lastSlash, lastBackslash);

        String fileName;
        if (lastSeparator >= 0 && lastSeparator < input.length() - 1) {
            fileName = input.substring(lastSeparator + 1);
        } else {
            fileName = input;
        }
        for (char c : GLOB_CHARS.toCharArray()) {
            if (fileName.indexOf(c) >= 0) {
                return true;
            }
        }
        return fileName.contains("[") || fileName.contains("]");
    }
}

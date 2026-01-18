package academy;

import academy.dto.OutputEnums;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "academy.ClassInspector run", 
         version = "1.0", 
         mixinStandardHelpOptions = true)
public class Application implements Runnable {

    @Option(names = {"-c", "--class"}, 
            required = true)
    private String className;

    @Option(names = {"-f", "--format"}, 
            defaultValue = "TEXT")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application())
            .setExecutionStrategy(new CommandLine.RunLast())
            .execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            if (!OutputEnums.isValid(format)) {
                System.err.println("Error: Unsupported format '" + format + "'. Supported formats: TEXT, JSON");
                System.exit(2);
            }
            Class<?> clazz = Class.forName(className);
            String result = ClassInspector.inspect(clazz, format);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
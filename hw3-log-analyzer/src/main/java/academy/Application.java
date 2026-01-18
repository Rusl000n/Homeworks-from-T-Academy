package academy;

import static academy.utils.constants.Constants.EXIT_FAILURE;
import static academy.utils.constants.Constants.EXIT_MISUSE;

import academy.runnerApp.RunnerLog;
import academy.utils.FormatCandidates;
import academy.utils.Validation;
import academy.utils.factories.OutputFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "Application Example", version = "Example 1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {
    private static final Logger logger = LogManager.getLogger(Application.class);

    private static final String UNDEFINED_PARAMETER = "undefined";

    public static void main(String[] args) {
        debugArgs(Arrays.asList(args));
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Option(
            names = {"--path", "-p"},
            description = "path or paths to .log .txt files",
            required = true,
            split = ",")
    private String[] paths;

    @Option(
            names = {"--format", "-f"},
            description = "output format: ${COMPLETION-CANDIDATES}",
            required = true,
            completionCandidates = FormatCandidates.class)
    private String format;

    @Option(
            names = {"--output", "-o"},
            description = "output file path",
            required = true)
    private String outputFile;

    @Override
    public void run() {
        try {
            OutputFactory outputFactory = new OutputFactory();
            outputFactory.createOutputModule(format);
            Validation validation = new Validation();
            validation.validateOutputFile(outputFile, format);
            validation.validateInputFiles(paths);
            RunnerLog runnerLog = new RunnerLog();
            runnerLog.run(paths, format, outputFile);
            logger.info("Application completed successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            System.exit(EXIT_MISUSE);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            System.exit(EXIT_FAILURE);
        }
    }

    // Note: нужно только для отладки, удалить в случае ненадобности
    @Deprecated(forRemoval = true)
    private static void debugArgs(List<String> args) {
        var argsPerParam = getArgumentsPerParameter(args);
        System.out.printf("Входные параметры программы: %s%n", argsPerParam);

        logPaths("Пути к лог-файлам", argsPerParam, "p", "path");
    }

    private static Map<String, List<String>> getArgumentsPerParameter(List<String> args) {
        var argsPerParameter = new HashMap<String, List<String>>();
        argsPerParameter.put(UNDEFINED_PARAMETER, new ArrayList<>());

        var queue = new ArrayDeque<>(args);
        String currentParameter = null;
        while (!queue.isEmpty()) {
            var element = queue.removeFirst();
            if (element.startsWith("-")) {
                currentParameter = element.startsWith("--") ? element.substring(2) : element.substring(1);
                argsPerParameter.putIfAbsent(currentParameter, new ArrayList<>());
            } else {
                argsPerParameter
                        .get(Optional.ofNullable(currentParameter).orElse(UNDEFINED_PARAMETER))
                        .add(element);
            }
        }

        return argsPerParameter;
    }

    private static void logPaths(String description, Map<String, List<String>> argsPerParam, String... params) {
        var paths = new ArrayList<String>();
        for (var param : params) {
            paths.addAll(argsPerParam.getOrDefault(param, List.of()));
        }
        System.out.printf(
                "%s: %s%n",
                description,
                paths.stream()
                        .map(it -> it.contains("*")
                                ? "glob: " + it
                                : "path: %s, exists: %s".formatted(it, Files.exists(Path.of(it))))
                        .collect(Collectors.joining(";")));
    }
}

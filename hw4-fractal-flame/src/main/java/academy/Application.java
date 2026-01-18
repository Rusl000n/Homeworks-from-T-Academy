package academy;

import academy.dto.inputs.AffineParams;
import academy.dto.inputs.InputParams;
import academy.dto.inputs.TransformFunctions;
import academy.ioModule.InputParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "FractalFlameGenerator",
        version = "1.0",
        mixinStandardHelpOptions = true,
        description = "Генератор фрактального пламени")
public class Application implements Runnable {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Application.class);

    @Option(
            names = {"-w", "--width"},
            description = "Ширина изображения")
    private Integer width;

    @Option(
            names = {"-h", "--height"},
            description = "Высота изображения")
    private Integer height;

    @Option(
            names = {"--seed"},
            description = "Начальное значение генератора")
    private Long seed;

    @Option(
            names = {"-i", "--iteration-count"},
            description = "Количество итераций")
    private Integer iterationCount;

    @Option(
            names = {"-o", "--output-path"},
            description = "Путь для сохранения PNG")
    private String outputPath;

    @Option(
            names = {"-t", "--threads"},
            description = "Количество потоков")
    private Integer threads;

    @Option(
            names = {"-ap", "--affine-params"},
            description = "Аффинные параметры: a1,b1,c1,d1,e1,f1/a2,b2,...")
    private String affineParamsString;

    @Option(
            names = {"-f", "--functions"},
            description = "Функции трансформации: функция1:вес1,функция2:вес2,...")
    private String functionsString;

    @Option(
            names = {"--config"},
            description = "Путь к JSON файлу конфигурации")
    private File configFile;

    private final InputParser parser = new InputParser();

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            logger.info("Старт программы");
            InputParams params = loadBaseParameters();
            applyCommandLineArguments(params);
            validateParameters(params);
            FractalFlameRunner runner = new FractalFlameRunner();
            runner.run(params);
            logger.info("Работа программы окончена");
        } catch (Exception e) {
            logger.info("ОШИБКА: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private InputParams loadBaseParameters() throws IOException {
        InputParams params;

        if (configFile != null && configFile.exists()) {
            params = parser.parseJsonConfig(configFile);
        } else {
            params = new InputParams();
        }
        return params;
    }

    private void applyCommandLineArguments(InputParams params) {
        if (width != null) {
            params.getSize().setWidth(width);
        }
        if (height != null) {
            params.getSize().setHeight(height);
        }
        if (seed != null) {
            params.setSeed(seed);
        }
        if (iterationCount != null) {
            params.setIterationCount(iterationCount);
        }
        if (outputPath != null) {
            params.setOutputPath(outputPath);
        }
        if (threads != null) {
            params.setThreads(threads);
        }
        if (affineParamsString != null && !affineParamsString.trim().isEmpty()) {
            List<AffineParams> affineParams = parser.parseAffineParams(affineParamsString);
            params.setAffineParamsList(affineParams);
        }
        if (functionsString != null && !functionsString.trim().isEmpty()) {
            List<TransformFunctions> functions = parser.parseTransformFunctions(functionsString);
            params.setTransformFunctionsList(functions);
        }
    }

    private void validateParameters(InputParams params) {
        if (params.getSize().getWidth() <= 0 || params.getSize().getHeight() <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными");
        }
        if (params.getIterationCount() <= 0) {
            throw new IllegalArgumentException("Количество итераций должно быть > 0");
        }
        if (params.getThreads() <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть > 0");
        }
        if (params.getAffineParamsList() == null || params.getAffineParamsList().isEmpty()) {
            throw new IllegalArgumentException("Не указаны аффинные параметры");
        }
        if (params.getTransformFunctionsList() == null
                || params.getTransformFunctionsList().isEmpty()) {
            throw new IllegalArgumentException("Не указаны функции трансформации");
        }
    }
}

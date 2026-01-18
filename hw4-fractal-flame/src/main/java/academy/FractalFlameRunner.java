package academy;

import academy.affineCalculation.CountingPoints;
import academy.dto.DoublePoints;
import academy.dto.Field;
import academy.dto.inputs.InputParams;
import academy.ioModule.ImageWriter;
import academy.utils.Utils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;

public class FractalFlameRunner {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Application.class);
    private final CountingPoints countingPoints = new CountingPoints();
    private final Utils utils = new Utils();
    private final ImageWriter imageWriter = new ImageWriter();

    public void run(InputParams inputParams) {
        try {
            logger.info("Начинаем вычисления...");
            logger.info("Итераций: " + inputParams.getIterationCount());
            logger.info("Потоков: " + inputParams.getThreads());

            List<DoublePoints> listPoints = threadRunner(inputParams);
            Field field = utils.convertToImageParams(
                    listPoints,
                    inputParams.getSize().getHeight(),
                    inputParams.getSize().getWidth());
            Path outputFile = Path.of(inputParams.getOutputPath());
            imageWriter.writeFractalImage(field, outputFile.toFile());

            logger.info("Изображение сохранено: " + outputFile);

        } catch (Exception e) {
            logger.info("Ошибка! " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<DoublePoints> threadRunner(InputParams inputParams) throws InterruptedException {
        List<DoublePoints> doublePointsList = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        int countIteration = inputParams.getIterationCount() / inputParams.getThreads();
        logger.info("Итераций на поток: " + countIteration);

        for (int i = 0; i < inputParams.getThreads(); ++i) {
            long seedThread = inputParams.getSeed() + i;
            Thread thread = Thread.ofPlatform().start(() -> {
                DoublePoints doublePoints = countingPoints.calculateField(inputParams, countIteration, seedThread);
                synchronized (doublePointsList) {
                    doublePointsList.add(doublePoints);
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return doublePointsList;
    }
}

package academy.runnerApp;

import academy.dto.ParseFiles;
import academy.dto.analytics.AnalyticsDate;
import academy.io.OutputInterface;
import academy.logAnalyzer.Analyzer;
import academy.parser.FileParser;
import academy.parser.LogParser;
import academy.utils.factories.OutputFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunnerLog {

    private FileParser fileParser;
    private LogParser parse;
    private Analyzer analyzer;
    private OutputFactory outputFactory;

    public RunnerLog() {
        fileParser = new FileParser();
        parse = new LogParser();
        analyzer = new Analyzer();
        outputFactory = new OutputFactory();
    }

    private static final Logger logger = LogManager.getLogger(RunnerLog.class);

    public void run(String[] inputs, String format, String outputFile) throws IOException {
        try {
            List<ParseFiles> parseFilesList = toParseFiles(inputs);
            logger.info("Successfully parsed {} files", parseFilesList.size());
            AnalyticsDate analyticsDate = analyzer.analyze(parseFilesList);
            logger.info("Analysis completed. Total requests: {}", analyticsDate.getTotalRequestsCount());

            OutputInterface outputModule = outputFactory.createOutputModule(format);
            outputModule.writeFile(outputFile, analyticsDate);

        } catch (Exception e) {
            logger.error("Failed to process logs", e);
            throw new IOException(e);
        }
    }

    private List<ParseFiles> toParseFiles(String[] inputs) throws IOException {
        try {
            return Arrays.stream(inputs)
                    .map(input -> {
                        try {
                            List<String> lines = fileParser.parseFile(input);
                            return new ParseFiles(input, parse.parseList(lines));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}

package academy;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "Benchmark run", version = "1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StudentBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    @Override
    public void run() {}
}

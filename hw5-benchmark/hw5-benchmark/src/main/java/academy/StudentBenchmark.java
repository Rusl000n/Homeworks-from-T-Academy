package academy;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class StudentBenchmark {

    @Benchmark
    public void directCall(StudentBenchmarkState state, Blackhole bh) {
        String name = state.student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflectionCall(StudentBenchmarkState state, Blackhole bh) throws Exception {
        String name = (String) state.reflectionMethod.invoke(state.student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandleCall(StudentBenchmarkState state, Blackhole bh) throws Throwable {
        String name = (String) state.methodHandle.invoke(state.student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactoryCall(StudentBenchmarkState state, Blackhole bh) {
        String name = state.lambdaFunction.apply(state.student);
        bh.consume(name);
    }
}

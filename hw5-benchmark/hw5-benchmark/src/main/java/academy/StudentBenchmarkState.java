package academy;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.Function;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class StudentBenchmarkState {

    public Student student;
    public Method reflectionMethod;
    public MethodHandle methodHandle;
    public Function<Student, String> lambdaFunction;

    @Setup(Level.Trial)
    public void setup() throws Throwable {
        student = new Student("Sayfullin Ruslan", 19);

        reflectionMethod = Student.class.getMethod("name");

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        methodHandle = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));

        CallSite site = LambdaMetafactory.metafactory(
                lookup,
                "apply",
                MethodType.methodType(Function.class),
                MethodType.methodType(Object.class, Object.class),
                lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class)),
                MethodType.methodType(String.class, Student.class));
        lambdaFunction = (Function<Student, String>) site.getTarget().invoke();
    }
}

package academy;

import academy.analyze.ClassAnalyzer;
import academy.dto.ClassDTO;
import academy.io.Writer;
import academy.io.WriterFactory;

public class ClassInspector {
    private static final ClassAnalyzer Analyzer = new ClassAnalyzer();
    private static final InstanceCreator Creator = new InstanceCreator();
    
    public static String inspect(Class<?> clazz, String format) {
        ClassDTO classDTO = Analyzer.analyze(clazz);
        Writer writer = WriterFactory.createWriter(format);
        return writer.write(classDTO);
    }
    
    public static <T> T create(Class<T> clazz) {
        return Creator.createInstance(clazz);
    }
}
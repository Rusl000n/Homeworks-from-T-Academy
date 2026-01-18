package academy.io;

import academy.dto.analytics.AnalyticsDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JacksonModule implements OutputInterface {
    private static final Logger logger = LogManager.getLogger(JacksonModule.class);
    private ObjectMapper objectMapper;

    public JacksonModule() {
        objectMapper = new ObjectMapper();
        setDataType();
    }

    @Override
    public void writeFile(String output, AnalyticsDate analyticsDate) throws Exception {
        try {
            Path path = Path.of(output);
            objectMapper.writeValue(path.toFile(), analyticsDate);
        } catch (Exception e) {
            logger.error("Error writing JSON to file: {}", output, e);
            throw new IOException("Error write data to file", e);
        }
    }

    private void setDataType() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}

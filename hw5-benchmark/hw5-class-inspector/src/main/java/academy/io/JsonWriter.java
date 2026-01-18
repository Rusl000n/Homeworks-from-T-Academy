package academy.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import academy.dto.ClassDTO;

public class JsonWriter implements Writer {
    
    private static final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public String write(ClassDTO classDTO) {
        try {
            return objectMapper.writeValueAsString(classDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error writing JSON", e);
        }
    }
}
package academy.io;

import academy.dto.ClassDTO;
import java.util.Map;

public class TextWriter implements Writer {
    
    @Override
    public String write(ClassDTO classDTO) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Class: ").append(classDTO.getClassName()).append("\n");
        
        if (classDTO.getSuperclass() != null) {
            builder.append("Superclass: ").append(classDTO.getSuperclass()).append("\n");
        }
        
        addInterfaces(builder, classDTO);
        addFields(builder, classDTO);
        addMethods(builder, classDTO);
        addAnnotations(builder, classDTO);
        addHierarchy(builder, classDTO);
        
        return builder.toString();
    }
    
    private void addInterfaces(StringBuilder builder, ClassDTO classDTO) {
        if (classDTO.getInterfaces() != null && !classDTO.getInterfaces().isEmpty()) {
            builder.append("Interfaces:\n");
            classDTO.getInterfaces().forEach(item -> builder
                .append("  - ")
                .append(item)
                .append("\n"));
        }
    }
    
    private void addFields(StringBuilder builder, ClassDTO classDTO) {
        if (classDTO.getFields() != null && !classDTO.getFields().isEmpty()) {
            builder.append("Fields:\n");
            classDTO.getFields().forEach(field -> 
                builder.append("  - ")
                  .append(field.getAccess()).append(" ")
                  .append(field.getName()).append(" (")
                  .append(field.getType()).append(")\n")
            );
        }
    }
    
    private void addMethods(StringBuilder builder, ClassDTO classDTO) {
        if (classDTO.getMethods() != null && !classDTO.getMethods().isEmpty()) {
            builder.append("Methods:\n");
            classDTO.getMethods().forEach(method -> {
                builder.append("  - ")
                  .append(method.getAccess()).append(" ")
                  .append(method.getName()).append("("); 
                if (method.getParameters() != null && !method.getParameters().isEmpty()) {
                    for (int i = 0; i < method.getParameters().size(); i++) {
                        if (i > 0) {
                                builder.append(", ");
                        }
                        builder.append(method.getParameters().get(i));
                    }
                }
                builder.append(") : ").append(method.getReturnType()).append("\n");
            });
        }
    }
    
    private void addAnnotations(StringBuilder builder, ClassDTO classDTO) {
        if (classDTO.getAnnotations() != null && !classDTO.getAnnotations().isEmpty()) {
            builder.append("Annotations:\n");
            classDTO.getAnnotations().forEach(annotation -> 
                builder.append("  - @").append(annotation).append("\n")
            );
        }
    }
    
    private void addHierarchy(StringBuilder builder, ClassDTO classDTO) {
        if (classDTO.getHierarchy() != null && !classDTO.getHierarchy().isEmpty()) {
            builder.append("Hierarchy:\n");
            formatHierarchy(builder, classDTO.getHierarchy(), 0);
        }
    }
    
    private void formatHierarchy(StringBuilder builder, Map<String, Object> hierarchy, int depth) {
        hierarchy.forEach((key, value) -> {
            builder.append("  ".repeat(depth)).append(key).append("\n");
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> childMap = (Map<String, Object>) value;
                formatHierarchy(builder, childMap, depth + 1);
            }
        });
    }
}
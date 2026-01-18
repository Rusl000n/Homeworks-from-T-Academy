package academy.analyze;

import academy.dto.ClassDTO;
import academy.dto.FieldDTO;
import academy.dto.MethodDTO;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.*;

public class ClassAnalyzer {
    
    public ClassDTO analyze(Class<?> clazz) {
        return ClassDTO.builder()
            .className(clazz.getName())
            .superclass(getSuperclassName(clazz))
            .interfaces(getInterfaceNames(clazz))
            .fields(getFields(clazz))
            .methods(getMethods(clazz))
            .annotations(getClassAnnotations(clazz))
            .hierarchy(getHierarchy(clazz))
            .build();
    }
    
    private String getSuperclassName(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        return (superclass != null && !superclass.equals(Object.class))  ? superclass.getName() : null;
    }
    
    private List<String> getInterfaceNames(Class<?> clazz) {
        List<String> interfaces = new ArrayList<>();
        for (Class<?> iface : clazz.getInterfaces()) {
            interfaces.add(iface.getName());
        }
        return interfaces;
    }
    
    private List<FieldDTO> getFields(Class<?> clazz) {
        List<FieldDTO> fields = new ArrayList<>();
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            FieldDTO fieldDTO = FieldDTO.builder()
                .access(Modifier.toString(field.getModifiers()))
                .name(field.getName())
                .type(field.getType().getSimpleName())
                .annotations(getAnnotationNames(field.getAnnotations()))
                .build();
            fields.add(fieldDTO);
        }
        return fields;
    }
    
    private List<MethodDTO> getMethods(Class<?> clazz) {
        List<MethodDTO> methods = new ArrayList<>();
        for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
            MethodDTO methodDTO = MethodDTO.builder()
                .access(Modifier.toString(method.getModifiers()))
                .name(method.getName())
                .returnType(method.getReturnType().getSimpleName())
                .parameters(getParameterTypes(method))
                .annotations(getAnnotationNames(method.getAnnotations()))
                .build();
            methods.add(methodDTO);
        }
        return methods;
    }
    
    private List<String> getParameterTypes(java.lang.reflect.Method method) {
        List<String> params = new ArrayList<>();
        for (Class<?> paramType : method.getParameterTypes()) {
            params.add(paramType.getSimpleName());
        }
        return params;
    }
    
    private List<String> getClassAnnotations(Class<?> clazz) {
        return getAnnotationNames(clazz.getAnnotations());
    }
    
    private List<String> getAnnotationNames(Annotation[] annotations) {
        List<String> annotationNames = new ArrayList<>();
        for (Annotation annotation : annotations) {
            annotationNames.add(annotation.annotationType().getSimpleName());
        }
        return annotationNames;
    }
    
    private Map<String, Object> getHierarchy(Class<?> clazz) {
        Map<String, Object> hierarchy = new LinkedHashMap<>();
        addToHierarchy(clazz, hierarchy);
        return hierarchy;
    }
    
    private void addToHierarchy(Class<?> clazz, Map<String, Object> map) {
        if (clazz == null || clazz.equals(Object.class)) {
            return;
        }
        Map<String, Object> children = new LinkedHashMap<>();
        addToHierarchy(clazz.getSuperclass(), children);
        try {
            Class<?>[] permitted = clazz.getPermittedSubclasses();
            if (permitted != null) {
                for (Class<?> subclass : permitted) {
                    Map<String, Object> subMap = new HashMap<>();
                    addToHierarchy(subclass, subMap);
                    children.put(subclass.getSimpleName(), subMap.isEmpty() ? new HashMap<>() : subMap);
                }
            }
        } catch (Exception e) {}
        String className = clazz.getSimpleName();
        map.put(className, children.isEmpty() ? new HashMap<>() : children);
    }
}
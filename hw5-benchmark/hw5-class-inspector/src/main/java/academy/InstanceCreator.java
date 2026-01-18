package academy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import academy.utils.RandomUtils;

public class InstanceCreator {
    private static final int MAX_DEPTH = 3;
    private int currentDepth = 0;
    
    public <T> T createInstance(Class<T> clazz) {
        if (currentDepth > MAX_DEPTH) {
            return null;
        }
        try {
            currentDepth++;
            Constructor<T> constructor = findConstructor(clazz);
            constructor.setAccessible(true);
            Object[] args = Arrays.stream(constructor.getParameterTypes())
                .map(paramType -> RandomUtils.generateValue(paramType))
                .toArray();
            T instance = constructor.newInstance(args);
            fillFields(instance, clazz);
            return instance;
        } catch (Exception e) {
            return null;
        } finally {
            currentDepth--;
        }
    }
    
    private <T> Constructor<T> findConstructor(Class<T> clazz) throws NoSuchMethodException {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors.length > 0) {
                return (Constructor<T>) constructors[0];
            }
            throw e;
        }
    }
    
    private <T> void fillFields(T instance, Class<T> clazz) throws IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object value = RandomUtils.generateValue(field.getType());
            if (value != null) {
                field.set(instance, value);
            }
        }
    }
}
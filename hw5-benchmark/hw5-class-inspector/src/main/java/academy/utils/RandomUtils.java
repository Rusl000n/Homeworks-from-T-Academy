package academy.utils;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();
    private static final String Characters = "abcdefghijklmnopqrstuvwxyz";
    
    public static Object generateValue(Class<?> type) {
        if (type == null) {
            return null;
        }
        if (type.isPrimitive() || type.equals(String.class) || type.equals(Boolean.class) || 
            type.equals(Character.class) || type.equals(LocalDate.class) || type.equals(LocalDateTime.class)) {
            return generateSimpleValue(type);
        }
        return createSimpleInstance(type);
    }
    
    private static Object generateSimpleValue(Class<?> type) {
        String typeName = type.getName();
        return switch (typeName) {
            case "int", "java.lang.Integer" -> random.nextInt(100);
            case "long", "java.lang.Long" -> random.nextLong();
            case "double", "java.lang.Double" -> random.nextDouble() * 100;
            case "float", "java.lang.Float" -> random.nextFloat() * 100;
            case "boolean", "java.lang.Boolean" -> random.nextBoolean();
            case "byte", "java.lang.Byte" -> (byte) random.nextInt(256);
            case "short", "java.lang.Short" -> (short) random.nextInt(32768);
            case "char", "java.lang.Character"-> generateChar();
            case "java.lang.String" -> generateString();
            case "java.time.LocalDate" -> LocalDate.now().plusDays(random.nextInt(365));
            case "java.time.LocalDateTime"-> LocalDateTime.now().plusDays(random.nextInt(365));
            default -> null;
        };
    }
    
    private static String generateString() {
        int length = 5 + random.nextInt(10);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(Characters.charAt(random.nextInt(Characters.length())));
        }
        return sb.toString();
    }
    
    private static char generateChar() {
        return (char) ('A' + random.nextInt(26));
    }
    
    private static Object createSimpleInstance(Class<?> type) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
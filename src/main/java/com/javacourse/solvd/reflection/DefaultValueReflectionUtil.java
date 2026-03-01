package com.javacourse.solvd.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;

public class DefaultValueReflectionUtil {

    public <T> T initWithDefaults(Class<T> clazz) {
        try {
            T object = clazz.getConstructor().newInstance();
            return initWithDefaults(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public <T> T initWithDefaults(T object) {
        if (object == null) {
            return null;
        }
        Class<T> clazz = (Class<T>) object.getClass();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            Annotation annotation = field.getAnnotation(DefaultValue.class);
            if (annotation != null) {
                boolean isAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    Object val = field.get(object);
                    Class<?> type = field.getType();
                    if (val == null || isPrimitiveDefault(type, val)) {
                        DefaultValue defaultValue = (DefaultValue) annotation;
                        String value = defaultValue.value();
                        val = parseValue(type, value);
                        field.set(object, val);
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(isAccessible);
                }
            }
        });
        return object;
    }

    private boolean isPrimitiveDefault(Class<?> type, Object val) {
        if (!type.isPrimitive()) return false;
        switch (type.getName()) {
            case "boolean":
                return !((boolean) val);
            case "byte":
                return (byte) val == 0;
            case "char":
                return ((char) val) == 0;
            case "short":
                return ((short) val) == 0;
            case "int":
                return ((int) val) == 0;
            case "long":
                return ((long) val) == 0;
            case "float":
                return ((float) val) == 0;
            case "double":
                return ((double) val) == 0;
        }
        return false;
    }

    private Object parseValue(Type type, String value) {
        if (type.equals(String.class)) {
            return value;
        }
        return switch (type.getTypeName()) {
            case "boolean", "java.lang.Boolean" -> Boolean.parseBoolean(value);
            case "byte", "java.lang.Byte" -> Byte.parseByte(value);
            case "char", "java.lang.Character" -> value.charAt(0);
            case "short", "java.lang.Short" -> Short.parseShort(value);
            case "int", "java.lang.Integer" -> Integer.parseInt(value);
            case "long", "java.lang.Long" -> Long.parseLong(value);
            case "float", "java.lang.Float" -> Float.parseFloat(value);
            case "double", "java.lang.Double" -> Double.parseDouble(value);
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }
}

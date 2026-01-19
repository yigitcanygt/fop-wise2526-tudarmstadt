package h10;

import com.google.common.primitives.Primitives;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class ReflectionUtilsPublic {

    public static void setFieldValue(Object instance, String fieldName, Object value) {
        try {
            Class<?> objectClass = instance.getClass();
            Field declaredField;
            try {
                declaredField = objectClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                declaredField = getSuperClassesIncludingSelf(objectClass).stream()
                    .filter((c) -> List.of(c.getDeclaredFields()).stream()
                        .map(Field::getName)
                        .anyMatch(name -> name.equals(fieldName))
                    )
                    .map((c) -> {
                        try {
                            return c.getDeclaredField(fieldName);
                        } catch (NoSuchFieldException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFieldException(e.getMessage()));
            }

            //best case field in non Final
            if (!Modifier.isFinal(declaredField.getModifiers())) {
                try {
                    declaredField.setAccessible(true);
                    declaredField.set(instance, value);
                    return;
                } catch (Exception ignored) {
                }
            }

            //field has setter
            Optional<Method> setter = Arrays
                .stream(objectClass.getDeclaredMethods())
                .filter(
                    m -> m.getName().equalsIgnoreCase("set" + fieldName)
                ).findFirst();
            if (setter.isPresent()) {
                setter.get().invoke(instance, value);
                return;
            }

            //rely on Unsafe to set value
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);

            Field theInternalUnsafeField = Unsafe.class.getDeclaredField("theInternalUnsafe");
            theInternalUnsafeField.setAccessible(true);
            Object theInternalUnsafe = theInternalUnsafeField.get(null);

            Method offset = Class.forName("jdk.internal.misc.Unsafe").getMethod("objectFieldOffset", Field.class);
            unsafe.putBoolean(offset, 12, true);

            switch (value) {
                case Boolean val -> unsafe.putBoolean(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                case Character val -> unsafe.putChar(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                case Short val -> unsafe.putShort(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                case Integer val -> unsafe.putInt(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                case Long val -> unsafe.putLong(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                case Double val -> unsafe.putDouble(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                case Float val -> unsafe.putFloat(instance, (long) offset.invoke(theInternalUnsafe, declaredField), val);
                default -> unsafe.putObject(instance, (long) offset.invoke(theInternalUnsafe, declaredField), value);
            }
        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getFieldValue(Object instance, String fieldName) {
        Field f;
        Class<?> fieldType = null;
        try {
            f = instance.getClass().getDeclaredField(fieldName);

            try {
                f.setAccessible(true);
                return (T) f.get(instance);
            } catch (Exception ignored) {
            }

            fieldType = f.getType();
            if (Primitives.isWrapperType(fieldType)) {
                fieldType = Primitives.unwrap(fieldType);
            }

            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);

            Field theInternalUnsafeField = Unsafe.class.getDeclaredField("theInternalUnsafe");
            theInternalUnsafeField.setAccessible(true);
            Object theInternalUnsafe = theInternalUnsafeField.get(null);

            Method offset = Class.forName("jdk.internal.misc.Unsafe").getMethod("objectFieldOffset", Field.class);
            unsafe.putBoolean(offset, 12, true);

            Object fieldValue;
            if (boolean.class == fieldType) {
                fieldValue = unsafe.getBoolean(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (byte.class == fieldType) {
                fieldValue = unsafe.getByte(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (short.class == fieldType) {
                fieldValue = unsafe.getShort(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (int.class == fieldType) {
                fieldValue = unsafe.getInt(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (long.class == fieldType) {
                fieldValue = unsafe.getLong(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (float.class == fieldType) {
                fieldValue = unsafe.getFloat(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (double.class == fieldType) {
                fieldValue = unsafe.getDouble(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else if (char.class == fieldType) {
                fieldValue = unsafe.getChar(instance, (long) offset.invoke(theInternalUnsafe, f));
            } else {
                fieldValue = unsafe.getObject(instance, (long) offset.invoke(theInternalUnsafe, f));
            }
            return (T) fieldValue;
        } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(
                "Could not set value for Field %s(%s) in %s. Please do not access this field.".formatted(
                    fieldName,
                    fieldType,
                    instance.getClass()
                ), e
            );
        }
    }

    public static void copyFields(Object source, Object dest) {
        for (Field f : source.getClass().getDeclaredFields()) {
            setFieldValue(dest, f.getName(), getFieldValue(source, f.getName()));
        }
    }

    public static boolean actsLikePrimitive(Class<?> type) {
        return type.isPrimitive() ||
            Enum.class.isAssignableFrom(type) ||
            Primitives.isWrapperType(type) ||
            type == String.class;
    }

    public static List<Class<?>> getSuperClassesIncludingSelf(Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        Deque<Class<?>> classDeque = new ArrayDeque<>();

        classDeque.add(clazz);

        while ((clazz = classDeque.peekFirst()) != null) {
            classDeque.pop();

            classes.add(clazz);
            if (clazz.getSuperclass() != null) {
                classDeque.add(clazz.getSuperclass());
            }
            if (clazz.getInterfaces().length > 0) {
                classDeque.addAll(List.of(clazz.getInterfaces()));
            }

        }
        return classes;
    }

    public static boolean isObjectMethod(Method methodToCheck) {
        List<String> objectMethods =
            List.of("getClass", "hashCode", "equals", "clone", "toString", "notify", "notifyAll", "wait", "finalize");
        return objectMethods.contains(methodToCheck.getName());
    }
}

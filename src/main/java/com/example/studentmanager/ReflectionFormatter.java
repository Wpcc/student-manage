package com.example.studentmanager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ReflectionFormatter {
  private ReflectionFormatter() {

  }

  public static String formatAnnotatedFields(Object target) {
    Objects.requireNonNull(target, "target 不能为空");

    List<Field> annotatedFields = new ArrayList<>();

    Class<?> currentClass = target.getClass();

    while (currentClass != null && currentClass != Object.class) {
      for (Field field : currentClass.getDeclaredFields()) {
        if (field.isAnnotationPresent(DisplayField.class)) {
          annotatedFields.add(field);
        }
      }

      currentClass = currentClass.getSuperclass();
    }

    annotatedFields.sort(Comparator.comparingInt(field -> field.getAnnotation(DisplayField.class).order()));

    return annotatedFields.stream()
        .map((Field field) -> readField(field, target))
        .collect(Collectors.joining(", "));
  }

  private static String readField(Field field, Object target) {
    try {
      field.setAccessible(true);

      DisplayField annotation = field.getAnnotation(DisplayField.class);

      Object value = field.get(target);

      return annotation.label() + "=" + value;
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("读取字段失败", e);
    }
  }

  public static Object invokeNoArgMethod(Object target, String methodName) {
    Objects.requireNonNull(target, "target 不能为空");

    try {
      Method method = target.getClass().getMethod(methodName);
      return method.invoke(target);
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new IllegalArgumentException("调用方法失败：" + methodName, e);
    }
  }
}

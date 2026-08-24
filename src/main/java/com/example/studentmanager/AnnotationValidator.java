package com.example.studentmanager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnnotationValidator {

  public static List<String> validate(Object target) {
    Objects.requireNonNull(target, "target 不能为空");

    List<String> errors = new ArrayList<>();

    for (Field field : target.getClass().getDeclaredFields()) {
      NotBlank annotation = field.getAnnotation(NotBlank.class);
      if (annotation == null) {
        continue;
      }

      try {
        field.setAccessible(true);
        Object value = field.get(target);
        if (value == null || (value instanceof String text && text.isBlank())) {
          errors.add(annotation.message());
        }
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("读取字段失败", e);
      }

    }

    return errors;
  }
}

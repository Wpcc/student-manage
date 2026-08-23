package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReflectionFormatterTest {

  @Test
  void shouldFormatAnnotatedFieldsIncludingParentFields() {
    Student student = new Student(1001, "张三", 18);

    String formatted = ReflectionFormatter.formatAnnotatedFields(student);

    assertEquals("学号=1001, 姓名=张三, 年龄=18", formatted);
  }

  @Test
  void shouldInvokePublicNoArgMethodByReflection() {
    Student student = new Student(1001, "张三", 18);

    Object summary = ReflectionFormatter.invokeNoArgMethod(student, "getSummary");

    assertEquals(student.getSummary(), summary);
  }
}

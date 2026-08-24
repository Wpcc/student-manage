package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  @Test
  void shouldFormatCourseWithCustomDelimiter() {
    Course course = new Course("Java 核心", 40);

    String formatted = ReflectionFormatter.formatAnnotatedFields(course, " | ");

    assertEquals("课程=Java 核心 | 课时=40", formatted);
  }

  @Test
  void shouldRejectNullTargetOrDelimiter() {
    Course course = new Course("Java 核心", 40);

    assertThrows(NullPointerException.class,
        () -> ReflectionFormatter.formatAnnotatedFields(null));
    assertThrows(NullPointerException.class,
        () -> ReflectionFormatter.formatAnnotatedFields(course, null));
  }
}

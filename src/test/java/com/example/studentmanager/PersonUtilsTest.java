package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PersonUtilsTest {

  @Test
  void shouldCalculateAverageAgeForStudents() {
    List<Student> students = List.of(
        new Student(1001, "张三", 18),
        new Student(1002, "李四", 21),
        new Student(1003, "王五", 24));

    double averageAge = PersonUtils.calculateAverageAge(students);

    assertEquals(21.0, averageAge);
  }

  @Test
  void shouldCopyStudentsToPersonList() {
    List<Student> source = List.of(
        new Student(1001, "张三", 18),
        new Student(1002, "李四", 20));
    List<Person> target = new ArrayList<>();

    PersonUtils.copyStudents(target, source);

    assertEquals(2, target.size());
    assertEquals("张三", target.get(0).getName());
    assertEquals("李四", target.get(1).getName());
  }
}

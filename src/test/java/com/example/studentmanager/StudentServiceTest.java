package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentServiceTest {
  @Test
  void shouldFindStudentAfterAdding() {
    // 创建 StudentService
    // 新增学号 1001 的学生
    // 查询 1001
    // 使用 assertNotNull 和 assertEquals 验证结果
    StudentService studentService = new StudentService();

    Student student = new Student(1001, "张三", 18);
    studentService.addStudent(student);

    Student foundStudent = studentService.findById(1001);

    assertNotNull(foundStudent);
    assertEquals(1001, foundStudent.getId());
    assertEquals("张三", foundStudent.getName());
    assertEquals(18, foundStudent.getAge());
  }

  @Test
  void shouldThrowExceptionWhenIdIsDuplicated() {
    // 先新增 1001
    // 再新增 1001
    // 用 assertThrows 验证 DuplicateStudentException

    StudentService studentService = new StudentService();
    Student student = new Student(1001, "张三", 18);
    studentService.addStudent(student);

    assertThrows(
        DuplicateStudentException.class,
        () -> studentService.addStudent(student));
  }

  @Test
  void shouldChangeAgeWhenStudentExists() {
    // 新增学生
    // changeAge(1001, 20)
    // assertTrue(...)
    // 查询后 assertEquals(20, ...)

    StudentService studentService = new StudentService();
    Student student = new Student(1001, "张三", 18);
    studentService.addStudent(student);

    assertTrue(studentService.changeAge(1001, 22));

    Student foundStudent = studentService.findById(1001);
    assertEquals(22, foundStudent.getAge());
  }

  @Test
  void shouldFindOnlyAdultStudents() {
    // 新增 17 岁与 20 岁学生
    // 调用 findAdults()
    // 验证结果数量为 1，且学生学号正确

    StudentService studentService = new StudentService();
    Student student1 = new Student(1001, "张三", 17);
    Student student2 = new Student(1002, "小明", 20);
    studentService.addStudent(student1);
    studentService.addStudent(student2);

    List<Student> adultStudents = studentService.findAdults();

    assertEquals(1, adultStudents.size());
    assertEquals(1002, adultStudents.get(0).getId());
  }

}

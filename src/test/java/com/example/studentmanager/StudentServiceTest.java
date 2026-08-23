package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

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

    Student foundStudent = studentService.findById(1001).orElseThrow();

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

    Student foundStudent = studentService.findById(1001).orElseThrow();
    assertEquals(22, foundStudent.getAge());

    assertTrue(studentService.findById(9999).isEmpty());
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

  @Test
  void shouldRejectNonPositiveStudentId() {
    assertThrows(IllegalArgumentException.class,
        () -> new Student(0, "张三", 18));
  }

  @Test
  void shouldRejectBlankStudentName() {
    assertThrows(IllegalArgumentException.class,
        () -> new Student(1001, "  ", 18));
  }

  @Test
  void shouldRejectAgeOutsideValidRange() {
    assertThrows(IllegalArgumentException.class,
        () -> new Student(1001, "张三", 151));
  }

  @Test
  void shouldRejectInvalidAgeWhenChangingStudentAge() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 18));

    assertThrows(IllegalArgumentException.class,
        () -> studentService.changeAge(1001, 151));
    assertEquals(18, studentService.findById(1001).orElseThrow().getAge());
  }

  @Test
  void shouldSortStudentsByAgeAscendingWithGenericStrategy() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 20));
    studentService.addStudent(new Student(1002, "李四", 18));
    studentService.addStudent(new Student(1003, "王五", 19));

    SortStrategy<Student> strategy = new AgeAscendingStrategy();
    List<Student> sortedStudents = studentService.findAllOrder(strategy);

    assertEquals(List.of(1002, 1003, 1001),
        sortedStudents.stream().map(Student::getId).toList());
  }

  @Test
  void shouldFindStudentsMatchingCustomCondition() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 17));
    studentService.addStudent(new Student(1002, "李四", 20));
    studentService.addStudent(new Student(1003, "王五", 22));

    List<Student> matchedStudents =
        studentService.findByCondition(student -> student.getAge() >= 20);

    assertEquals(2, matchedStudents.size());
    assertTrue(matchedStudents.stream().anyMatch(student -> student.getId() == 1002));
    assertTrue(matchedStudents.stream().anyMatch(student -> student.getId() == 1003));
  }

  @Test
  void shouldTreatEighteenYearOldStudentAsAdult() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 18));

    assertEquals(1, studentService.findAdults().size());
  }

  @Test
  void shouldReturnOldestAdultStudentSummaries() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "小明", 17));
    studentService.addStudent(new Student(1002, "张三", 20));
    studentService.addStudent(new Student(1003, "李四", 22));
    studentService.addStudent(new Student(1004, "王五", 30));

    List<String> summaries = studentService.findTopAdultSummaries(2);

    assertEquals(List.of(
        "学生：王五, 学号：1004, 年龄：30",
        "学生：李四, 学号：1003, 年龄：22"), summaries);
  }

  @Test
  void shouldRejectNonPositiveLimitWhenFindingTopAdults() {
    StudentService studentService = new StudentService();

    assertThrows(IllegalArgumentException.class,
        () -> studentService.findTopAdultSummaries(0));
  }

  @Test
  void shouldGroupStudentsByAge() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 18));
    studentService.addStudent(new Student(1002, "李四", 18));
    studentService.addStudent(new Student(1003, "王五", 20));

    Map<Integer, List<Student>> studentsByAge = studentService.groupStudentsByAge();

    assertEquals(2, studentsByAge.get(18).size());
    assertEquals(1, studentsByAge.get(20).size());
  }

  @Test
  void shouldPartitionStudentsByAdultStatus() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 17));
    studentService.addStudent(new Student(1002, "李四", 18));

    Map<Boolean, List<Student>> studentsByAdultStatus =
        studentService.partitionStudentsByAdult();

    assertEquals(1, studentsByAdultStatus.get(false).size());
    assertEquals(1001, studentsByAdultStatus.get(false).get(0).getId());
    assertEquals(1, studentsByAdultStatus.get(true).size());
    assertEquals(1002, studentsByAdultStatus.get(true).get(0).getId());
  }

  @Test
  void shouldReturnEmptyMinorGroupWhenAllStudentsAreAdults() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 18));

    Map<Boolean, List<Student>> studentsByAdultStatus =
        studentService.partitionStudentsByAdult();

    assertTrue(studentsByAdultStatus.get(false).isEmpty());
  }

  @Test
  void shouldReturnEmptyAgeStatisticsWhenNoStudentsExist() {
    StudentService studentService = new StudentService();

    assertTrue(studentService.findOldestStudent().isEmpty());
    assertTrue(studentService.calculateAverageAge().isEmpty());
  }

  @Test
  void shouldCalculateOldestStudentAndAverageAge() {
    StudentService studentService = new StudentService();
    studentService.addStudent(new Student(1001, "张三", 18));
    studentService.addStudent(new Student(1002, "李四", 20));
    studentService.addStudent(new Student(1003, "王五", 22));

    Student oldestStudent = studentService.findOldestStudent().orElseThrow();

    assertEquals(22, oldestStudent.getAge());
    assertEquals(20.0, studentService.calculateAverageAge().orElseThrow(), 0.001);
  }

}

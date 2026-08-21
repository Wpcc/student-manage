package com.example.studentmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 负责保存和查询学生数据。 */
public class StudentService {
  private final Map<Integer, Student> students = new HashMap<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

  public void addStudent(Student student) {
    if (findById(student.getId()) != null) {
      LOGGER.warn("发现重复学号：id={}", student.getId());
      throw new DuplicateStudentException(student.getId());
    }

    students.put(student.getId(), student);
    LOGGER.info("新增学生成功：id={}, name={}", student.getId(), student.getName());
  }

  public List<Student> findAll() {
    return new ArrayList<>(students.values());
  }

  public Student findById(int id) {
    return students.get(id);
  }

  public boolean deleteById(int id) {
    if (students.remove(id) != null) {
      LOGGER.info("删除学生成功：id={}", id);
      return true;
    } else {
      LOGGER.warn("删除失败，该学生不存在：id={}", id);
      return false;
    }
  }

  public List<Student> findByName(String keyword) {
    List<Student> result = new ArrayList<>();
    for (Student student : students.values()) {
      if (student.getName().contains(keyword)) {
        result.add(student);
      }
    }
    return result;
  }

  public boolean changeAge(int id, int age) {
    Student student = findById(id);
    if (student != null) {
      LOGGER.info("修改年龄成功：id={},age={}", id, age);
      student.setAge(age);
      return true;
    }
    LOGGER.warn("修改失败，学生不存在：id={}", id);
    return false;
  }

  public List<Student> findAdults() {
    return students.values()
        .stream()
        .filter(student -> student.getAge() >= 18)
        .toList();
  }

  public List<Student> findAllOrder(StudentSortStrategy strategy) {
    List<Student> students = findAll();
    return strategy.sort(students);
  }

}

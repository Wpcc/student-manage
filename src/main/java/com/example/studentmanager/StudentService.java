package com.example.studentmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 负责保存和查询学生数据。 */
public class StudentService {
  private final Map<Integer, Student> students = new HashMap<>();

  public void addStudent(Student student) {
    if (findById(student.getId()) != null) {
      throw new DuplicateStudentException(student.getId());
    }

    students.put(student.getId(), student);
  }

  public List<Student> findAll() {
    return new ArrayList<>(students.values());
  }

  public Student findById(int id) {
    return students.get(id);
  }

  public boolean deleteById(int id) {
    return students.remove(id) != null;
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
      student.setAge(age);
      return true;
    }
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

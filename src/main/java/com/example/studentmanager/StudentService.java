package com.example.studentmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 负责保存和查询学生数据。 */
public class StudentService {
  private final Map<Integer, Student> students = new HashMap<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

  public void addStudent(Student student) {
    if (findById(student.getId()).isPresent()) {
      LOGGER.warn("发现重复学号：id={}", student.getId());
      throw new DuplicateStudentException(student.getId());
    }

    students.put(student.getId(), student);
    LOGGER.info("新增学生成功：id={}, name={}", student.getId(), student.getName());
  }

  public List<Student> findAll() {
    return new ArrayList<>(students.values());
  }

  public Optional<Student> findById(int id) {
    return Optional.ofNullable(students.get(id));
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
    return findByCondition(student -> student.getName().contains(keyword));
  }

  public boolean changeAge(int id, int age) {
    return findById(id)
        .map(student -> {
          student.setAge(age);
          LOGGER.info("修改年龄成功：id={},age={}", id, age);
          return true;
        })
        .orElseGet(() -> {
          LOGGER.warn("修改失败，学生不存在：id={}", id);
          return false;
        });
  }

  public List<Student> findAdults() {
    return findByCondition(student -> student.getAge() >= 18);
  }

  public List<Student> findAllOrder(SortStrategy<Student> strategy) {
    List<Student> students = findAll();
    return strategy.sort(students);
  }

  public List<Student> findByCondition(Predicate<Student> condition) {
    return students.values()
        .stream()
        .filter(condition)
        .toList();
  }

  public List<String> findTopAdultSummaries(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException();
    }

    return students.values()
        .stream()
        .filter(student -> student.getAge() >= 18)
        .sorted(Comparator.comparingInt(Student::getAge).reversed())
        .limit(limit)
        .map(Student::getSummary)
        .toList();
  }

  public Map<Integer, List<Student>> groupStudentsByAge() {
    return students.values()
        .stream()
        .collect(Collectors.groupingBy(Student::getAge));
  }

  public Map<Boolean, List<Student>> partitionStudentsByAdult() {
    return students.values()
        .stream()
        .collect(Collectors.partitioningBy(student -> student.getAge() >= 18));
  }

  public Optional<Student> findOldestStudent() {
    return students.values()
        .stream()
        .max(Comparator.comparingInt(Student::getAge));
  }

  public OptionalDouble calculateAverageAge() {
    return students.values()
        .stream()
        .mapToInt(Student::getAge)
        .average();
  }

}

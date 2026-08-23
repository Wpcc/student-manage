package com.example.studentmanager;

import java.util.Comparator;
import java.util.List;

public class NameAscendingStrategy implements SortStrategy<Student> {
  @Override
  public List<Student> sort(List<Student> students) {
    return students
        .stream()
        .sorted(Comparator.comparing(Student::getName))
        .toList();
  }
}

package com.example.studentmanager;

import java.util.Comparator;
import java.util.List;

public class AgeAscendingStrategy implements StudentSortStrategy {
  @Override
  public List<Student> sort(List<Student> students) {
    return students
        .stream()
        .sorted(Comparator.comparingInt(Student::getAge))
        .toList();
  }
}

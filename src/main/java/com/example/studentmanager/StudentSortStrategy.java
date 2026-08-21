package com.example.studentmanager;

import java.util.List;

public interface StudentSortStrategy {
  List<Student> sort(List<Student> students);
}
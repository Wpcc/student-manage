package com.example.studentmanager;

public class DuplicateStudentException extends RuntimeException {
  public DuplicateStudentException(int id) {
    super("学号已存在：" + id);
  }
}

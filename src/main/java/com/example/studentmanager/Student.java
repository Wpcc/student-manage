package com.example.studentmanager;

/** 表示一个学生。 */
public class Student extends Person {
  private final int id;

  public Student(int id, String name, int age) {
    super(name, age);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public String getRole() {
    return "学生";
  }

  @Override
  public String toString() {
    return "Student{id=%d, name='%s', age=%d}".formatted(id, getName(), getAge());
  }
}

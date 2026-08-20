package com.example.studentmanager;

/** 表示一个学生。 */
public class Student {
  private final int id;
  private final String name;
  private int age;

  public Student(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Student{id=%d, name='%s', age=%d}".formatted(id, name, age);
  }
}

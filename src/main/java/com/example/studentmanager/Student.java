package com.example.studentmanager;

/** 表示一个学生。 */
public class Student extends Person implements Printable {
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

  @Override
  public String getSummary() {
    return "%s：%s, 学号：%d, 年龄：%d".formatted(getRole(), getName(), id, getAge());
  }
}

package com.example.studentmanager;

/** 表示一个学生。 */
public class Student extends Person implements Printable {
  private final int id;

  public Student(int id, String name, int age) {
    super(requireName(name), requireAge(age));
    this.id = requireId(id);
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

  @Override
  public void setAge(int age) {
    super.setAge(requireAge(age));
  }

  private static int requireId(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("id必须大于 0");
    }
    return id;
  }

  private static String requireName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("姓名不能为空");
    }
    return name;
  }

  private static int requireAge(int age) {
    if (age < 0 || age > 150) {
      throw new IllegalArgumentException("年龄必须在 0 到 150 之间");
    }
    return age;
  }
}

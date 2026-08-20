package com.example.studentmanager;

public class Teacher extends Person implements Printable {
  private final String employeeNo;

  public Teacher(String employeeNo, String name, int age) {
    super(name, age);
    this.employeeNo = employeeNo;
  }

  @Override
  public String getRole() {
    return "老师";
  }

  @Override
  public String getSummary() {
    return "%s：%s, 工号：%s, 年龄：%d".formatted(getRole(), getName(), employeeNo, getAge());
  }
}

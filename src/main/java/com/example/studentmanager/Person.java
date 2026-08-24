package com.example.studentmanager;

public abstract class Person {
  @DisplayField(label = "姓名", order = 2)
  private final String name;

  @DisplayField(label = "年龄", order = 3)
  private int age;

  protected Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return this.name;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public abstract String getRole();

}

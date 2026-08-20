package com.example.studentmanager;

public abstract class Person {
  private final String name;
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

package com.example.studentmanager;

public class Person {
  private final String name;
  private int age;

  Person(String name, int age) {
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

  public String getRole() {
    return "人员";
  }

}

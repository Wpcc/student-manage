package com.example.studentmanager;

public class InheritanceDemo {
  public static void main(String[] args) {
    Person person = new Student(1001, "张三", 18);
    System.out.println(person.getRole());
  }
}

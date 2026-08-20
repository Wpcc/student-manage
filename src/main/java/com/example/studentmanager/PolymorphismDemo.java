package com.example.studentmanager;

import java.util.ArrayList;
import java.util.List;

public class PolymorphismDemo {
  public static void main(String[] args) {
    List<Person> people = new ArrayList<>();

    Student student = new Student(1001, "张三", 18);
    Teacher teacher = new Teacher("T001", "王老师", 35);

    people.add(student);
    people.add(teacher);

    for (Person person : people) {
      System.out.println(person.getRole() + ":" + person.getName());
    }

    printSummary(student);
    printSummary(teacher);

  }

  private static void printSummary(Printable printable) {
    System.out.println(printable.getSummary());
  }
}

package com.example.studentmanager;

import java.util.List;

public final class PersonUtils {
  private PersonUtils() {

  }

  public static double calculateAverageAge(List<? extends Person> people) {
    return people.stream()
        .mapToInt(Person::getAge)
        .average()
        .orElse(0.0);
  }

  public static void copyStudents(
      List<? super Student> target,
      List<? extends Student> source) {
    for (Student student : source) {
      target.add(student);
    }
  }
}

package com.example.studentmanager;

public final class Course {
  public static void main(String[] args) {
    Course course = new Course("Java 核心", 40);

    System.out.println(
        ReflectionFormatter.formatAnnotatedFields(course, " | "));
  }

  @DisplayField(label = "课程", order = 1)
  private final String name;

  @DisplayField(label = "课时", order = 2)
  private final int hours;

  public Course(String name, int hours) {
    this.name = name;
    this.hours = hours;
  }
}

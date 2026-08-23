package com.example.studentmanager;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceDemo {
  public static void main(String[] args) {
    Student student = new Student(1001, "张三", 20);

    Predicate<Student> isAdult = value -> value.getAge() > 18;

    Function<Student, String> toSummary = Student::getSummary;

    Consumer<Student> printStudent = System.out::println;

    Supplier<Student> defaultStudent = () -> new Student(9999, "默认学生", 18);

    boolean adult = isAdult.test(student);
    System.out.println("是否成年：" + adult);

    String summary = toSummary.apply(student);
    System.out.println("学生摘要：" + summary);

    printStudent.accept(student);

    Student createdStudent = defaultStudent.get();
    System.out.println("默认学生：" + createdStudent);

  }

}

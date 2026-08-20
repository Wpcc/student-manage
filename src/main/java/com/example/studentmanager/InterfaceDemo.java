package com.example.studentmanager;

public class InterfaceDemo {
  public static void main(String[] args) {
    Printable printable = new Student(1001, "张三", 18);
    System.out.println(printable.getSummary());
  }
}

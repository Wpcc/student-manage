package com.example.studentmanager;

public class ClassLoadingDemo {
  public static void main(String[] args) {
    System.out.println("创建Child前");

    new Child();
  }

  static class Parent {
    static {
      System.out.println("初始化Parent");
    }
  }

  static class Child extends Parent {
    static {
      System.out.println("初始化Child");
    }
  }
}

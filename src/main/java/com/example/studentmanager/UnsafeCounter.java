package com.example.studentmanager;

public class UnsafeCounter {
  private int value;

  public void increment() {
    value++;
  }

  public int getValue() {
    return value;
  }
}

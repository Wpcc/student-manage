package com.example.studentmanager;

public class PrivateReflectionTarget {
  private final String prefix;

  private PrivateReflectionTarget(String prefix) {
    this.prefix = prefix;
  }

  @SuppressWarnings("unused")
  private String format(String value) {
    if (value == null) {
      throw new IllegalArgumentException("value 不能为空");
    }

    return prefix + "：" + value;
  }
}

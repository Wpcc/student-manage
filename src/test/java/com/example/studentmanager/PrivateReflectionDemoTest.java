package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PrivateReflectionDemoTest {

  @Test
  void shouldCreateObjectAndInvokePrivateMethod() {
    String formatted = PrivateReflectionDemo.createAndFormat("学生", "张三");

    assertEquals("学生：张三", formatted);
  }

  @Test
  void shouldKeepOriginalCauseWhenPrivateMethodThrowsException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> PrivateReflectionDemo.createAndFormat("学生", null));

    assertEquals("私有方法执行失败", exception.getMessage());
    IllegalArgumentException cause =
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
    assertEquals("value 不能为空", cause.getMessage());
  }
}

package com.example.studentmanager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrivateReflectionDemo {

  public static void main(String[] args) {
    System.out.println(
        PrivateReflectionDemo.createAndFormat("学生", "张三"));
  }

  public static String createAndFormat(String prefix, String value) {
    try {
      Constructor<PrivateReflectionTarget> constructor = PrivateReflectionTarget.class
          .getDeclaredConstructor(String.class);

      constructor.setAccessible(true);

      PrivateReflectionTarget target = constructor.newInstance(prefix);

      Method formatMethod = PrivateReflectionTarget.class.getDeclaredMethod("format", String.class);

      formatMethod.setAccessible(true);

      return (String) formatMethod.invoke(target, value);
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();

      throw new IllegalArgumentException(
          "私有方法执行失败",
          cause);

    } catch (NoSuchMethodException
        | InstantiationException
        | IllegalAccessException e) {

      throw new IllegalStateException(
          "反射创建或调用失败",
          e);
    }

  }
}

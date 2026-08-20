package com.example.studentmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExceptionDemo {
  public static void main(String[] args) {
    try {
      divide(10, 0);
    } catch (ArithmeticException e) {
      System.out.println("除数不能为0");
    } finally {
      System.out.println("除法演示结束");
    }

    try {
      readFirstLine("not-exist.txt");
    } catch (IOException e) {
      System.out.println("文件读取失败：" + e);
    } finally {
      System.out.println("文件读取演示结束");
    }
  }

  public static int divide(int a, int b) {
    return a / b;
  }

  private static String readFirstLine(String filePath) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(filePath));
    return lines.get(0);
  }

}

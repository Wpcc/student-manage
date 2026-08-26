package com.example.studentmanager;

import java.util.ArrayList;
import java.util.List;

public class JvmMemoryDemo {
  public static void main(String[] args) {
    printMemory("程序开始");

    createTemporaryObjects();

    printMemory("执行后");

    System.gc();
    printMemory("请求 GC 后");
  }

  private static void printMemory(String stage) {
    Runtime runtime = Runtime.getRuntime();

    long totalMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();
    long maxMemory = runtime.maxMemory();
    long usedMemory = totalMemory - freeMemory;

    long mb = 1024 * 1024;

    System.out.println("=== " + stage + " ===");
    System.out.println("已使用堆内存：" + usedMemory / mb + " MB");
    System.out.println("当前已分配堆内存：" + totalMemory / mb + " MB");
    System.out.println("最大可用堆内存：" + maxMemory / mb + " MB");
  }

  private static void createTemporaryObjects() {
    List<byte[]> data = new ArrayList<>();

    for (int i = 0; i < 1_000; i++) {
      data.add(new byte[1024]);
    }

    System.out.println("已创建 1000 个 1KB 的临时数组");
  }
}

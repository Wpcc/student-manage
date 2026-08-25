package com.example.studentmanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskExecutorDemo {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(3);

    for (int i = 1; i <= 5; i++) {
      int taskNumber = i;

      executor.submit(() -> {
        System.out.println(
            "任务 " + taskNumber + " 正在线程 "
                + Thread.currentThread().getName() + " 执行");

        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          System.out.println("任务" + taskNumber + "被中断");
          return;
        }

        System.out.println("任务" + taskNumber + "执行完成");
      });
    }

    executor.shutdown();

    try {
      boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);

      if (!finished) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

}

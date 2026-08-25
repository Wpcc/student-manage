package com.example.studentmanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    try {
      for (int i = 1; i <= 4; i++) {
        String requestId = "request-" + i;

        executor.submit(() -> {
          RequestContext.setRequestId(requestId);
          try {
            System.out
                .println("任务-" + RequestContext.getRequestId() + "正在线程" + Thread.currentThread().getName() + "中执行");
          } finally {
            RequestContext.clear();
          }

        });
      }
    } finally {
      shutdownExecutor(executor);
    }
  }

  private static void shutdownExecutor(ExecutorService executor) {
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

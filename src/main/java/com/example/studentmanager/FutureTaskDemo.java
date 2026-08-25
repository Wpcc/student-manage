package com.example.studentmanager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    try {
      Callable<String> successTask = () -> {
        Thread.sleep(200);

        return "任务完成，执行线程：" + Thread.currentThread().getName();
      };

      Future<String> successFuture = executor.submit(successTask);

      String result = successFuture.get();
      System.out.println(result);

      Callable<String> failedTask = () -> {
        throw new IllegalStateException("模拟任务失败");
      };

      Future<String> failedFuture = executor.submit(failedTask);

      try {
        failedFuture.get();
      } catch (ExecutionException e) {
        System.out.println("任务失败原因：" + e.getCause().getMessage());
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("等待任务结果时被中断", e);
    } catch (ExecutionException e) {
      throw new IllegalStateException("成功任务执行失败", e.getCause());
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

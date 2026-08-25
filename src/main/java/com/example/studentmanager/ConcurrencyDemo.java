package com.example.studentmanager;

import java.util.List;

public class ConcurrencyDemo {
  public static void main(String[] args) {
    Counter counter = new Counter();

    Runnable task = () -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    };

    List<Thread> threads = List.of(
        new Thread(task, "thread-1"),
        new Thread(task, "thread-2"),
        new Thread(task, "thread-3"),
        new Thread(task, "thread-4"),
        new Thread(task, "thread-5"));

    threads.forEach(Thread::start);

    try {
      for (Thread thread : threads) {
        thread.join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("等待线程完成时被中断", e);
    }

    System.out.println("实际值：" + counter.getValue());
    System.out.println("预期值：" + 5 * 1_000);

  }
}

package com.example.studentmanager;

import java.util.List;

public class ThreadSafetyDemo {
  public static void main(String[] args) {

    Counter counter = new Counter();

    Runnable run = () -> {
      for (int i = 0; i < 100_000; i++) {
        counter.increment();
      }
    };

    List<Thread> threads = List.of(
        new Thread(run, "thread1"),
        new Thread(run, "thread2"),
        new Thread(run, "thread3"),
        new Thread(run, "thread4"),
        new Thread(run, "thread5"));

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
    System.out.println("预期值：" + 5 * 100_000);

    UnsafeCounter unsafeCounter = new UnsafeCounter();

    Runnable run2 = () -> {
      for (int i = 0; i < 100_000; i++) {
        unsafeCounter.increment();
      }
    };

    List<Thread> threads2 = List.of(
        new Thread(run2, "thread1"),
        new Thread(run2, "thread2"),
        new Thread(run2, "thread3"),
        new Thread(run2, "thread4"),
        new Thread(run2, "thread5"));

    threads2.forEach(Thread::start);

    try {
      for (Thread thread2 : threads2) {
        thread2.join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("等待线程完成时被中断", e);
    }

    System.out.println("unsafeCounter实际值：" + unsafeCounter.getValue());
    System.out.println("unsafeCounter预期值：" + 5 * 100_000);
  }

}

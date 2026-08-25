package com.example.studentmanager;

import java.util.List;

public class VolatileAndAtomicDemo {
  public static void main(String[] args) {
    testAtomicCounter();
    testVolatileFlag();
  }

  private static void testAtomicCounter() {
    AtomicCounter atomic = new AtomicCounter();

    Runnable task = () -> {
      for (int i = 0; i < 100_000; i++) {
        atomic.increment();
      }
    };

    List<Thread> threads = List.of(
        new Thread(task, "atomic-thread-1"),
        new Thread(task, "atomic-thread-2"),
        new Thread(task, "atomic-thread-3"),
        new Thread(task, "atomic-thread-4"),
        new Thread(task, "atomic-thread-5"));

    threads.forEach(Thread::start);

    waitForThreads(threads);

    System.out.println("AtomicCounter 实际值：" + atomic.getValue());
    System.out.println("AtomicCounter 预期值：500000");
  }

  private static void testVolatileFlag() {
    VolatileFlag flag = new VolatileFlag();

    Thread worker = new Thread(() -> {
      while (flag.isRunning()) {
        Thread.onSpinWait();
      }

      System.out.println("工作线程已停止");
    }, "worker-thread");

    worker.start();

    try {
      Thread.sleep(100);
      flag.stop();
      worker.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("等待线程时被中断", e);
    }
  }

  private static void waitForThreads(List<Thread> threads) {
    try {
      for (Thread thread : threads) {
        thread.join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("等待线程时被中断", e);
    }
  }
}

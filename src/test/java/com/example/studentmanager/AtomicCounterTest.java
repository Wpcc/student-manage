package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;

class AtomicCounterTest {

  @Test
  void shouldCountCorrectlyWhenIncrementedByMultipleThreads() throws InterruptedException {
    AtomicCounter counter = new AtomicCounter();
    int threadCount = 5;
    int incrementsPerThread = 1_000;
    List<Thread> threads = new ArrayList<>();

    for (int i = 0; i < threadCount; i++) {
      Thread thread = new Thread(() -> {
        for (int j = 0; j < incrementsPerThread; j++) {
          counter.increment();
        }
      });
      threads.add(thread);
      thread.start();
    }

    for (Thread thread : threads) {
      thread.join();
    }

    assertEquals(threadCount * incrementsPerThread, counter.getValue());
  }

  @Test
  void shouldMakeStopSignalVisibleToWorkerThread() throws InterruptedException {
    VolatileFlag flag = new VolatileFlag();
    CountDownLatch workerStarted = new CountDownLatch(1);
    Thread worker = new Thread(() -> {
      workerStarted.countDown();
      while (flag.isRunning()) {
        Thread.onSpinWait();
      }
    });

    worker.start();
    workerStarted.await();
    flag.stop();
    worker.join(1_000);

    assertFalse(worker.isAlive());
    assertFalse(flag.isRunning());
  }
}

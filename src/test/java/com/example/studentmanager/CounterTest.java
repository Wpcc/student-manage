package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CounterTest {

  @Test
  void shouldIncrementValue() {
    Counter counter = new Counter();

    counter.increment();

    assertEquals(1, counter.getValue());
  }

  @Test
  void shouldCountCorrectlyWhenIncrementedByMultipleThreads() throws InterruptedException {
    Counter counter = new Counter();
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
}

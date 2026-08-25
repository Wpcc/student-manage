package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UnsafeCounterTest {

  @Test
  void shouldIncrementCorrectlyInSingleThread() {
    UnsafeCounter counter = new UnsafeCounter();

    counter.increment();
    counter.increment();

    assertEquals(2, counter.getValue());
  }
}

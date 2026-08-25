package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class FutureTaskDemoTest {

  @Test
  void shouldHandleSuccessfulAndFailedFutureTasks() {
    assertTimeoutPreemptively(Duration.ofSeconds(2),
        () -> FutureTaskDemo.main(new String[0]));
  }
}

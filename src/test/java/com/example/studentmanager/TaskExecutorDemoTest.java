package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class TaskExecutorDemoTest {

  @Test
  void shouldFinishSubmittedTasksAndShutdownExecutor() {
    assertTimeoutPreemptively(Duration.ofSeconds(2),
        () -> TaskExecutorDemo.main(new String[0]));
  }
}

package com.example.studentmanager.scheduler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class TaskActionFactoryTest {

  @Test
  void shouldCreateSuccessAndFailureActions() {
    Runnable successAction = TaskActionFactory.createSuccessAction("任务执行成功");
    Runnable failureAction = TaskActionFactory.createFailureAction("任务执行失败");

    assertDoesNotThrow(successAction::run);
    assertThrows(IllegalStateException.class, failureAction::run);
  }

  @Test
  void shouldRejectBlankMessages() {
    assertThrows(IllegalArgumentException.class,
        () -> TaskActionFactory.createSuccessAction(" "));
    assertThrows(IllegalArgumentException.class,
        () -> TaskActionFactory.createFailureAction(null));
  }

  @Test
  void shouldFinishTaskSchedulerDemo() {
    assertTimeoutPreemptively(Duration.ofSeconds(2),
        () -> TaskSchedulerDemo.main(new String[0]));
  }
}

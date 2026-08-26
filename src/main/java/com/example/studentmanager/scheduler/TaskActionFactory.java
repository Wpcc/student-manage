package com.example.studentmanager.scheduler;

public final class TaskActionFactory {
  private TaskActionFactory() {

  }

  public static Runnable createSuccessAction(String message) {
    if (message == null || message.isBlank()) {
      throw new IllegalArgumentException("message 不能为空");
    }

    return () -> System.out.println(message);
  }

  public static Runnable createFailureAction(String message) {
    if (message == null || message.isBlank()) {
      throw new IllegalArgumentException("message 不能为空");
    }

    return () -> {
      throw new IllegalStateException(message);
    };
  }
}

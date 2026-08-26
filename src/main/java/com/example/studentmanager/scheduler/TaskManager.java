package com.example.studentmanager.scheduler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManager {
  private final Map<String, ScheduledTask> tasks = new ConcurrentHashMap<>();
  private final ExecutorService executor = Executors.newFixedThreadPool(2);

  public ScheduledTask submit(String description) {
    String taskId = UUID.randomUUID().toString();
    ScheduledTask task = new ScheduledTask(taskId, description);
    tasks.put(taskId, task);
    return task;
  }

  public ScheduledTask submit(String description, Runnable action) {
    Objects.requireNonNull(action, "action 不能为空");

    ScheduledTask task = submit(description);

    executor.submit(() -> {
      executeTask(task, action);
    });

    return task;

  }

  private void executeTask(ScheduledTask task, Runnable action) {
    task.setStatus(TaskStatus.RUNNING);

    try {
      action.run();
      task.setStatus(TaskStatus.SUCCESS);
    } catch (RuntimeException e) {
      task.setStatus(TaskStatus.FAILED);

      System.err.println(
          "任务执行失败，id=" + task.getId() + "，原因：" + e.getMessage());
    }
  }

  public void shutdown() {
    executor.shutdown();

    try {
      boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);

      if (!finished) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  public Optional<ScheduledTask> findById(String taskId) {
    return Optional.ofNullable(tasks.get(taskId));
  }

  public List<ScheduledTask> findAll() {
    return List.copyOf(tasks.values());
  }

  public List<ScheduledTask> findByStatus(TaskStatus status) {
    Objects.requireNonNull(status, "status 不能为空");

    return tasks.values()
        .stream()
        .filter(task -> task.getStatus() == status)
        .toList();
  }

  public int getTaskCount() {
    return tasks.size();
  }

}

package com.example.studentmanager.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskManager {
  private final Map<String, ScheduledTask> tasks = new ConcurrentHashMap<>();
  private final ExecutorService executor = Executors.newFixedThreadPool(2);
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);
  private final ScheduledExecutorService cleanupExecutor = Executors.newSingleThreadScheduledExecutor();

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
    LOGGER.info("任务开始执行，id={}，description={}", task.getId(), task.getDescription());

    try {
      action.run();
      task.markCompleted(TaskStatus.SUCCESS);
      LOGGER.info("任务成功完成，id={}", task.getId());
    } catch (RuntimeException e) {
      task.markCompleted(TaskStatus.FAILED);
      LOGGER.error("任务失败，id={}", task.getId(), e);
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

  public void startCleanup(Duration maxAge, Duration interval) {
    Objects.requireNonNull(maxAge, "maxAge 不能为空");
    Objects.requireNonNull(interval, "interval 不能为空");
    if (maxAge.isNegative()) {
      throw new IllegalArgumentException("maxAge 不能为负数");
    }

    if (interval.isZero() || interval.isNegative()) {
      throw new IllegalArgumentException("interval 必须大于 0");
    }

    long intervalMillis = interval.toMillis();

    if (intervalMillis <= 0) {
      throw new IllegalArgumentException("interval 必须至少为 1 毫秒");
    }

    cleanupExecutor.scheduleAtFixedRate(
        () -> cleanupCompletedBefore(LocalDateTime.now().minus(maxAge)),
        0,
        intervalMillis,
        TimeUnit.MILLISECONDS);
  }

  public int cleanupCompletedBefore(LocalDateTime cutoff) {
    Objects.requireNonNull(cutoff, "cutoff 不能为空");

    int removedCount = 0;

    for (ScheduledTask task : tasks.values()) {
      LocalDateTime completedAt = task.getCompletedAt();

      if (completedAt != null && completedAt.isBefore(cutoff) && tasks.remove(task.getId(), task)) {
        removedCount++;
      }
    }

    LOGGER.info("清理完成任务数量：{}", removedCount);

    return removedCount;
  }

  public void shutdown() {
    shutdownExecutor(executor);
    shutdownExecutor(cleanupExecutor);
  }

  private void shutdownExecutor(ExecutorService executor) {
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
}

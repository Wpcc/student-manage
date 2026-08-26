package com.example.studentmanager.scheduler;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduledTask {

  private final String id;
  private final String description;
  private final LocalDateTime createdAt;
  private volatile LocalDateTime completedAt;
  private volatile TaskStatus status;

  public ScheduledTask(String id, String description) {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("任务ID不能为空");
    }

    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("任务描述不能为空");
    }

    this.id = id;
    this.description = description;
    this.createdAt = LocalDateTime.now();
    this.status = TaskStatus.PENDING;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getCompletedAt() {
    return completedAt;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    Objects.requireNonNull(status, "任务状态不能为空");
    this.status = status;
  }

  public void markCompleted(TaskStatus status) {
    if (status != TaskStatus.SUCCESS && status != TaskStatus.FAILED) {
      throw new IllegalArgumentException("完成状态只能是 SUCCESS 或 FAILED");
    }

    this.status = status;
    this.completedAt = LocalDateTime.now();
  }

}

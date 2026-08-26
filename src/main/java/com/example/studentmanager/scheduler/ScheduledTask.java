package com.example.studentmanager.scheduler;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduledTask {

  private final String id;
  private final String description;
  private final LocalDateTime createdAt;
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

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    Objects.requireNonNull(status, "任务状态不能为空");
    this.status = status;
  }

}

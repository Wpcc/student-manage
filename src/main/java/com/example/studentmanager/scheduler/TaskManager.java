package com.example.studentmanager.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskManager {
  private final Map<String, ScheduledTask> tasks = new HashMap<>();

  public ScheduledTask submit(String description) {
    String taskId = UUID.randomUUID().toString();
    ScheduledTask task = new ScheduledTask(taskId, description);
    tasks.put(taskId, task);
    return task;
  }

  public Optional<ScheduledTask> findById(String taskId) {
    return Optional.ofNullable(tasks.get(taskId));
  }

  public List<ScheduledTask> findAll() {
    return List.copyOf(tasks.values());
  }
}

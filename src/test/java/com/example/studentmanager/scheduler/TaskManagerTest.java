package com.example.studentmanager.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class TaskManagerTest {

  @Test
  void shouldSubmitAndFindTask() {
    TaskManager manager = new TaskManager();

    ScheduledTask submitted = manager.submit("生成学习报告");

    assertNotNull(submitted.getId());
    assertFalse(submitted.getId().isBlank());
    assertEquals("生成学习报告", submitted.getDescription());
    assertNotNull(submitted.getCreatedAt());
    assertEquals(TaskStatus.PENDING, submitted.getStatus());
    assertEquals(submitted, manager.findById(submitted.getId()).orElseThrow());
    assertTrue(manager.findById("missing").isEmpty());
  }

  @Test
  void shouldChangeTaskStatusAndReturnUnmodifiableTaskList() {
    TaskManager manager = new TaskManager();
    ScheduledTask first = manager.submit("任务一");
    ScheduledTask second = manager.submit("任务二");

    first.setStatus(TaskStatus.RUNNING);
    List<ScheduledTask> tasks = manager.findAll();

    assertEquals(2, tasks.size());
    assertTrue(tasks.containsAll(List.of(first, second)));
    assertEquals(TaskStatus.RUNNING, first.getStatus());
    assertThrows(UnsupportedOperationException.class, () -> tasks.add(first));
  }

  @Test
  void shouldRejectInvalidTaskData() {
    assertThrows(IllegalArgumentException.class, () -> new ScheduledTask(null, "描述"));
    assertThrows(IllegalArgumentException.class, () -> new ScheduledTask("id", "  "));

    ScheduledTask task = new ScheduledTask("id", "描述");
    assertThrows(NullPointerException.class, () -> task.setStatus(null));
  }
}

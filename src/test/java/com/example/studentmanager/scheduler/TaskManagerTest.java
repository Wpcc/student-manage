package com.example.studentmanager.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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

  @Test
  void shouldExecuteTaskAndUpdateStatus() throws InterruptedException {
    TaskManager manager = new TaskManager();
    CountDownLatch started = new CountDownLatch(1);
    CountDownLatch allowFinish = new CountDownLatch(1);

    ScheduledTask task = manager.submit("异步生成报告", () -> {
      started.countDown();
      try {
        allowFinish.await();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException("任务被中断", e);
      }
    });

    try {
      assertTrue(started.await(1, TimeUnit.SECONDS));
      assertEquals(TaskStatus.RUNNING, task.getStatus());

      allowFinish.countDown();
      manager.shutdown();

      assertEquals(TaskStatus.SUCCESS, task.getStatus());
    } finally {
      allowFinish.countDown();
      manager.shutdown();
    }
  }

  @Test
  void shouldMarkTaskAsFailedWhenActionThrowsException() {
    TaskManager manager = new TaskManager();
    ScheduledTask task = manager.submit("失败任务", () -> {
      throw new IllegalStateException("模拟失败");
    });

    try {
      manager.shutdown();

      assertEquals(TaskStatus.FAILED, task.getStatus());
      assertThrows(NullPointerException.class, () -> manager.submit("空任务", null));
    } finally {
      manager.shutdown();
    }
  }

  @Test
  void shouldSupportConcurrentTaskSubmission() throws Exception {
    TaskManager manager = new TaskManager();
    ExecutorService callers = Executors.newFixedThreadPool(8);
    CountDownLatch start = new CountDownLatch(1);
    List<Future<ScheduledTask>> futures = new ArrayList<>();

    try {
      for (int i = 0; i < 20; i++) {
        int taskNumber = i;
        futures.add(callers.submit(() -> {
          start.await();
          return manager.submit("并发任务-" + taskNumber);
        }));
      }

      start.countDown();
      for (Future<ScheduledTask> future : futures) {
        assertNotNull(future.get(1, TimeUnit.SECONDS));
      }

      assertEquals(20, manager.getTaskCount());
    } finally {
      callers.shutdownNow();
      manager.shutdown();
    }
  }

  @Test
  void shouldFindTasksByStatus() {
    TaskManager manager = new TaskManager();
    ScheduledTask pending = manager.submit("等待任务");
    ScheduledTask success = manager.submit("成功任务");
    success.setStatus(TaskStatus.SUCCESS);

    try {
      assertEquals(List.of(pending), manager.findByStatus(TaskStatus.PENDING));
      assertEquals(List.of(success), manager.findByStatus(TaskStatus.SUCCESS));
      assertThrows(NullPointerException.class, () -> manager.findByStatus(null));
    } finally {
      manager.shutdown();
    }
  }
}

package com.example.studentmanager.scheduler;

public class TaskSchedulerDemo {
  public static void main(String[] args) {
    TaskManager manager = new TaskManager();

    ScheduledTask successTask = manager.submit("成功任务",
        TaskActionFactory.createSuccessAction("成功任务的业务代码执行完成"));

    ScheduledTask failedTask = manager.submit(
        "失败任务",
        TaskActionFactory.createFailureAction("失败任务模拟异常"));

    manager.shutdown();

    System.out.println("成功任务最终状态：" + successTask.getStatus());
    System.out.println("失败任务最终状态：" + failedTask.getStatus());
  }
}

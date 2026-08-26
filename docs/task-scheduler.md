# Task Scheduler 项目复盘

## 1. 项目解决的问题

任务调度器减少了频繁创建线程的开销，同时统一管理任务状态、失败处理、过期任务清理和线程池生命周期。

如果不用线程池，每提交一个任务都要创建一个新线程。任务量增加时会带来频繁的线程创建和销毁、难以控制的资源消耗，以及缺少统一关闭和异常处理的问题。

## 2. 核心类与职责

- `ScheduledTask`：任务的数据对象，记录任务 id、描述、创建时间、状态和完成时间。
- `TaskManager`：管理任务集合、提交异步任务、更新任务状态、清理过期任务，以及关闭线程池。
- `TaskStatus`：任务状态枚举，包含 `PENDING`、`RUNNING`、`SUCCESS` 和 `FAILED`。
- `TaskActionFactory`：集中创建成功或失败的 `Runnable` 任务动作，避免调用方重复编写校验和 Lambda 逻辑。

## 3. 一次任务的完整生命周期

任务状态变化如下：

```text
PENDING -> RUNNING -> SUCCESS
                   -> FAILED
```

1. `TaskManager.submit()` 创建 `ScheduledTask`，初始状态为 `PENDING`。
2. 线程池开始执行任务时，`TaskManager` 将状态更新为 `RUNNING`。
3. `Runnable` 正常执行完成时，`TaskManager` 标记任务为 `SUCCESS`；执行过程中抛出异常时，标记为 `FAILED`。

## 4. 并发安全设计

- `ConcurrentHashMap`：让任务集合能够被多个线程安全地读写。
- `volatile`：保证任务状态更新后，其他线程能够看到最新值。
- `ExecutorService`：执行普通异步任务的线程池，复用工作线程。
- `ScheduledExecutorService`：定期执行“清理过期任务”的线程池。

## 5. 异常与资源管理

任务执行失败时，`TaskManager` 捕获 `RuntimeException`，记录 error 日志，并将任务标记为 `FAILED`。

最终必须调用 `shutdown()`：它会停止接收新任务，并等待已提交的任务结束，避免线程池中的非守护线程持续占用资源，导致程序无法正常退出。

## 6. 目前的局限与可优化点

- 当前任务数据只在内存中，应用重启后会丢失；可以接入数据库或 Redis。
- 线程池大小写死为 2；可以通过配置文件设置，并按业务负载调整。
- 目前只能立即提交任务；可以增加指定时间执行或延迟队列。
- 可以增加任务取消、重试次数、超时控制和执行结果查询。
- 可以补充成功率、耗时、队列长度等监控指标。

# 学生管理系统 V1

一个用于学习 Java 基础知识的控制台项目。程序通过菜单管理学生信息，并将数据保存到本地文件，重启后可自动恢复。

## 已实现功能

- 新增学生
- 查看全部学生
- 按学号查询学生
- 按姓名关键词模糊搜索
- 按学号删除学生
- 按学号修改年龄
- 学号唯一校验
- 数字与非空文本输入校验
- 自定义 `DuplicateStudentException`
- 退出时保存学生数据，启动时自动恢复

## 技术点

- 面向对象：`Student`、`StudentService`
- 集合：`List`、`HashMap`、`Deque`
- 异常处理：`try-catch-finally`、`IOException`、自定义运行时异常
- 文件读写：`Files.readAllLines`、`Files.write`
- 算法练习：双指针、二分查找、哈希表、栈、单链表反转

学生数据使用 `HashMap<Integer, Student>` 存储，学号作为唯一键；按学号查找的平均时间复杂度为 `O(1)`。

## 项目结构

```text
src/main/java/com/example/studentmanager/
├── Main.java                       # 控制台菜单、输入输出、文件读写
├── Student.java                    # 学生实体
├── StudentService.java             # 学生业务逻辑
├── DuplicateStudentException.java  # 重复学号异常
├── CollectionDemo.java             # 集合练习
├── ExceptionDemo.java              # 异常练习
└── AlgorithmPractice.java          # 算法练习
```

## 运行方式

需要 JDK 17 或更高版本。

```powershell
javac -encoding UTF-8 -d out src/main/java/com/example/studentmanager/*.java
java -cp out com.example.studentmanager.Main
```

## 数据持久化

程序在当前目录读写 `students.txt`：

```text
1001,张三,18
1002,李四,20
```

该文件是本地学习数据，已通过 `.gitignore` 排除，不会提交到 Git。

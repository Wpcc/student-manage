package com.example.studentmanager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static final Scanner SCANNER = new Scanner(System.in,
      Charset.forName(System.getProperty("native.encoding")));
  private static final StudentService STUDENT_SERVICE = new StudentService();
  private static final Path DATA_FILE = Path.of(AppConfig.getRequired("student.data.file"));

  public static void main(String[] args) {
    boolean running = true;

    try {
      loadStudents();
    } catch (IOException e) {
      System.out.println("读取学生数据失败:" + e);
    }

    while (running) {
      printMenu();
      String choice = SCANNER.nextLine().trim();

      switch (choice) {
        case "1" -> addStudent();
        case "2" -> listStudents();
        case "3" -> findStudentById();
        case "4" -> deleteStudentById();
        case "5" -> findStudentsByKeyword();
        case "6" -> changeStudentAge();
        case "7" -> listAdultStudents();
        case "8" -> listStudentsOrderByAge();
        case "9" -> listStudentsOrderByName();
        case "10" -> listStudentsDescendByAge();
        case "0" -> running = false;
        default -> System.out.println("无效选项，请重新输入。");
      }
    }

    try {
      saveStudents();
    } catch (IOException e) {
      System.out.println("保存学生数据失败:" + e);
    }

    System.out.println("程序已退出，再见！");
  }

  private static void printMenu() {
    System.out.println("\n=== 学生管理系统 ===");
    System.out.println("1. 新增学生");
    System.out.println("2. 查看全部学生");
    System.out.println("3. 按学号查询学生");
    System.out.println("4. 按学号删除学生");
    System.out.println("5. 按姓名搜索学生");
    System.out.println("6. 按学号修改年龄");
    System.out.println("7. 查看成年学生");
    System.out.println("8. 按年龄升序查看学生");
    System.out.println("9. 按名字升序查看学生");
    System.out.println("10. 按年龄降序查看学生");
    System.out.println("0. 退出");
    System.out.print("请选择：");
  }

  private static void addStudent() {
    int id = readPositiveInt("请输入学号：");
    String name = readNonBlankText("请输入姓名：");
    int age = readPositiveInt("请输入年龄：");

    try {
      STUDENT_SERVICE.addStudent(new Student(id, name, age));
      System.out.println("新增成功。");
    } catch (DuplicateStudentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * 循环读取一个正整数；不合法的输入不会让程序终止。
   */
  private static int readPositiveInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = SCANNER.nextLine().trim();

      try {
        int value = Integer.parseInt(input);
        if (value > 0) {
          return value;
        }
        System.out.println("请输入大于 0 的整数。");
      } catch (NumberFormatException exception) {
        System.out.println("输入格式错误，请输入整数。");
      }
    }
  }

  /**
   * 循环读取非空文本。
   */
  private static String readNonBlankText(String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = SCANNER.nextLine().trim();
      if (!input.isEmpty()) {
        return input;
      }
      System.out.println("内容不能为空，请重新输入。");
    }
  }

  private static void listStudents() {
    List<Student> students = STUDENT_SERVICE.findAll();

    printStudents(students);
  }

  private static void findStudentById() {
    int id = readPositiveInt("请输入学号：");
    Student student = STUDENT_SERVICE.findById(id);

    if (student != null) {
      System.out.println("找到学生：" + student);
    } else {
      System.out.println("未找到该学生。");
    }
  }

  private static void deleteStudentById() {
    int id = readPositiveInt("请输入学号：");
    boolean hasDel = STUDENT_SERVICE.deleteById(id);

    if (hasDel) {
      System.out.println("删除成功");
    } else {
      System.out.println("未找到该学生。");
    }
  }

  private static void findStudentsByKeyword() {
    String name = readNonBlankText("请输入姓名关键词：");
    List<Student> students = STUDENT_SERVICE.findByName(name);

    printStudents(students);
  }

  private static void changeStudentAge() {
    int id = readPositiveInt("请输入学号：");
    int age = readPositiveInt("请输入年龄：");
    boolean hasChangeAge = STUDENT_SERVICE.changeAge(id, age);

    if (hasChangeAge) {
      System.out.println("更新年龄，修改成功");
    } else {
      System.out.println("未找到该学生");
    }
  }

  private static void printStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("暂无学生数据。");
    } else {
      System.out.println("学号\t姓名\t年龄");
      students.forEach(System.out::println);
    }
  }

  private static void loadStudents() throws IOException {
    if (!Files.exists(DATA_FILE)) {
      return;
    }

    List<String> lines = Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8);

    for (String line : lines) {
      if (line.isBlank()) {
        continue;
      }

      String[] parts = line.split(",");

      int id = Integer.parseInt(parts[0]);
      String name = parts[1];
      int age = Integer.parseInt(parts[2]);

      Student student = new Student(id, name, age);
      STUDENT_SERVICE.addStudent(student);
    }

  }

  private static void saveStudents() throws IOException {
    List<Student> students = STUDENT_SERVICE.findAll();

    List<String> lines = students.stream().map(student -> {
      String line = student.getId() + ","
          + student.getName()
          + ","
          + student.getAge();
      return line;
    }).toList();

    Files.write(DATA_FILE, lines, StandardCharsets.UTF_8);
  }

  private static void listAdultStudents() {
    List<Student> students = STUDENT_SERVICE.findAdults();
    printStudents(students);
  }

  private static void listStudentsOrderByAge() {
    StudentSortStrategy strategy = new AgeAscendingStrategy();
    List<Student> students = STUDENT_SERVICE.findAllOrder(strategy);
    printStudents(students);
  }

  private static void listStudentsDescendByAge() {
    StudentSortStrategy strategy = new AgeDescendingStrategy();
    List<Student> students = STUDENT_SERVICE.findAllOrder(strategy);
    printStudents(students);
  }

  private static void listStudentsOrderByName() {
    StudentSortStrategy strategy = new NameAscendingStrategy();
    List<Student> students = STUDENT_SERVICE.findAllOrder(strategy);
    printStudents(students);
  }
}

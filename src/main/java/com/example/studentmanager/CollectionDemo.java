package com.example.studentmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionDemo {
  public static void main(String[] args) {
    // 1 ArrayList 练习
    practiseArrayList();

    // 2 LinkedList 练习
    practiseLinkedList();

    // 3 HashSet 练习
    practiseHashSet();
  }

  public static void practiseArrayList() {

    List<String> names = new ArrayList<String>();

    names.add("张三");
    names.add("李四");
    names.add("王五");

    System.out.println(names.get(1));

    Iterator<String> iterator = names.iterator();

    while (iterator.hasNext()) {
      if (iterator.next().equals("李四")) {
        iterator.remove();
        break;
      }
    }

    names.forEach(System.out::println);
  }

  public static void practiseLinkedList() {
    List<String> projects = new LinkedList<String>();

    projects.addFirst("紧急任务");
    projects.addLast("普通任务");
    projects.addFirst("最紧急任务");

    String removeItem = projects.removeFirst();
    System.out.println(removeItem);

    projects.forEach(System.out::println);
  }

  public static void practiseHashSet() {
    Set<String> skills = new HashSet<String>();

    skills.add("Java");
    skills.add("MySQL");
    skills.add("Java");
    skills.add("Redis");

    System.out.println(skills);

    System.out.println(skills.size());

    boolean hasJava = skills.contains("Java");

    boolean hasPython = skills.contains("Python");

    if (hasJava) {
      System.out.println("包含JAVA");
    } else {
      System.out.println("不包含JAVA");
    }

    if (hasPython) {
      System.out.println("包含PYTHON");
    } else {
      System.out.println("不包含PYTHON");
    }
  }

}

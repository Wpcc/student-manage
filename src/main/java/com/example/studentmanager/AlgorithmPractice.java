package com.example.studentmanager;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmPractice {
  public static void main(String[] args) {
    String reverseStr = reverse("hello");
    System.out.println(reverseStr);

    int[] testData1 = { 1, 3, 5, 7, 9 };
    int subScript1 = binarySearch(testData1, 7);

    System.out.println(subScript1);

    int subScript2 = binarySearch(testData1, 4);

    System.out.println(subScript2);

    int[] sumNum = { 2, 7, 11, 15 };

    int[] subScriptTwo = twoSum(sumNum, 9);
    System.out.println(Arrays.toString(subScriptTwo));

    System.out.println("()[]{} -> " + isValidParentheses("()[]{}"));
    System.out.println("([)] -> " + isValidParentheses("([)]"));
    System.out.println("(( -> " + isValidParentheses("(("));
    System.out.println(") -> " + isValidParentheses(")"));

    ListNode first = new ListNode(1);
    ListNode second = new ListNode(2);
    ListNode third = new ListNode(3);

    first.next = second;
    second.next = third;

    printList(first);

    ListNode reversedHead = reverseList(first);

    printList(reversedHead);

  }

  private static String reverse(String text) {
    char[] chars = text.toCharArray();

    int left = 0;
    int right = chars.length - 1;

    while (left < right) {
      char temp = chars[left];
      chars[left] = chars[right];
      chars[right] = temp;

      left++;
      right--;
    }

    return new String(chars);
  }

  private static int binarySearch(int[] numbers, int target) {
    int left = 0;
    int right = numbers.length - 1;

    while (left <= right) {
      int middle = left + (right - left) / 2;

      if (numbers[middle] == target) {
        return middle;
      } else if (numbers[middle] < target) {
        left = middle + 1;
      } else if (numbers[middle] > target) {
        right = middle - 1;
      }

    }

    return -1;
  }

  private static int[] twoSum(int[] numbers, int target) {
    Map<Integer, Integer> numberToIndex = new HashMap<>();

    for (int i = 0; i < numbers.length; i++) {
      int current = numbers[i];

      int needed = target - current;

      if (numberToIndex.containsKey(needed)) {
        int previousIndex = numberToIndex.get(needed);
        return new int[] { previousIndex, i };
      }

      numberToIndex.put(current, i);
    }

    return new int[] { -1, -1 };
  }

  private static boolean isValidParentheses(String text) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char current : text.toCharArray()) {
      if (current == '(' || current == '[' || current == '{') {
        stack.push(current);
      } else {
        if (stack.isEmpty() || !isMatch(stack.pop(), current)) {
          return false;
        }
      }
    }

    if (stack.isEmpty()) {
      return true;
    }
    return false;
  }

  private static boolean isMatch(char left, char right) {
    if (left == '(' && right == ')') {
      return true;
    } else if (left == '[' && right == ']') {
      return true;
    } else if (left == '{' && right == '}') {
      return true;
    }
    return false;
  }

  private static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
    }
  }

  private static ListNode reverseList(ListNode head) {
    ListNode previous = null;
    ListNode current = head;

    while (current != null) {
      ListNode next = current.next;

      current.next = previous;

      previous = current; // previous 前进
      current = next;
    }

    return previous;

  }

  private static void printList(ListNode head) {
    ListNode current = head;

    while (current != null) {
      System.out.print(current.value);

      if (current.next != null) {
        System.out.print(" -> ");
      }

      current = current.next;
    }

    System.out.println();
  }
}

package com.example.studentmanager;

import java.util.List;

public interface SortStrategy<T> {
  List<T> sort(List<T> items);
}
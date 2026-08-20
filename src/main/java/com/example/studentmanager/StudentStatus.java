package com.example.studentmanager;

public enum StudentStatus {
  ACTIVE("在读"),
  GRADUATED("已毕业"),
  SUSPENDED("休学");

  private final String description;

  StudentStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

package com.example.studentmanager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaApiDemo {
  public static void main(String[] args) {
    StudentStatus status = StudentStatus.ACTIVE;
    System.out.println(status.getDescription());

    BigDecimal price = new BigDecimal("199.90");
    BigDecimal discount = new BigDecimal("0.85");

    BigDecimal finalPrice = price.multiply(discount).setScale(2, RoundingMode.HALF_UP);
    System.out.println(finalPrice);

    BigDecimal first = new BigDecimal("0.1");
    BigDecimal second = new BigDecimal("0.2");
    BigDecimal result = first.add(second);
    System.out.println(result);

    LocalDate today = LocalDate.now();
    LocalDateTime now = LocalDateTime.now();
    LocalDate graduationDate = today.plusYears(4);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String formattedTime = now.format(formatter);

    System.out.println(today);
    System.out.println(graduationDate);
    System.out.println(formattedTime);
  }
}

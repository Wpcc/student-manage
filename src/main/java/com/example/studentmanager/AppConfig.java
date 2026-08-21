package com.example.studentmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {
  private static final Properties PROPERTIES = new Properties();

  private AppConfig() {

  }

  static {
    // 加载配置项到PROPERTYIES中
    try (InputStream input = AppConfig.class
        .getClassLoader()
        .getResourceAsStream("app.properties")) {
      if (input == null) {
        throw new IllegalStateException("找不到 app.properties 配置文件");
      }

      PROPERTIES.load(input);

    } catch (IOException e) {
      throw new IllegalStateException("加载 app.properties 失败", e);
    }
  }

  // 从PROPERTIES获取配置项
  public static String getRequired(String key) {
    String value = PROPERTIES.getProperty(key);

    if (value == null || value.isBlank()) {
      throw new IllegalStateException("缺少必要配置项：" + key);
    }

    return value;
  }

}

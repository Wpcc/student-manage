package com.example.studentmanager;

public class RegistrationRequest {
  @NotBlank(message = "用户名不能为空")
  private final String username;

  @NotBlank(message = "邮箱不能为空")
  private final String email;

  private final String nickname;

  public RegistrationRequest(String username, String email, String nickname) {
    this.username = username;
    this.email = email;
    this.nickname = nickname;
  }
}

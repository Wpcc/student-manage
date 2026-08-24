package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class AnnotationValidatorTest {

  @Test
  void shouldReturnNoErrorsForValidRequest() {
    RegistrationRequest request = new RegistrationRequest("zhangsan", "zhangsan@example.com", null);

    List<String> errors = AnnotationValidator.validate(request);

    assertTrue(errors.isEmpty());
  }

  @Test
  void shouldReturnAnnotationMessagesForNullOrBlankFields() {
    RegistrationRequest request = new RegistrationRequest("   ", null, "小张");

    List<String> errors = AnnotationValidator.validate(request);

    assertEquals(List.of("用户名不能为空", "邮箱不能为空"), errors);
  }

  @Test
  void shouldRejectNullTarget() {
    assertThrows(NullPointerException.class, () -> AnnotationValidator.validate(null));
  }
}

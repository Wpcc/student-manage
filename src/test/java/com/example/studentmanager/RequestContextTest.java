package com.example.studentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class RequestContextTest {

  @Test
  void shouldClearContextBeforeWorkerThreadIsReused() throws Exception {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    try {
      String requestId = executor.submit(() -> {
        RequestContext.setRequestId("request-1");
        try {
          return RequestContext.getRequestId();
        } finally {
          RequestContext.clear();
        }
      }).get();

      assertEquals("request-1", requestId);
      assertNull(executor.submit(RequestContext::getRequestId).get());
    } finally {
      executor.shutdownNow();
    }
  }

  @Test
  void shouldFinishThreadLocalDemo() {
    assertTimeoutPreemptively(Duration.ofSeconds(2),
        () -> ThreadLocalDemo.main(new String[0]));
  }
}

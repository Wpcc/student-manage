package com.example.studentmanager;

public class VolatileFlag {
  private volatile boolean running = true;

  public boolean isRunning() {
    return running;
  }

  public void stop() {
    running = false;
  }
}

package com.plusend.diycode.view.widget;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class AppOperator {
  private static ExecutorService EXECUTORS_INSTANCE;

  public static Executor getExecutor() {
    if (EXECUTORS_INSTANCE == null) {
      synchronized (AppOperator.class) {
        if (EXECUTORS_INSTANCE == null) {
          EXECUTORS_INSTANCE = Executors.newFixedThreadPool(6);
        }
      }
    }
    return EXECUTORS_INSTANCE;
  }

  public static void runOnThread(Runnable runnable) {
    getExecutor().execute(runnable);
  }
}

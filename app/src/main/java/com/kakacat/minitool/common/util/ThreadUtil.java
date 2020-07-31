package com.kakacat.minitool.common.util;

import java.util.concurrent.Callable;

import bolts.Task;

public class ThreadUtil {

  public static void callInUiThread(Runnable runnable){
      Task.call(runToCall(runnable),Task.UI_THREAD_EXECUTOR);
  }

  private static Callable<Void> runToCall(Runnable runnable){
      return () -> {
          runnable.run();
          return null;
      };
  }
}

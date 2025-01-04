package com.nduyhai.volumetracker.service;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractThreadPoolManager {

  protected final ExecutorService executorService;

  protected AbstractThreadPoolManager(ExecutorService executorService) {
    this.executorService = executorService;
  }

  @PreDestroy
  public void shutdownThreadPool() {
    log.info("Shutting down thread pool...");
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        log.info("Forcing shutdown of thread pool...");
        executorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      log.info("Thread pool shutdown interrupted...");
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}

package com.nduyhai.volumetracker.scheduler;

import com.nduyhai.volumetracker.service.DailyDataCleaningService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@RequiredArgsConstructor
@Component
@Slf4j
public class DailyDataCleaningScheduler {
  private final DailyDataCleaningService dailyDataService;

  @Scheduled(cron = "0 30 9 * * ?") // Runs at 9:30 AM daily
  public void fillMissingDailyRecords() {
    log.info("Start fill missing daily records...");
    LocalDateTime today = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);

    this.dailyDataService.fillMissingRecord(today);

    log.info("End fill missing daily records...");
  }
}

package com.nduyhai.volumetracker.util;

import java.time.LocalDateTime;

public final class TimeUtils {

  private TimeUtils() {}

  public static LocalDateTime toEndOfDay(LocalDateTime dateTime) {
    return dateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
  }

  public static LocalDateTime toStartOfDay(LocalDateTime dateTime) {
    return dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
  }
}

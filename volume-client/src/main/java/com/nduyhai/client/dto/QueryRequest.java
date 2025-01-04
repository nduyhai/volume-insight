package com.nduyhai.client.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryRequest {

  private String userId;

  private List<String> keywords;

  private SubscriptionType timing;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public enum SubscriptionType {
    HOURLY,
    DAILY
  }
}

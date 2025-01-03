package com.nduyhai.volumetracker.dto;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryRequest {

  @NotEmpty
  private List<String> keywords;

  @NotNull
  private SubscriptionType timing;

  @NotNull
  private LocalDateTime startTime;

  @NotNull
  private LocalDateTime endTime;
}

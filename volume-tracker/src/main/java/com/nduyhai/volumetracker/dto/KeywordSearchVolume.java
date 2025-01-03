package com.nduyhai.volumetracker.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordSearchVolume {

  private Long id;

  private Long keywordId;

  private LocalDateTime createdDatetime;

  private Long searchVolume;
}

package com.nduyhai.client.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResponse {

  private String keyword;

  private List<KeywordSearchVolume> data;
}

@Getter
@Setter
class KeywordSearchVolume {

  private String name;

  private LocalDateTime createdDatetime;

  private Long searchVolume;
}

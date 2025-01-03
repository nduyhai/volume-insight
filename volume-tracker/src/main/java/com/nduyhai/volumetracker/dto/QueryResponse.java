package com.nduyhai.volumetracker.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {

  private String keyword;

  private List<KeywordSearchVolume> data;
}

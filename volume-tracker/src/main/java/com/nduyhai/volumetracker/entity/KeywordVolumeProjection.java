package com.nduyhai.volumetracker.entity;

import java.time.LocalDateTime;

public interface KeywordVolumeProjection {

  String getName();

  LocalDateTime getCreatedDatetime();

  Long getSearchVolume();

}

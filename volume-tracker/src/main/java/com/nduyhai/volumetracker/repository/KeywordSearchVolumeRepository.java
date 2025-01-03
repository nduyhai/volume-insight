package com.nduyhai.volumetracker.repository;

import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordSearchVolumeRepository extends
    JpaRepository<KeywordSearchVolumeEntity, Long> {

  @Query("SELECT k FROM KeywordSearchVolumeEntity k WHERE k.keywordId = :keywordId AND k.createdDatetime BETWEEN :startTime AND :endTime AND HOUR(k.createdDatetime) = 9")
  List<KeywordSearchVolumeEntity> findDailyData(@Param("keywordId") Long keywordId,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

  @Query("SELECT k FROM KeywordSearchVolumeEntity k WHERE k.keywordId = :keywordId AND k.createdDatetime BETWEEN :startTime AND :endTime")
  List<KeywordSearchVolumeEntity> findHourlyData(@Param("keywordId") Long keywordId,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}

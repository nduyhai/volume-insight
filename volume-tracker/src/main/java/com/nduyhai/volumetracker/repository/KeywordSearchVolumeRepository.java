package com.nduyhai.volumetracker.repository;

import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import com.nduyhai.volumetracker.entity.KeywordVolumeProjection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordSearchVolumeRepository
    extends JpaRepository<KeywordSearchVolumeEntity, Long> {

  @Query(
      """
          SELECT DISTINCT k.name AS name, ksv.createdDatetime AS createdDatetime, ksv.searchVolume AS searchVolume
            FROM KeywordSearchVolumeEntity ksv
            JOIN KeywordEntity k ON k.id = ksv.keywordId
            JOIN UserSubscriptionEntity us ON us.keyword.id = k.id
            WHERE us.userId = :userId
              AND k.name IN :keywordNames
              AND EXISTS (
                        SELECT 1
                        FROM UserSubscriptionEntity us_inner
                        WHERE us_inner.userId = :userId
                          AND us_inner.keyword.id = k.id
                          AND ksv.createdDatetime BETWEEN us_inner.startDatetime AND us_inner.endDatetime
                    )
               AND ksv.createdDatetime BETWEEN :startTime AND :endTime
              AND (us.subscriptionType = 'DAILY'  OR us.subscriptionType = 'HOURLY')
              AND HOUR(ksv.createdDatetime) = 9
          ORDER BY ksv.createdDatetime ASC
          """)
  List<KeywordVolumeProjection> findDailyDataWithKeyword(
      @Param("userId") String userId,
      @Param("keywordNames") List<String> keywordNames,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);

  @Query(
      """
          SELECT DISTINCT k.name AS name, ksv.createdDatetime AS createdDatetime, ksv.searchVolume AS searchVolume
            FROM KeywordSearchVolumeEntity ksv
            JOIN KeywordEntity k ON k.id = ksv.keywordId
            JOIN UserSubscriptionEntity us ON us.keyword.id = k.id
            WHERE us.userId = :userId
              AND k.name IN :keywordNames
              AND EXISTS (
                        SELECT 1
                        FROM UserSubscriptionEntity us_inner
                        WHERE us_inner.userId = :userId
                          AND us_inner.keyword.id = k.id
                          AND ksv.createdDatetime BETWEEN us_inner.startDatetime AND us_inner.endDatetime
                    )
               AND ksv.createdDatetime BETWEEN :startTime AND :endTime
              AND us.subscriptionType = 'HOURLY'
           ORDER BY ksv.createdDatetime ASC
          """)
  List<KeywordVolumeProjection> findHourlyDataWithKeyword(
      @Param("userId") String userId,
      @Param("keywordNames") List<String> keywordNames,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);

  boolean existsByKeywordIdAndCreatedDatetime(Long keywordId, LocalDateTime createdDatetime);

  @Query(
      """
        SELECT ksv
        FROM KeywordSearchVolumeEntity ksv
        WHERE ksv.keywordId = :keywordId
          AND ksv.createdDatetime BETWEEN :startOfDay AND :endOfDay
        ORDER BY ksv.createdDatetime DESC
        LIMIT 1
    """)
  Optional<KeywordSearchVolumeEntity> findNearestTimeTo9AM(
      @Param("keywordId") Long keywordId,
      @Param("startOfDay") LocalDateTime startOfDay,
      @Param("endOfDay") LocalDateTime endOfDay);
}

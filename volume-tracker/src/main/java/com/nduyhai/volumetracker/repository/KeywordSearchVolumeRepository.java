package com.nduyhai.volumetracker.repository;

import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import com.nduyhai.volumetracker.entity.KeywordVolumeProjection;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordSearchVolumeRepository
    extends JpaRepository<KeywordSearchVolumeEntity, Long> {

  @Query(
      """
        SELECT k.name AS name, ksv.createdDatetime AS createdDatetime, ksv.searchVolume AS searchVolume
          FROM KeywordSearchVolumeEntity ksv
          JOIN KeywordEntity k ON k.id = ksv.keywordId
          JOIN UserSubscriptionEntity us ON us.keyword.id = k.id
          WHERE us.userId = :userId
            AND k.name IN :keywordNames
            AND ksv.createdDatetime BETWEEN (
                SELECT MIN(us.startDatetime)
                FROM UserSubscriptionEntity us
                WHERE us.userId = :userId AND us.keyword.id = k.id
            )
            AND (
                SELECT MAX(us.endDatetime)
                FROM UserSubscriptionEntity us
                WHERE us.userId = :userId AND us.keyword.id = k.id
            )
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
          SELECT k.name AS name, ksv.createdDatetime AS createdDatetime, ksv.searchVolume AS searchVolume
            FROM KeywordSearchVolumeEntity ksv
            JOIN KeywordEntity k ON k.id = ksv.keywordId
            JOIN UserSubscriptionEntity us ON us.keyword.id = k.id
            WHERE us.userId = :userId
              AND k.name IN :keywordNames
              AND ksv.createdDatetime BETWEEN (
                  SELECT MIN(us.startDatetime)
                  FROM UserSubscriptionEntity us
                  WHERE us.userId = :userId AND us.keyword.id = k.id
              )
              AND (
                  SELECT MAX(us.endDatetime)
                  FROM UserSubscriptionEntity us
                  WHERE us.userId = :userId AND us.keyword.id = k.id
              )
              AND us.subscriptionType = 'HOURLY'
           ORDER BY ksv.createdDatetime ASC
          """)
  List<KeywordVolumeProjection> findHourlyDataWithKeyword(
      @Param("userId") String userId,
      @Param("keywordNames") List<String> keywordNames,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);
}

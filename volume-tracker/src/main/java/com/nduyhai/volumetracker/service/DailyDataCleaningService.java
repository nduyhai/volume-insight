package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.entity.KeywordEntity;
import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import com.nduyhai.volumetracker.repository.KeywordRepository;
import com.nduyhai.volumetracker.repository.KeywordSearchVolumeRepository;
import com.nduyhai.volumetracker.util.TimeUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

@Service
public class DailyDataCleaningService extends AbstractThreadPoolManager {
  private final KeywordRepository keywordRepository;
  private final KeywordSearchVolumeRepository searchVolumeRepository;

  public DailyDataCleaningService(
      KeywordRepository keywordRepository, KeywordSearchVolumeRepository searchVolumeRepository) {
    super(Executors.newFixedThreadPool(10));
    this.keywordRepository = keywordRepository;
    this.searchVolumeRepository = searchVolumeRepository;
  }

  public void fillMissingRecord(LocalDateTime today) {
    List<Long> keywordIds = keywordRepository.findAll().stream().map(KeywordEntity::getId).toList();

    for (Long keywordId : keywordIds) {

      executorService.submit(
          () -> {
            if (!searchVolumeRepository.existsByKeywordIdAndCreatedDatetime(keywordId, today)) {
              Optional<KeywordSearchVolumeEntity> maybeEntity =
                  searchVolumeRepository.findNearestTimeTo9AM(
                      keywordId, TimeUtils.toStartOfDay(today), TimeUtils.toEndOfDay(today));

              if (maybeEntity.isPresent()) {
                KeywordSearchVolumeEntity nearestTime = maybeEntity.get();

                KeywordSearchVolumeEntity newRecord = new KeywordSearchVolumeEntity();
                newRecord.setKeywordId(keywordId);
                newRecord.setCreatedDatetime(today);
                newRecord.setSearchVolume(nearestTime.getSearchVolume());
                searchVolumeRepository.save(newRecord);
              }
            }
          });
    }
  }
}

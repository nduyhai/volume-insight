package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.entity.KeywordEntity;
import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import com.nduyhai.volumetracker.repository.KeywordRepository;
import com.nduyhai.volumetracker.repository.KeywordSearchVolumeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MasterDataService extends AbstractThreadPoolManager {

  private final KeywordRepository keywordRepository;
  private final KeywordSearchVolumeRepository keywordSearchVolumeRepository;

  public MasterDataService(
      KeywordRepository keywordRepository,
      KeywordSearchVolumeRepository keywordSearchVolumeRepository) {
    super(Executors.newFixedThreadPool(10));
    this.keywordRepository = keywordRepository;
    this.keywordSearchVolumeRepository = keywordSearchVolumeRepository;
  }

  public void generateData() {
    List<KeywordEntity> keywords = this.keywordRepository.findAll();

    LocalDateTime startDate =
        LocalDateTime.now().minusMonths(3).withMinute(0).withSecond(0).withNano(0);
    LocalDateTime endDate = LocalDateTime.now();

    for (KeywordEntity keyword : keywords) {

      executorService.submit(
          () -> {
            LocalDateTime currentDate = startDate;
            log.info("Start generate data for keyword: {}", keyword.getName());
            while (currentDate.isBefore(endDate)) {
              KeywordSearchVolumeEntity volume = new KeywordSearchVolumeEntity();
              volume.setKeywordId(keyword.getId());
              volume.setCreatedDatetime(currentDate);
              volume.setSearchVolume(ThreadLocalRandom.current().nextLong(1000));
              keywordSearchVolumeRepository.save(volume);

              currentDate = currentDate.plusHours(1); // hourly
            }
            log.info("Data generated for keyword: {}", keyword.getName());
          });
    }
  }
}

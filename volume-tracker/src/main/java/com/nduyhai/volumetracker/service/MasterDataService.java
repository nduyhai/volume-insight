package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.entity.KeywordEntity;
import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import com.nduyhai.volumetracker.repository.KeywordRepository;
import com.nduyhai.volumetracker.repository.KeywordSearchVolumeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterDataService {

  private final ExecutorService executorService = Executors.newFixedThreadPool(10);
  private final KeywordRepository keywordRepository;
  private final KeywordSearchVolumeRepository keywordSearchVolumeRepository;

  public void generateData() {
    List<KeywordEntity> keywords = IntStream.range(0, 10).mapToObj(i -> {
      KeywordEntity keyword = new KeywordEntity();
      keyword.setName("Keyword_" + i);
      return keywordRepository.save(keyword);
    }).toList();

    LocalDateTime startDate = LocalDateTime.now().minusMonths(3);
    LocalDateTime endDate = LocalDateTime.now();

    for (KeywordEntity keyword : keywords) {

      executorService.submit(() -> {
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

package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import com.nduyhai.volumetracker.dto.QueryRequest;
import com.nduyhai.volumetracker.dto.QueryResponse;
import com.nduyhai.volumetracker.entity.KeywordVolumeProjection;
import com.nduyhai.volumetracker.mapper.VolumeMapper;
import com.nduyhai.volumetracker.repository.KeywordSearchVolumeRepository;
import com.nduyhai.volumetracker.util.TimeUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyQueryService implements KeywordService {

  private final KeywordSearchVolumeRepository keywordSearchVolumeRepository;
  private final VolumeMapper volumeMapper;

  @Override
  public List<QueryResponse> queryData(QueryRequest request) {

    List<KeywordVolumeProjection> volumes =
        keywordSearchVolumeRepository.findDailyDataWithKeyword(
            request.getUserId(),
            request.getKeywords(),
            TimeUtils.toStartOfDay(request.getStartTime()),
            TimeUtils.toEndOfDay(request.getEndTime()));

    return volumes.stream()
        .collect(
            Collectors.groupingBy(
                KeywordVolumeProjection::getName,
                Collectors.mapping(this.volumeMapper::toKeywordSearchVolume, Collectors.toList())))
        .entrySet()
        .stream()
        .map(entry -> new QueryResponse(entry.getKey(), entry.getValue()))
        .toList();
  }

  @Override
  public SubscriptionType supportedType() {
    return SubscriptionType.DAILY;
  }
}

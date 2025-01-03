package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import com.nduyhai.volumetracker.dto.QueryRequest;
import com.nduyhai.volumetracker.dto.QueryResponse;
import com.nduyhai.volumetracker.entity.KeywordEntity;
import com.nduyhai.volumetracker.entity.KeywordSearchVolumeEntity;
import com.nduyhai.volumetracker.mapper.VolumeMapper;
import com.nduyhai.volumetracker.repository.KeywordRepository;
import com.nduyhai.volumetracker.repository.KeywordSearchVolumeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HourlyQueryService implements KeywordService {

  private final KeywordRepository keywordRepository;
  private final KeywordSearchVolumeRepository keywordSearchVolumeRepository;
  private final VolumeMapper volumeMapper;


  @Override
  public List<QueryResponse> queryData(QueryRequest request) {
    List<KeywordEntity> keywords = keywordRepository.findByNameIn(request.getKeywords());

    if (keywords.isEmpty()) {
      throw new IllegalArgumentException("No matching keywords found.");
    }

    List<QueryResponse> responses = new ArrayList<>();
    for (KeywordEntity keyword : keywords) {
      List<KeywordSearchVolumeEntity> volumes = keywordSearchVolumeRepository.findHourlyData(
          keyword.getId(), request.getStartTime(), request.getEndTime());
      responses.add(
          new QueryResponse(keyword.getName(), this.volumeMapper.toKeywordSearchVolumes(volumes)));
    }

    return responses;
  }

  @Override
  public SubscriptionType supportedType() {
    return SubscriptionType.HOURLY;
  }
}

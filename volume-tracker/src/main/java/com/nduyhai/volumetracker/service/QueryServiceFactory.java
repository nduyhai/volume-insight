package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryServiceFactory {

  private final List<KeywordService> keywordServices;

  public KeywordService getService(SubscriptionType type) {
    return keywordServices.stream().filter(service -> service.supportedType().equals(type))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Not support type: " + type));
  }

}

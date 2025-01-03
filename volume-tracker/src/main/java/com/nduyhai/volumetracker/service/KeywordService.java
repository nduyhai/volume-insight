package com.nduyhai.volumetracker.service;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import com.nduyhai.volumetracker.dto.QueryRequest;
import com.nduyhai.volumetracker.dto.QueryResponse;
import java.util.List;

public interface KeywordService {

  List<QueryResponse> queryData(QueryRequest request);

  SubscriptionType supportedType();

}

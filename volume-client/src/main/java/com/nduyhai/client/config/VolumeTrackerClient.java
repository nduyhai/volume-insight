package com.nduyhai.client.config;

import com.nduyhai.client.dto.QueryRequest;
import com.nduyhai.client.dto.QueryResponse;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface VolumeTrackerClient {
  @PostExchange("/api/v1/volumes/generate-data")
  void generateData();

  @PostExchange("/api/v1/volumes/query")
  List<QueryResponse> queryData(@RequestBody QueryRequest request);
}

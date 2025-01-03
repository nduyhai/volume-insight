package com.nduyhai.volumetracker.controller;

import com.nduyhai.volumetracker.dto.QueryRequest;
import com.nduyhai.volumetracker.dto.QueryResponse;
import com.nduyhai.volumetracker.service.KeywordService;
import com.nduyhai.volumetracker.service.MasterDataService;
import com.nduyhai.volumetracker.service.QueryServiceFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/volumes")
public class VolumeController {

  private final QueryServiceFactory queryServiceFactory;
  private final MasterDataService masterDataService;

  @PostMapping("/query")
  public ResponseEntity<List<QueryResponse>> queryData(
      @Validated @RequestBody QueryRequest request) {
    KeywordService queryService = queryServiceFactory.getService(request.getTiming());
    List<QueryResponse> responses = queryService.queryData(request);
    return ResponseEntity.ok(responses);
  }

  @PostMapping("/generate-data")
  public ResponseEntity<String> generateData() {
    this.masterDataService.generateData();
    return ResponseEntity.accepted().body("Data being generated");
  }

}

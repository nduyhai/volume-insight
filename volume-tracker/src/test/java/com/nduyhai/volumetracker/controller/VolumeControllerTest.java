package com.nduyhai.volumetracker.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import com.nduyhai.volumetracker.dto.QueryRequest;
import com.nduyhai.volumetracker.dto.QueryResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolumeControllerTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void testGenerateData() {
    // Call the /generate-data endpoint
    ResponseEntity<String> response =
        restTemplate.postForEntity("/api/v1/volumes/generate-data", null, String.class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
  }

  @Test
  void testQueryData() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
    request.setKeywords(List.of("Keyword_1", "Keyword_2"));
    request.setStartTime(LocalDateTime.now().minusMonths(3));
    request.setEndTime(LocalDateTime.now());
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotEmpty();
  }
}

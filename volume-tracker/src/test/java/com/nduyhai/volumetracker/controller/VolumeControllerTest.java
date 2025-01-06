package com.nduyhai.volumetracker.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

  // Daily
  @Test
  void queryDataDaily_1Keyword_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
    request.setKeywords(List.of("Keyword_1"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 3, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody()[0].getData().size()).isEqualTo(3);
  }

  @Test
  void queryDataDaily_2Keyword_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("5f2bd0e6-4e6c-4021-90c8-16df7bfc9a2f");
    request.setKeywords(List.of("Keyword_3", "Keyword_4"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 3, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(2);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(3);
    assertThat(response.getBody()[1].getData().size()).isEqualTo(3);
  }

  @Test
  void queryDataDaily_OverlapTime_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("d5f76e40-7479-4deb-b76e-4074799deb39");
    request.setKeywords(List.of("Keyword_5"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(3);
  }

  @Test
  void queryDataDaily_IntersectOverlapTime_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("d5f76e40-7479-4deb-b76e-4074799deb39");
    request.setKeywords(List.of("Keyword_6"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(4);
  }

  @Test
  void queryDataDaily_OutOfSubscription_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
    request.setKeywords(List.of("Keyword_1"));
    request.setStartTime(LocalDateTime.of(2024, 12, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2024, 12, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(0);
  }

  @Test
  void queryDataDaily_OutOfTimeRange_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
    request.setKeywords(List.of("Keyword_1"));
    request.setStartTime(LocalDateTime.now().minusMonths(1));
    request.setEndTime(LocalDateTime.now().minusMonths(2));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(0);
  }

  // Hourly
  @Test
  void queryDataHourly_With1Keyword_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
    request.setKeywords(List.of("Keyword_2"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
    request.setTiming(SubscriptionType.HOURLY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody()[0].getData().size()).isEqualTo(25);
  }

  @Test
  void queryDataHourly_With1KeywordDaily_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
    request.setKeywords(List.of("Keyword_2"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody()[0].getData().size()).isEqualTo(2);
  }

  @Test
  void queryDataHourly_WithOverlapTime_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("0b35d894-37d9-4936-b769-cdb14f527bc1");
    request.setKeywords(List.of("Keyword_5"));
    request.setStartTime(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.HOURLY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(26);
  }

  @Test
  void queryDataHourly_WithDailyOverlapTime_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("0b35d894-37d9-4936-b769-cdb14f527bc1");
    request.setKeywords(List.of("Keyword_5"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(3);
  }

  @Test
  void queryDataHourly_WithIntersectOverlapTime_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("0b35d894-37d9-4936-b769-cdb14f527bc1");
    request.setKeywords(List.of("Keyword_6"));
    request.setStartTime(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.HOURLY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(49);
  }

  @Test
  void queryDataHourly_WithDailyIntersectOverlapTime_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("0b35d894-37d9-4936-b769-cdb14f527bc1");
    request.setKeywords(List.of("Keyword_6"));
    request.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2025, 1, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.DAILY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);

    assertThat(response.getBody()[0].getData().size()).isEqualTo(4);
  }

  @Test
  void queryDataHourly_OutOfSubscription_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("0b35d894-37d9-4936-b769-cdb14f527bc1");
    request.setKeywords(List.of("Keyword_5"));
    request.setStartTime(LocalDateTime.of(2024, 1, 2, 0, 0, 0));
    request.setEndTime(LocalDateTime.of(2024, 2, 4, 0, 0, 0));
    request.setTiming(SubscriptionType.HOURLY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(0);
  }

  @Test
  void queryDataHourly_OutOfTimeRange_success() {
    // Prepare test data
    QueryRequest request = new QueryRequest();
    request.setUserId("0b35d894-37d9-4936-b769-cdb14f527bc1");
    request.setKeywords(List.of("Keyword_5"));
    request.setStartTime(LocalDateTime.now().minusMonths(1));
    request.setEndTime(LocalDateTime.now().minusMonths(2));
    request.setTiming(SubscriptionType.HOURLY);

    ResponseEntity<QueryResponse[]> response =
        restTemplate.postForEntity("/api/v1/volumes/query", request, QueryResponse[].class);

    // Validate the response
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(0);
  }
}

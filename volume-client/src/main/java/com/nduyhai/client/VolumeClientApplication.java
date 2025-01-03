package com.nduyhai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nduyhai.client.config.VolumeTrackerClient;
import com.nduyhai.client.dto.QueryRequest;
import com.nduyhai.client.dto.QueryRequest.SubscriptionType;
import com.nduyhai.client.dto.QueryResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class VolumeClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(VolumeClientApplication.class, args);
  }

  //  @Bean
  public CommandLineRunner generateData(VolumeTrackerClient client, ObjectMapper mapper) {
    return args -> {
      client.generateData();
      log.info("Generate Data success");
    };
  }

  @Bean
  public CommandLineRunner runner(VolumeTrackerClient client, ObjectMapper mapper) {

    return args -> {
      QueryRequest request = new QueryRequest();
      request.setUserId("e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b");
      request.setKeywords(List.of("keyword_1", "keyword_2"));
      request.setTiming(SubscriptionType.HOURLY);
      request.setStartTime(LocalDateTime.of(2024, 11, 1, 0, 0));
      request.setEndTime(LocalDateTime.of(2025, 1, 3, 0, 0));

      List<QueryResponse> queryResponses = client.queryData(request);
      log.info("Query Response: {}", mapper.writeValueAsString(queryResponses));
    };
  }
}

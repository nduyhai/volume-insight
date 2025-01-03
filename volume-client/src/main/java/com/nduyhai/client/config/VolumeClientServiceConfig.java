package com.nduyhai.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class VolumeClientServiceConfig {
  @ConfigurationProperties(prefix = "client.volumetracker")
  @Bean
  public RestClientProperties productProperties() {
    return new RestClientProperties();
  }

  @Bean
  public VolumeTrackerClient productClient(RestClientProperties productProperties) {

    RestClient restClient = RestClient.builder().baseUrl(productProperties.getBaseUrl()).build();
    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(VolumeTrackerClient.class);
  }
}

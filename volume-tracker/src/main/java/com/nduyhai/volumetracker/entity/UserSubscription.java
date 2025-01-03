package com.nduyhai.volumetracker.entity;

import com.nduyhai.volumetracker.contant.SubscriptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_subscription")
public class UserSubscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long subscriptionId;

  @Column(nullable = false)
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "keyword_id", nullable = false)
  private KeywordEntity keyword;

  @Enumerated(EnumType.STRING)
  private SubscriptionType subscriptionType;

  private LocalDateTime startDatetime;

  private LocalDateTime endDatetime;

}

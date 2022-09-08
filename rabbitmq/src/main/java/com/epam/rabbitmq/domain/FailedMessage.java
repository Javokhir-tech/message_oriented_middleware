package com.epam.rabbitmq.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FailedMessage {
  private UUID id;
  private Object payload;
  private LocalDateTime failedTime;
  private String exchange;
  private String routing;

}

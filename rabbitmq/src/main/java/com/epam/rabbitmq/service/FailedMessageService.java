package com.epam.rabbitmq.service;

import com.epam.rabbitmq.domain.FailedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailedMessageService {


  private final RabbitTemplate rabbitTemplate;
  private final Map<UUID, FailedMessage> cache = new ConcurrentHashMap<>();

  public <T> void create(FailedMessage failedMessage) {
    final var id = UUID.randomUUID();
    failedMessage.setFailedTime(now());
    failedMessage.setId(id);
    cache.put(UUID.randomUUID(), failedMessage);
  }

  public List<FailedMessage> get() {
    return List.copyOf(cache.values());
  }

  public void delete(UUID key) {
    cache.remove(key);
  }

  public void republish(UUID id) {
    ofNullable(cache.get(id))
      .ifPresent(msg -> rabbitTemplate.convertAndSend(msg.getExchange(), msg.getRouting(), msg.getPayload()));
    cache.remove(id);
  }
}

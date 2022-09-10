package com.epam.rabbitmq.receivers;

import com.epam.rabbitmq.domain.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.epam.rabbitmq.config.RabbitArgs.*;
import static com.epam.rabbitmq.config.RabbitProperties.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryConsumer {

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    messageConverter = "converter",
    bindings = @QueueBinding(
      value = @Queue(name = RETRY_QUEUE, durable = "true"),
      arguments = {
        @Argument(name = DLX, value = DLX_VALUE),
        @Argument(name = DLR_KEY, value = DLR_VALUE),
        @Argument(name = TTL, value = TTL_VALUE),
        @Argument(name = MAX_LENGTH, value = MAX_LENGTH_VALUE),
        @Argument(name = OVERFLOW, value = OVERFLOW_VALUE)
      },
      key = RETRY_ROUTING,
      exchange = @Exchange(name = RETRY_EXCHANGE, type = ExchangeTypes.TOPIC)))
  public void failedMessageListener(Message<Receipt> message) {
    log.info(" Retry Consumer received message -> {}", message.getPayload());
    rabbitTemplate.convertAndSend(EXCHANGE, ROUTING, message.getPayload());
  }
}

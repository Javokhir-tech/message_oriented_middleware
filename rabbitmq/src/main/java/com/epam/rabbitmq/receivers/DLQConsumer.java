package com.epam.rabbitmq.receivers;

import com.epam.rabbitmq.domain.FailedMessage;
import com.epam.rabbitmq.domain.Receipt;
import com.epam.rabbitmq.service.FailedMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.epam.rabbitmq.config.RabbitArgs.DLR_VALUE;
import static com.epam.rabbitmq.config.RabbitArgs.DLX_VALUE;
import static com.epam.rabbitmq.config.RabbitProperties.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DLQConsumer {


  private final FailedMessageService failedMessageService;

  @RabbitListener(
    messageConverter = "converter",
    bindings = @QueueBinding(
      value = @Queue(name = DEAD_LETTER_QUEUE, durable = "true"),
      key = DLR_VALUE,
      exchange = @Exchange(name = DLX_VALUE, type = ExchangeTypes.TOPIC)))
  public void listen(Message<Receipt> message) {

    log.info(" Dead Letter Consumer received message -> {}", message.getPayload());

    failedMessageService.create(FailedMessage.builder()
      .payload(message.getPayload())
      .exchange(EXCHANGE)
      .routing(ROUTING)
      .build());
  }

}

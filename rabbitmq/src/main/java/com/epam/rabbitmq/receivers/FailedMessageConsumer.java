package com.epam.rabbitmq.receivers;

import com.epam.rabbitmq.domain.FailedMessage;
import com.epam.rabbitmq.domain.Receipt;
import com.epam.rabbitmq.service.FailedMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.epam.rabbitmq.config.RabbitProperties.*;

@Component
@RequiredArgsConstructor
public class FailedMessageConsumer {

  private final FailedMessageService failedMessageService;

  @RabbitListener(
    messageConverter = "converter",
    bindings = @QueueBinding(
      value = @Queue(name = FAILED_MESSAGE_QUEUE, durable = "true"),
      key = FAILED_MESSAGE_ROUTING,
      exchange = @Exchange(name = FAILED_MESSAGE_EXCHANGE, type = ExchangeTypes.TOPIC)))
  public void failedMessageListener(Message<Receipt> message) {
    failedMessageService.create(FailedMessage.builder().payload(message.getPayload()).build());
  }
}

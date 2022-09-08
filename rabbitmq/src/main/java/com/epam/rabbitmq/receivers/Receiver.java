package com.epam.rabbitmq.receivers;

import com.epam.rabbitmq.domain.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

import java.util.Random;
import static com.epam.rabbitmq.config.RabbitProperties.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class Receiver {

    private final Random random = new Random();
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = {QUEUE}, containerFactory = "retryContainerFactory", messageConverter = "converter")
    public void receiveMessage1(Message<Receipt> message) throws Exception {
        int retryCount = RetrySynchronizationManager.getContext().getRetryCount();
        log.info("Message received: {}", message.getPayload());

        final boolean shouldFail = random.nextBoolean();

        log.info(" Should failed: " + shouldFail);

        log.debug("Retry time:" + retryCount);

        if (retryCount == MAX_ATTEMPT - 1) {
            sendToFailedMessageExchange(message.getPayload());
            throw new AmqpRejectAndDontRequeueException(" Eventually message cannot be processed! ");
        } else if(shouldFail) {
            sendToRetryExchange(message.getPayload());
        }

        log.info("Message successfully processed after on {} attempt", retryCount);
    }

    private void sendToFailedMessageExchange(Object payload) {
        rabbitTemplate.convertAndSend(FAILED_MESSAGE_EXCHANGE, FAILED_MESSAGE_ROUTING, payload);
    }

    private void sendToRetryExchange(Object payload) {
        rabbitTemplate.convertAndSend(RETRY_EXCHANGE, RETRY_ROUTING, payload);
    }
}

package com.epam.rabbitmq.receivers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@RequiredArgsConstructor
public class FailedReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = {"failed-queue"})
    public void receiveMessage(Message message) throws Exception {
        log.info("Message read from workerQueue : " + message);
    }
}

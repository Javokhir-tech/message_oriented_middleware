package com.epam.rabbitmq.sender;

import com.epam.rabbitmq.config.RabbitMqConfig;
import com.epam.rabbitmq.domain.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.epam.rabbitmq.config.RabbitProperties.EXCHANGE;
import static com.epam.rabbitmq.config.RabbitProperties.ROUTING;


@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");

        Product apple = new Product();
        apple.setName("apple");
        apple.setPrice("5 so'm");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING, apple);
    }
}


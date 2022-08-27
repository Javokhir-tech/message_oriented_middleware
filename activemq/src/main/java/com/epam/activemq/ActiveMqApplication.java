package com.epam.activemq;

import com.epam.activemq.producer.MessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@SpringBootApplication
public class ActiveMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveMqApplication.class, args);
		MessageProducer publisher = new MessageProducer();
		publisher.publishMessage("This sample message is consumed by subscriber");
	}
}

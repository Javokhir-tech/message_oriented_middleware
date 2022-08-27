package com.epam.activemq.producer;

import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Component
public class MessageProducer {

    public static void main(String[] args) {
        MessageProducer publisher = new MessageProducer();
        publisher.publishMessage("This sample message is consumed by subscriber");
    }

    public void publishMessage(final String msg) {
        InitialContext initialContext = null;
        TopicConnectionFactory connectionFactory;
        TopicConnection connection = null;
        TopicPublisher publisher;
        TopicSession session;
        Topic topic;

        try {
            // Step 1. Create an initial context to perform the JNDI lookup.
            initialContext = new InitialContext();

            // Step 2. Look-up the JMS topic
            topic = (Topic) initialContext.lookup("topic/topicName");

            // Step 3. Look-up the JMS Topic connection factory
            connectionFactory = (TopicConnectionFactory) initialContext.lookup("ConnectionFactory");

            // Step 4. Create a JMS Topic connection
            connection = connectionFactory.createTopicConnection();

            // Step 5. Set the client-id on the connection
            connection.start();

            // step 6. Create Topic session
            session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);

            // step 7. Create publisher
            publisher = session.createPublisher(topic);
            // publisher.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Step 8. Create a text message
            TextMessage message = session.createTextMessage(msg);

            // Step 9. Publish the text message to the topic
            publisher.publish(message);
        } catch (JMSException | NamingException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

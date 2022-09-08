package com.epam.rabbitmq.config;

public class RabbitProperties {
  public final static String QUEUE = "consumer-queue";
  public final static String EXCHANGE = "consumer.exchange";
  public final static String ROUTING = "consumer.routing";
  public final static String RETRY_EXCHANGE = "consumer.retry.exchange";
  public final static String RETRY_ROUTING = "consumer.retry.routing";
  public final static String RETRY_QUEUE = "consumer.retry.queue";
  public final static String FAILED_MESSAGE_QUEUE = "consumer.failed.message.queue";
  public final static String FAILED_MESSAGE_EXCHANGE = "consumer.failed.message.exchange";
  public final static String FAILED_MESSAGE_ROUTING = "consumer.failed.message.routing";
  public final static String DEAD_LETTER_QUEUE = "consumer.dead.letter.queue";
  public final static int MAX_ATTEMPT = 4;
}

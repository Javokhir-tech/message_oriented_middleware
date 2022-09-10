package com.epam.rabbitmq.config;

public class RabbitArgs {
    public final static String MAX_LENGTH = "x-max-length";
    public final static String MAX_LENGTH_VALUE = "10";

    public final static String TTL = "x-message-ttl";
    public final static String TTL_VALUE = "10000";

    public final static String DLX = "x-dead-letter-exchange";
    public final static String DLX_VALUE = "task.dead.letter.exchange";

    public final static String DLR_KEY = "x-dead-letter-routing-key";
    public final static String DLR_VALUE = "task.dead.letter.routing";

    public final static String OVERFLOW = "x-overflow";
    public final static String OVERFLOW_VALUE = "reject-publish-dlx";
}

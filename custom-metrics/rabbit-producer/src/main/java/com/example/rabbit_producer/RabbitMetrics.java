package com.example.rabbit_producer;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


@Component
public class RabbitMetrics {

    private final Counter messagesSent;
    private final Counter messagesRetried;
    private final Counter messagesDeadLettered;

    public RabbitMetrics(MeterRegistry registry) {
        messagesSent = Counter.builder("rabbitmq_messages_sent")
                .description("Messages sent to RabbitMQ by dm jadhav")
                .register(registry);

        messagesRetried = Counter.builder("rabbitmq_messages_retried")
                .description("Messages retried in RabbitMQ")
                .register(registry);

        messagesDeadLettered = Counter.builder("rabbitmq_messages_dlq")
                .description("Messages sent to DLQ")
                .register(registry);
    }

    public void incSent() {
        messagesSent.increment();
    }

    public void incRetry() {
        messagesRetried.increment();
    }

    public void incDlq() {
        messagesDeadLettered.increment();
    }
}

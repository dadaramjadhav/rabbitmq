package com.example.rabbit_consumer;

import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

 
@Component
public class RabbitConsumer {

    @Autowired
    AmqpTemplate template;
 
    @RabbitListener(queues = "myqueue")
    @RabbitHandler
    public void receive1(Customer msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("✅ Received: " + msg);
            // Simulate processing logic
            if (msg.getCustName().contains("dm3")) {
                throw new RuntimeException("Simulated failure");
            }

            // Acknowledge message
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("❌ Processing failed: " + e.getMessage());
            // Reject and drop the message (no retry, no requeue)
            channel.basicReject(tag, false);
        }
    }
}

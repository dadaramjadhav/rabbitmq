package com.example.rabbit_producer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "myqueue")
    public void receive(Customer customer) {
        System.out.println("Received: " + customer);
    }
}

package com.example.rabbit_producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    private RabbitMetrics metrics;

    @Autowired
    private AmqpTemplate template;

    private static int count = 0;

    // retry example
    @GetMapping("/producer")
    public String producer(@RequestParam("message") String messageData) {
        metrics.incSent();
        count++;
        Customer cust = new Customer(count, messageData, "pune" + count);
        template.convertAndSend("direct-exchange", "dm", cust);
        return "successfully sent message to  exchange having message: v1" + cust;
    }
}
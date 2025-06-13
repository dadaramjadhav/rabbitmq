package com.example.rabbit_consumer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//ssssssss
@Component
public class RabbitConsumer {

    @Autowired
    AmqpTemplate template;
    @RabbitListener(queues = "myqueue")
    public void receive(Customer customer) {
            if(customer.getCustName().equals("dm3")){
                throw new RuntimeException("customer name is dm3 could not add in queue.");
            }
		 
			if ((customer.getCustId() % 2) ==0) {
                System.out.println( "even message recieved : " + customer);
				template.convertAndSend("even.exchange", "even", customer);
			}else{
                System.out.println( "odd message recieved : " + customer);
                template.convertAndSend("odd.exchange", "odd", customer);
            }
    }
}
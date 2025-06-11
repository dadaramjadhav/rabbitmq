package com.example.rabbit_producer;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    
    @Autowired
    private AmqpTemplate template;

    //fanout exchange
    // @GetMapping("/producer")
    // public String producer(@RequestParam("exchange") String exchange, @RequestParam("message") String message){
    //     template.convertAndSend(exchange, "",  message + " "+ new Date());
    //     return "successfully sent message to  exchange: "+ exchange  + "  having message: "+ message;
    // }

    //direct exchange
    // @GetMapping("/producer")
    // public String producer(@RequestParam("exchange") String exchange, @RequestParam("key") String key, @RequestParam("message") String message){
    //     template.convertAndSend(exchange, key, message + " "+ new Date());
    //     return "successfully sent message to  exchange: "+ exchange + "  using key: "+ key + "  having message: "+ message;
    // }

        //topic exchange
    // @GetMapping("/producer")
    // public String producer(@RequestParam("exchange") String exchange, @RequestParam("key") String key, @RequestParam("message") String message){
    //     template.convertAndSend(exchange, key, message + " "+ new Date());
    //     return "successfully sent message to  exchange: "+ exchange + "  using key: "+ key + "  having message: "+ message;
    // }

    // //header exchange
    // @GetMapping("/producer")
    // public String producer(@RequestParam("exchange") String exchange,  @RequestParam("message") String messageData, @RequestParam("department") String department){

    //     MessageProperties mp = new MessageProperties();
    //     mp.setHeader("department", department);
    //     MessageConverter messageConverter = new SimpleMessageConverter();
    //     Message message = messageConverter.toMessage(messageData, mp);

    //     template.convertAndSend(exchange, "", message );
    //     return "successfully sent message to  exchange: "+ exchange +"  having message: "+ message;
    // }


        //retry example
        @GetMapping("/producer")
        public String producer(@RequestParam("message") String messageData){
            template.convertAndSend("direct-exchange", "dm", messageData );
            return "successfully sent message to  exchange having message: "+ messageData;
        }
}
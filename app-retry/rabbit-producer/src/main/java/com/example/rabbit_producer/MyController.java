package com.example.rabbit_producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
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

    @Autowired
    private AmqpTemplate template;

        private static int count = 0;
        //retry example
        @GetMapping("/producer")
        public String producer(@RequestParam("message") String messageData){
            count++;
            Customer cust = new Customer(count, messageData, "pune"+count);
            template.convertAndSend("direct-exchange", "dm", cust );
            return "successfully sent message to  exchange having message: "+ cust;
        }
}
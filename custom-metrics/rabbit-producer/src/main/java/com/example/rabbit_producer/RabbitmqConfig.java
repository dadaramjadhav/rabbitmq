package com.example.rabbit_producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitmqConfig {

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange("deadLetterExchange");
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct-exchange");
    }


    @Bean
    public Queue dlq(){
        return QueueBuilder.durable("deadLetter.queue").build();
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.durable("myqueue").withArgument("x-dead-letter-exchange", "deadLetterExchange")
        .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }
    
    @Bean
    public Binding dlqBinding(){
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
    }
    @Bean
    public Binding queuBinding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with("dm");
    }
      @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }















    

    // @Bean
    // public Binding financeBinding(){
    //     return BindingBuilder.bind(financeQueue()).to(exchange()).where("department").matches("finance");
    // }
    // @Bean
    // public Binding admBinding(){
    //     return BindingBuilder.bind(adminQueue()).to(exchange()).where("department").matches("admin");
    // }
    //fanout exchange
    // @Bean
    // public FanoutExchange exchange(){
    //     return new FanoutExchange("fanout-exchange");
    // }
    // @Bean
	// Binding marketingBinding(Queue marketingQueue, FanoutExchange exchange) {
	// 	return BindingBuilder.bind(marketingQueue).to(exchange);
	// }

	// @Bean
	// Binding financeBinding(Queue financeQueue, FanoutExchange exchange) {
	// 	return BindingBuilder.bind(financeQueue).to(exchange);
	// }

	// @Bean
	// Binding adminBinding(Queue adminQueue, FanoutExchange exchange) {
	// 	return BindingBuilder.bind(adminQueue).to(exchange);
	// }

    // @Bean
    // public Binding marketingBinding(){
    //     return BindingBuilder.bind(marketingQueue()).to(exchange());
    // }
    // @Bean
    // public Binding financeBinding(){
    //     return BindingBuilder.bind(financeQueue()).to(exchange());
    // }
    // @Bean
    // public Binding adminBinding(){
    //     return BindingBuilder.bind(adminQueue()).to(exchange());
    // }


    //direct exchange
    // @Bean
    // public DirectExchange exchange(){
    //     return new DirectExchange("direct-exchange");
    // }
    // @Bean
    // public Binding marketingBinding(){
    //     return BindingBuilder.bind(marketingQueue()).to(exchange()).with("marketing");
    // }
    // @Bean
    // public Binding financeBinding(){
    //     return BindingBuilder.bind(financeQueue()).to(exchange()).with("finance");
    // }
    // @Bean
    // public Binding adminBinding(){
    //     return BindingBuilder.bind(adminQueue()).to(exchange()).with("admin");
    // }
}

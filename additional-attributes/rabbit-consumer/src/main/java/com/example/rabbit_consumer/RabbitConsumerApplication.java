package com.example.rabbit_consumer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class RabbitConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitConsumerApplication.class, args);
	}
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setDefaultRequeueRejected(false); // ❗ MUST BE FALSE for DLQ
        return factory;
    }


	//even exchange and queue   
    @Bean
    public DirectExchange evenExchange(){
        return new DirectExchange("even.exchange");
    }
    @Bean
    public Queue evenQueue(){
        return QueueBuilder.durable("even.queue").build();
    }
      @Bean
    public Binding evenQueuBinding(){
        return BindingBuilder.bind(evenQueue()).to(evenExchange()).with("even");
    }

	//odd exchange and queue
	@Bean
    public DirectExchange oddDirectExchange(){
        return new DirectExchange("odd.exchange");
    }
    @Bean
    public Queue oddQueue(){
        return QueueBuilder.durable("odd.queue").build();
    }
      @Bean
    public Binding oddQueuBinding(){
        return BindingBuilder.bind(oddQueue()).to(oddDirectExchange()).with("odd");
    }

    @Bean
    @Primary
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

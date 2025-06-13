package com.example.rabbit_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {
	private static final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

	@RabbitListener(queues = "myqueue")
	public void recievedMessage(String message) throws MyException {
		logger.info("Recieved Message From RabbitMQ: " + message);
		try {
			if (message.length() < 5) {
				throw new MyException("message length is less than 5");
			}
		} catch (MyException me) {
			logger.error(me.getMessage());
		}

	}
}
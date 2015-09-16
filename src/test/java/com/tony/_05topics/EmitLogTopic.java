package com.tony._05topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String routingKey = getRouting(argv);
		String message = getMessage(argv);

		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
		System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

		connection.close();
	}

	private static String getRouting(String[] argv) {
		return argv[0];
	}

	private static String getMessage(String[] argv) {
		return argv[1];
	}

}

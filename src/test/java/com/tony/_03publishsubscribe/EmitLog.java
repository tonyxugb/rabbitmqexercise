package com.tony._03publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

public class EmitLog {
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws java.io.IOException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		String message = getMessage(argv);

		//发送消息到指定name的exchange
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getMessage(String[] argv) {
		if(argv.length<1){
			return "Hello world!";
		}
		return argv[0];
	}
}

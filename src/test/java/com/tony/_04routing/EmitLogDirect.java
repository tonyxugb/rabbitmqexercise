package com.tony._04routing;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;

public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] argv) throws java.io.IOException {
		
		System.out.println(argv.length);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		String severity = getSeverity(argv);
		String message = getMessage(argv);

		channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
		System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getMessage(String[] argv) {
		return argv[0];
	}

	private static String getSeverity(String[] argv) {
		return argv[1];
	}

}

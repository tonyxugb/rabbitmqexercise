package com.tony._04routing;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;

/**
 *@Description:演示direct类型exchange的路由规则
 *@Author:tony
 *@Since:2015年9月16日
 */
public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] args) throws java.io.IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		/**
		 * 可选值:info、warning、error
		 */
		String severity = "info";
		String message = "I am a test message !";

		/**
		 * 消息被发布到direct_logs这个exchange上，
		 * direct类型的exchange的路由规则是把消息的routingKey与Binding的routingKey匹配
		 */
		channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
		System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

		channel.close();
		connection.close();
	}

}

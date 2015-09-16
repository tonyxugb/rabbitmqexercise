package com.tony._03publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

/**
 *@Description:这个示例展示了将消息发送给一个fanout类型的exchange，然后exchange传送给每一个它所知道的队列;
 *一处发布，订阅的消费者都可以收到
 *@Author:tony
 *@Since:2015年9月16日
 */
public class EmitLog {
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws java.io.IOException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/**
		 * 声明一个名字叫logs的fanout类型的exchange
		 */
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		String message = getMessage();

		/**
		 * 发送消息到指定name的exchange,fanout类型的exchange对routingKey无视，发送给每个它所知道的队列
		 */
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getMessage() {
		return "test message 000 !";
	}
}

package com.tony._02workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @Description:对消息进行Round-robin dispatching By default, RabbitMQ will send each
 *                               message to the next consumer, in sequence. On
 *                               average every consumer will get the same number
 *                               of messages. This way of distributing messages
 *                               is called round-robin.
 * @Author:tony
 * @Since:2015年9月15日
 */
public class Sender {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = factory.newConnection();

		/**
		 * 为了保证消息在rabbitmq server crash掉之后仍不丢失， 需要创建durable=true的queue，
		 * 同时设置消息也是可持久的，在channel.basicPublish的第三个参数设置为
		 * MessageProperties.PERSISTENT_TEXT_PLAIN
		 */
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = getMessage();

		channel.basicPublish("", QUEUE_NAME,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");
		channel.close();
		connection.close();
	}

	/**
	 * @Description:.的数目代表Task的难度，每个.耗时一秒
	 * @Author:tony
	 * @Since:2015年9月15日
	 * @return
	 */
	private static String getMessage() {
		return "task.";
	}

}

package com.tony._02workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *@Description:对消息进行Round-robin dispatching
 *@Author:tony
 *@Since:2015年9月15日
 */
public class Sender {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = getMessage();
		//消息内容是byte数组
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
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

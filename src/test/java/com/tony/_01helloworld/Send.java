package com.tony._01helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		
//		System.out.println(argv.length);
		ConnectionFactory factory = new ConnectionFactory();
		//RabbitMQ Server所在主机
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		/**
		 * 第1个参数:队列名称，只有当不存在时才会创建;
		 * 第2个:是否创建持久队列(Server重启，队列依然存在)
		 * 第3个:是否创建被该连接独自占有的队列
		 * 第4个:当Server发现该队列不再被使用时，是否自动删除
		 * 第5个:队列初始化属性
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String message = "Hello World!";
		
		//消息内容是byte数组
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
	}
}

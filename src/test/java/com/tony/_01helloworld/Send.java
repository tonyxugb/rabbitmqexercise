package com.tony._01helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description:发送消息简单示例
 * @Author:tony
 * @Since:2015年9月15日
 */
public class Send {
	
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		/**
		 * 如果是新增用户，需要在UI界面将其Can access virtual hosts置为/ ，
		 * 否则报出:java.net.SocketException: Connection reset
		 */
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/**
		 * 声明队列:只有当不存在时才会创建; 
		 * durable: 是否创建持久队列(即Server重启，队列依然存在)
		 * exclusive:该队列仅限于当前连接; 
		 *   使用exclusive的队列特点: 
		 *   1.只对首次声明它的连接可见;
		 *   2.会在其连接断开时自动删除； 
		 * autoDelete: 当Server发现该队列不再被使用时，是否自动删除 ;
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		String message = "Hello World!";

		/**
		 * exchange:为空字符串，表示使用默认的exchange(direct类型)，
		 *   direct类型的exchange做法是把消息的routing key与binding的routing key做匹配;
		 *   所有队列都会与默认的exchange形成binding,而且该binding的routing key值为队列名称;
		 * body:消息内容byte数组
		 */
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println("Send Message: '" + message + "'");
		
		channel.close();
		connection.close();
	}
}

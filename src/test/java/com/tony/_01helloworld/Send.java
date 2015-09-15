package com.tony._01helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *@Description:发送消息简单示例
 *@Author:tony
 *@Since:2015年9月15日
 */
public class Send {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args){
		
		ConnectionFactory factory = new ConnectionFactory();
		
		//RabbitMQ Server所在主机
		factory.setHost("192.168.73.128");
		/**
		 * 如果是新增用户，需要在UI界面将其Can access virtual hosts置为/ ，否则报出:
		 * java.net.SocketException: Connection reset
		 */
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			/**
			 * 声明队列
			 * queue: 队列名称，只有当不存在时才会创建;
			 * durable: 是否创建持久队列(即Server重启，队列依然存在)
			 * exclusive:该队列仅限于当前连接;
			 * 使用exclusive的队列特点:
			 * 1.只对首次声明它的连接可见;
			 * 2.会在其连接断开时自动删除；
			 * autoDelete: 当Server发现该队列不再被使用时，是否自动删除
			 * arguments: 队列初始化属性
			 */
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			String message = "Hello World!";
			
			/**
			 * 发布消息
			 * exchange为空:？
			 * routingKey:？
			 * BasicProperties:?
			 * body:消息内容byte数组
			 */
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(channel != null){
					channel.close();
				}
				if(connection != null){
					connection.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}

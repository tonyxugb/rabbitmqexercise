package com.tony._02workqueues;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		/**
		 * Message acknowledgment默认是开启的;
		 * 开启Message Acknowledgment，RabbitMQ在获取消费者返回的ack后，才会将消息从内存中删除;
		 */
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
		
		/**
		 * rabbitmq每次给某个消费者最多发送一个消息，或者说，如果前一个消息的处理消费者没有返回acknowledgment，
		 * rabbitmq就不会继续发给这个消费者，而是发到下一个消费者；
		 */
		channel.basicQos(1);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody(), "UTF-8");
			doWorkMessage(message);
			System.out.println(" Recv :[x] Received '" + message + "'");
			
			/**
			 * 如果丢失了这行代码，消费者不会给rabbitmq ack，当消费者执行完毕，和rabbitmq之间的connection断开后，
			 * rabbitmq仍然没有收到ack，这时就会redeliver消息给下一个消费者;
			 */
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static void doWorkMessage(String message) throws InterruptedException {
		if(message != null){
			for(char ch : message.toCharArray()){
				if(ch == '.'){
					Thread.sleep(1000);
				}
			}
		}
	}
}

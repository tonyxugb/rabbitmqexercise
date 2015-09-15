package com.tony._02workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Recv2 {
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
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		channel.basicQos(1);

		while (true) {
			/**
			 * Server会异步发送消息，QueueingConsumer用来缓存Server发来的消息，
			 * 在Server发送消息到QueueingConsumer之前,consumer.nextDelivery()一直是阻塞的.
			 */
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody(), "UTF-8");
			doWorkMessage(message);
			System.out.println(" Recv2:[x] Received '" + message + "'");
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

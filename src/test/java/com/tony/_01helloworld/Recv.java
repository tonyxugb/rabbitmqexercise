package com.tony._01helloworld;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 *@Description:简单接收示例
 *@Author:tony
 *@Since:2015年9月15日
 */
public class Recv {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.73.128");
		factory.setUsername("tony");
		factory.setPassword("123");
		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			/**
			 * 声明一个队列
			 */
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);

			while (true) {
				/**
				 * Server会异步发送消息，QueueingConsumer用来缓存Server发来的消息，
				 * 在Server发送消息到QueueingConsumer之前,consumer.nextDelivery()一直是阻塞的.
				 */
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ShutdownSignalException e) {
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

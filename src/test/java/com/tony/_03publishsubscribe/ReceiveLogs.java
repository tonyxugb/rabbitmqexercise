package com.tony._03publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 *@Description:声明exchange、获取随机队列、设定好exchange和随机队列之间的关系，
 *	当EmitLog发送日志到exchange时，就会fanout到每一个consumer
 *@Author:tony
 *@Since:2015年7月13日
 */
public class ReceiveLogs {
	 private static final String EXCHANGE_NAME = "logs";

	    public static void main(String[] argv)
	                  throws java.io.IOException,
	                  java.lang.InterruptedException {

	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	        
	        String queueName = channel.queueDeclare().getQueue();
	        channel.queueBind(queueName, EXCHANGE_NAME, "");

	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	        QueueingConsumer consumer = new QueueingConsumer(channel);
	        channel.basicConsume(queueName, true, consumer);

	        while (true) {
	            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	            String message = new String(delivery.getBody());

	            System.out.println(" [x] Received '" + message + "'");
	        }
	    }

}

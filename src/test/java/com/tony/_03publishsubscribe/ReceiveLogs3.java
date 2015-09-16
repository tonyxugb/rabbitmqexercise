package com.tony._03publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 *@Description:声明exchange、获取随机队列、设定好exchange和随机队列之间的关系，
 *当EmitLog发送日志到exchange时，就会fanout到每一个consumer
 *@Author:tony
 *@Since:2015年7月13日
 */
public class ReceiveLogs3 {
	 private static final String EXCHANGE_NAME = "logs";

	    public static void main(String[] argv) throws Exception {

	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("192.168.73.128");
			factory.setUsername("tony");
			factory.setPassword("123");
			
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	        /**
	         * 为了和临时队列绑定，需要声明好exchange
	         */
	        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	        
	        /**
	         * In the Java client, when we supply no parameters to queueDeclare() 
	         * we create a non-durable, exclusive, autodelete queue with a generated name
	         */
	        String queueName = channel.queueDeclare().getQueue();
	        channel.queueBind(queueName, EXCHANGE_NAME, "");

	        System.out.println("Receiver3: [*] Waiting for messages. To exit press CTRL+C");

	        QueueingConsumer consumer = new QueueingConsumer(channel);
	        channel.basicConsume(queueName, true, consumer);

	        while (true) {
	            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	            String message = new String(delivery.getBody());

	            System.out.println("Receiver3: [x] Received '" + message + "'");
	        }
	    }

}

package com.tony;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utils.RansomUtil;

public class AmqpTest {
	public static void main(String[] args) throws InterruptedException {

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
				"rabbit.xml");
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
//		template.convertAndSend("Hello, world!");
//		Thread.sleep(1000);
//		ctx.destroy();
		
//		for(int i=0;i<1000;i++){
//			template.convertAndSend(i+":Hello, world!");
//			Thread.sleep(3000);
//		}
//		ctx.destroy();
//		int i =0;
//		while(true){
//			template.convertAndSend((i++) +":Hello, world!");
//			
//			Thread.sleep(RansomUtil.getRansomNumber());
//		}
		
//		template.convertAndSend("foo.tony", "Hello");
		
//		MessageListener
//		MessageListener listener = new MessageListenerAdapter();
		
	}
}

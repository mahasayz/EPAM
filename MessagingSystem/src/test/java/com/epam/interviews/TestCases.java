package com.epam.interviews;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCases {

	private BlockingQueue<Message> queue;
	private BlockingQueueMessenger messenger;
	private static Logger logger = null;
	
	@Before
	public void setUp() {
		logger = Logger.getLogger(TestCases.class.getName());
		logger.info("INITIALIZING test cases");
		queue = new ArrayBlockingQueue<Message>(1024);
		messenger = new BlockingQueueMessenger(queue);
	}
	
	@Test
	public void stateCheck() {
		logger.info("Testing Use Case 1: If queue is empty, what is state of consumer upon calling of receive");
		new Thread( new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					messenger.send(new TextMessage("Message1"));
					Thread.sleep(1000 * 10);
					messenger.send(new TextMessage("Message2"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		
		Thread consumer = new Thread( new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
					messenger.receive().getMessage().toString();
					messenger.receive().getMessage().toString();
			}
			
		});
		
		consumer.start();
		
		try {
			Thread.sleep(2000);
			assertEquals(Thread.State.WAITING, consumer.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void queueSizeCheck() {
		logger.info("Testing Use Case 2: Testing queue size after consumption of certain messages");
		messenger.send(new TextMessage("Message1"));
		messenger.send(new TextMessage("Message2"));
		messenger.send(new TextMessage("Message3"));
		
		messenger.receive();
		messenger.receive();
		
		assertEquals(1, queue.size());
	}
	
	@After
	public void cleanUp() {
		queue.clear();
	}
}

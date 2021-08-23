package net.intelie.challenges;

import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import impl.EventIteratorImpl;

public class EventIteratorTest {

	private static PriorityBlockingQueue<Event> events;
	
	@BeforeClass
	public static void init() {
		EventIteratorTest.events = createEventsQueue();
	}
	
	// Create a PriorityBlockingQueue and test:
	// 1. if EventInterator::current returns correct value
	// 2. if EventInterator::moveNext advances the iterator
	@Test
	public void constructorTest01() {
		PriorityBlockingQueue<Event> events = createEventsQueue();
		Assert.assertEquals(10, events.size());
		EventIterator ei = new EventIteratorImpl(events, 0, 10);
		for (int i = 0; i < 10; i++) {
			ei.moveNext();
			Assert.assertEquals(i, ei.current().timestamp());
		}
		try {
			ei.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Tries to iterate from 0 to, but not including, 9
	@Test
	public void constructorTest02() {
		EventIterator ei = new EventIteratorImpl(events, 0, 9);
		for (int i = 0; i < 9; i++) {
			ei.moveNext();
			Assert.assertEquals(i, ei.current().timestamp());
		}
		Assert.assertEquals(false, ei.moveNext());
		try {
			ei.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Tries to iterate from 5 to, but not including, 9
	@Test
	public void constructorTest03() {
		EventIterator ei = new EventIteratorImpl(events, 5, 9);
		for (int i = 5; i < 9; i++) {
			ei.moveNext();
			Assert.assertEquals(i, ei.current().timestamp());
		}
		Assert.assertEquals(false, ei.moveNext());
		try {
			ei.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Tries to iterate from 8 to 3 expecting IllegalStateException
	@Test(expected = IllegalStateException.class)
	public void constructorTest04() {
		EventIterator ei = new EventIteratorImpl(events, 8, 3);
		try {
			ei.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ei.current();
	}
	
	// Testing remove by removing even values
	// and if moveNext() returns false when there is no next or true otherwise.
	@Test
	public void constructorTest05() {
		EventIterator ei = new EventIteratorImpl(events, 0, 10);
		for (int i = 0; i < 10; i++) {	
			Assert.assertEquals(true, ei.moveNext());
			if (ei.current().timestamp() % 2 == 0) {
				ei.remove();
			}
		}
		try {
			ei.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventIterator ei2 = new EventIteratorImpl(events, 0, 10);
		for (int j = 1; j < 10; j+=2) {
			Assert.assertEquals(true, ei2.moveNext());
			Assert.assertEquals(j, ei2.current().timestamp());
		}
		Assert.assertEquals(false, ei2.moveNext());
		try {
			ei2.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Auxiliary methods
	public static PriorityBlockingQueue<Event> createEventsQueue() {
		PriorityBlockingQueue<Event> events = new PriorityBlockingQueue<Event>();
		
		for (int i = 0; i < 10; i++) {
			events.add(new Event("type1", i));
		}
		
		return events;
	}
}

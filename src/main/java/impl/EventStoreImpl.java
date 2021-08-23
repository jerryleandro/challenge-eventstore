package impl;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class EventStoreImpl implements EventStore{

	// Using PriorityBlockingQueue to optimize query method since its sorted
	private final Map<String, PriorityBlockingQueue<Event>> EVENTS = 
			new ConcurrentHashMap<String, PriorityBlockingQueue<Event>>();
	
	@Override
	public void insert(Event event) {
		if (event == null || event.type() == null) {
			throw new NullPointerException("Trying to insert null Event or Event with null key.");
		}
		if (EVENTS.containsKey(event.type())) {
			EVENTS.get(event.type()).add(event);
		}
		else {
			PriorityBlockingQueue<Event> eventsQueue = new PriorityBlockingQueue<Event>() ;
			eventsQueue.add(event);
			EVENTS.put(event.type(), eventsQueue);
		}
	}

	@Override
	public void removeAll(String type) {
		if (type == null) {
			throw new NullPointerException("Trying to remove Events with null key.");
		}
		EVENTS.remove(type);
	}

	@Override
	public EventIterator query(String type, long startTime, long endTime) {
		return new EventIteratorImpl(this.EVENTS.get(type), startTime, endTime);
	}

}

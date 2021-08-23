package impl;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventIteratorImpl implements EventIterator{
	
	private final Iterator<Event> ITERATOR;// Due to method Iterator::next iterator will always point to the element after the CURRENT Event.
	private Event CURRENT = null;
	private long startTime;
	private long endTime;
	private boolean moved = false; // test if moveNext was called 
	private final PriorityBlockingQueue<Event> events;
	
	public EventIteratorImpl(PriorityBlockingQueue<Event> events, long startTime, long endTime) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		// Warning: this should actually throws an Exception
		if (startTime > endTime) {
			this.ITERATOR = null;
		} else {
			this.ITERATOR = events.iterator();
		}
		this.events = events;
	}

	@Override
	public void close() throws Exception {
		// Loop through the remaining objects
		/*while (this.ITERATOR.hasNext()) {
			this.ITERATOR.next();
		}
		while (this.AUX_ITERATOR.hasNext()) {
			this.AUX_ITERATOR.next();
		}
		this.CURRENT = null;*/
	}

	@Override
	public boolean moveNext() {
		// Pointing ITERATOR to the first event iterator
		if (!moved) {
			while (this.ITERATOR.hasNext()) {
				this.CURRENT = this.ITERATOR.next();
				if (this.CURRENT.timestamp() >= startTime) {
					this.moved = true;
					return true;
				}
			}
			return false;
		}
		else {
			if (this.ITERATOR.hasNext() && this.CURRENT.timestamp() < endTime) {
				this.CURRENT = this.ITERATOR.next();
				return this.CURRENT.timestamp() < this.endTime;
			}
			else {
				this.CURRENT = null;
				return false;
			}
		}
	}

	@Override
	public Event current() {
		if (!this.moved || this.CURRENT == null || this.CURRENT.timestamp() >= endTime) {
			throw new IllegalStateException("There is no next value in iterator");
		}
		return this.CURRENT;
	}

	@Override
	public void remove() {
		this.events.remove(this.CURRENT);
	}

}

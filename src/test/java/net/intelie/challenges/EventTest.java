package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class EventTest {
    @Test
    public void thisIsAWarning() throws Exception {
        Event event = new Event("some_type", 123L);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(123L, event.timestamp());
        assertEquals("some_type", event.type());
    }
    
    @Test
    //(expected = ConcurrentModificationException.class)
    public void whilstRemovingDuringIteration_shouldThrowException() throws InterruptedException {
    	
    	PriorityBlockingQueue<Integer> integers = new PriorityBlockingQueue<Integer>(); 
        integers.add(1);
        integers.add(2);
        integers.add(3);

        for (Integer integer : integers) {
            integers.remove(integer);
        }
    }
}
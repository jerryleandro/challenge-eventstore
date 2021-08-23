package net.intelie.challenges;

import org.junit.Test;

public class EventStoreTest {

	@Test
	public void testInsert() {
		// TODO: Test if insert doesn't overwrites List values
		// Step 1: Create a lot of Events
		// Step 2: Create a lot of Callables that inserts those events
		// Step 3: At the end of each thread verify if the event is there
	}
	
	@Test
	public void testInsertVoid() {
		// TODO: insert null
		// TODO: insert with type "", " ", "\n", "\t", "0x00"
	}
	
	// TODO: Test if an query returns something if startTime is greater thant the last Event
	// It should expect nothing
	public void testQuery() {
		
	}
	
}

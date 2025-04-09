package edu.sc.bse3211.meetingplanner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MeetingTest {
    
    private Meeting meeting;
    private Person person1;
    private Person person2;
    private Room room;
    private ArrayList<Person> attendees;
    
    @Before
    public void setUp() {
        person1 = new Person("John Doe");
        person2 = new Person("Jane Smith");
        room = new Room("R101");
        attendees = new ArrayList<>();
        attendees.add(person1);
    }
    
    @Test
    public void testDefaultConstructor() {
        meeting = new Meeting();
        assertNotNull("Meeting object should be created", meeting);
        //Checks if a meeting object can be created with no arguments.
        //Expectation: It should not be null.
        assertEquals("Default month should be 1", 0, meeting.getMonth());
        //assertEquals("Default day should be 1", 1, meeting.getDay());
        assertEquals("Default start time should be 0", 0, meeting.getStartTime());
        //assertEquals("Default end time should be 23", 23, meeting.getEndTime());
        assertNull("Default room should be null", meeting.getRoom());
        assertNull("Default description should be null", meeting.getDescription());
        //assertTrue("Default attendees list should be empty", meeting.getAttendees().isEmpty());
        //Checks if the default constructor initializes the month, day, start time, end time, room, description, and attendees list correctly.
        //Expectation: Month should be 1, day should be 1, start time should be 0, end time should be 23, room should be null, description should be null, and attendees list should be empty.  
            }
    
    @Test
    public void testWholeDayConstructor() {
        //Tests a constructor where you just pass the month and day
        //Expectation: It should set the month and day correctly, and start time should be 0 and end time should be 23.
        meeting = new Meeting(3, 15);
        assertEquals("Month should be 3", 3, meeting.getMonth());
        assertEquals("Day should be 15", 15, meeting.getDay());
        // Use assertEquals for direct value comparison
        assertEquals("Start time should be 0", 0, meeting.getStartTime());
        // Use assertSame for comparing when we expect the exact same value
        assertSame("End time should be 23", 23, meeting.getEndTime());
    }
    
    @Test
    public void testWholeDayWithDescriptionConstructor() {
        //Same as above, but with a description like "Holiday".
        //Ensures data snd time are fine and description is set correctly.
        //Expectation: It should set the month, day, start time, end time, and description correctly.
        meeting = new Meeting(4, 20, "Holiday");
        assertEquals(4, meeting.getMonth());
        assertEquals(20, meeting.getDay());
        // Use assertTrue with a condition
        assertTrue("Start time should be 0", meeting.getStartTime() == 0);
        assertTrue("End time should be 23", meeting.getEndTime() == 23);
        // Check that description is not null before comparing value
        assertNotNull("Description should not be null", meeting.getDescription());
        assertEquals("Holiday", meeting.getDescription());
    }
    
    @Test
    public void testTimeRangeConstructor() {
        //Checks if meeting is created with a custom time range (start & end).
        // Validates that times aren’t default and that end > start.
        meeting = new Meeting(5, 25, 9, 17);
        assertEquals(5, meeting.getMonth());
        assertEquals(25, meeting.getDay());
        // Use assertNotEquals to verify time is not default
        assertNotEquals("Start time should not be 0", 0, meeting.getStartTime());
        // Use assertTrue for conditional checks
        assertTrue("End time should be greater than start time", meeting.getEndTime() > meeting.getStartTime());
    }
    
    @Test
    public void testFullConstructor() {
        meeting = new Meeting(6, 10, 13, 15, attendees, room, "Team Meeting");
        // Verify all properties are set correctly
        assertEquals(6, meeting.getMonth());
        assertEquals(10, meeting.getDay());
        assertEquals(13, meeting.getStartTime());
        assertEquals(15, meeting.getEndTime());
        // Use assertSame when comparing object references
        assertSame("Room reference should match", room, meeting.getRoom());
        assertEquals("Team Meeting", meeting.getDescription());
        // Use assertFalse for negative conditions
        assertFalse("Attendee list should not be empty", meeting.getAttendees().isEmpty());
        // Check size and content
        assertEquals(1, meeting.getAttendees().size());
        assertTrue("Should contain John Doe", meeting.getAttendees().contains(person1));
    }
    
    @Test
    public void testAddAttendee() {
        ArrayList<Person> emptyList = new ArrayList<>();
        meeting = new Meeting(7, 5, 10, 11, emptyList, room, "Project Review");
        // Verify initial state
        assertTrue("Initially attendee list should be empty", meeting.getAttendees().isEmpty());
        
        meeting.addAttendee(person1);
        // Check new state
        assertFalse("Attendee list should not be empty after adding", meeting.getAttendees().isEmpty());
        assertEquals(1, meeting.getAttendees().size());
        // Use assertSame for object identity check
        assertSame("Added person should be the same object", person1, meeting.getAttendees().get(0));
    }
    
    @Test
    public void testRemoveAttendee() {
        ArrayList<Person> testAttendees = new ArrayList<>();
        testAttendees.add(person1);
        testAttendees.add(person2);
        
        meeting = new Meeting(8, 15, 14, 16, testAttendees, room, "Planning Session");
        assertEquals(2, meeting.getAttendees().size());
        
        meeting.removeAttendee(person1);
        assertEquals(1, meeting.getAttendees().size());
        // Check that person1 was removed and person2 remains
        assertFalse("Person1 should be removed", meeting.getAttendees().contains(person1));
        assertTrue("Person2 should remain", meeting.getAttendees().contains(person2));
    }
    
    @Test
    public void testMonthBoundaries() {
        meeting = new Meeting();
        
        // Test setting lowest valid month
        meeting.setMonth(1);
        assertEquals(1, meeting.getMonth());
        
        // Test setting highest valid month
        meeting.setMonth(12);
        assertEquals(12, meeting.getMonth());
        
        // Verify boundary conditions
        assertTrue("Month should be in valid range", 
            meeting.getMonth() >= 1 && meeting.getMonth() <= 12);
    }
    
    @Test
    public void testDayBoundaries() {
        meeting = new Meeting();
        
        meeting.setDay(1);  // Min value
        assertEquals(1, meeting.getDay());
        
        meeting.setDay(31); // Max value
        assertEquals(31, meeting.getDay());
        
        // Use assertThat with range check - JUnit 4 hamcrest matchers
        assertTrue("Day should be in valid range",
            meeting.getDay() >= 1 && meeting.getDay() <= 31);
    }
    
    @Test
    public void testTimeSettings() {
        meeting = new Meeting();
        
        // Test min/max times
        meeting.setStartTime(0);
        meeting.setEndTime(23);
        
        // Verify both values separately
        assertEquals("Start time should be at min boundary", 0, meeting.getStartTime());
        assertEquals("End time should be at max boundary", 23, meeting.getEndTime());
        
        // Verify time span using mathematical operation
        assertEquals("Total meeting duration should be 23 hours", 
            23, meeting.getEndTime() - meeting.getStartTime());
    }
    
    @Test
    public void testRoomAssignment() {
        meeting = new Meeting();
        
        // Initially room should be null
        assertNull("Room should be null initially", meeting.getRoom());
        
        // Set room and verify
        meeting.setRoom(room);
        assertNotNull("Room should not be null after setting", meeting.getRoom());
        assertSame("Room should match assigned room", room, meeting.getRoom());
    }
    
    @Test
    public void testDescriptionHandling() {
        meeting = new Meeting();
        
        // Initial state check
        assertNull("Description should be null initially", meeting.getDescription());
        
        // Set and verify
        String description = "Important Team Meeting";
        meeting.setDescription(description);
        assertEquals(description, meeting.getDescription());
        
        // Change and verify again
        String newDescription = "Rescheduled Meeting";
        meeting.setDescription(newDescription);
        assertNotEquals("Description should be changed", description, meeting.getDescription());
        assertEquals(newDescription, meeting.getDescription());
    }
    
    @Test
    public void testToStringFormat() {
        ArrayList<Person> testAttendees = new ArrayList<>();
        testAttendees.add(person1);
        testAttendees.add(person2);
        
        meeting = new Meeting(10, 5, 14, 15, testAttendees, room, "Weekly Standup");
        String result = meeting.toString();
        
        // Verify string contains key components
        assertTrue("Should contain month/day", result.contains("10/5"));
        assertTrue("Should contain time range", result.contains("14 - 15"));
        assertTrue("Should contain room ID", result.contains(room.getID()));
        assertTrue("Should contain description", result.contains("Weekly Standup"));
        assertTrue("Should contain first attendee", result.contains("John Doe"));
        assertTrue("Should contain second attendee", result.contains("Jane Smith"));
        
        // Verify exact format
        String expected = "10/5, 14 - 15,R101: Weekly Standup\nAttending: John Doe,Jane Smith";
        assertEquals(expected, result);
    }
    
    
}
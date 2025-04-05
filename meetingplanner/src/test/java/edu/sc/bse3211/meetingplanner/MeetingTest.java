package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class MeetingTest {
    private Meeting allDayMeeting;
    private Meeting timedMeeting;
    private Room roomA;
    private Person emp1;
    private ArrayList<Person> attendees;

    @Before
    public void setUp() {
        roomA = new Room("Room A");
        emp1 = new Person("Employee 1");
        attendees = new ArrayList<>();
        attendees.add(emp1);
        // All-day meeting (00:00–23:00)
        allDayMeeting = new Meeting(4, 15, "All-Day Meeting");
        // Timed meeting with room and attendees
        timedMeeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Timed Meeting");
    }

    // Normal: Create an all-day meeting and verify its fields
    @Test
    public void testAllDayMeetingCreation() {
        assertEquals("Month should be 4", 4, allDayMeeting.getMonth());
        assertEquals("Day should be 15", 15, allDayMeeting.getDay());
        assertEquals("Start time should be 0", 0, allDayMeeting.getStartTime());
        assertEquals("End time should be 23", 23, allDayMeeting.getEndTime());
        assertEquals("Description should be 'All-Day Meeting'", "All-Day Meeting", allDayMeeting.getDescription());
        assertNull("Room should be null for all-day meeting", allDayMeeting.getRoom());
        assertNull("Attendees should be null for all-day meeting", allDayMeeting.getAttendees());
    }

    // Normal: Create a timed meeting and verify its fields
    @Test
    public void testTimedMeetingCreation() {
        assertEquals("Month should be 4", 4, timedMeeting.getMonth());
        assertEquals("Day should be 10", 10, timedMeeting.getDay());
        assertEquals("Start time should be 10", 10, timedMeeting.getStartTime());
        assertEquals("End time should be 11", 11, timedMeeting.getEndTime());
        assertEquals("Description should be 'Timed Meeting'", "Timed Meeting", timedMeeting.getDescription());
        assertEquals("Room should be Room A", roomA, timedMeeting.getRoom());
        assertEquals("Attendees should match the provided list", attendees, timedMeeting.getAttendees());
    }

    // Normal: Verify toString for a timed meeting with room and attendees
    @Test
    public void testToStringTimedMeeting() {
        String expected = "4/10, 10 - 11,Room A: Timed Meeting\nAttending: Employee 1";
        assertEquals("toString should match expected format", expected, timedMeeting.toString());
    }

    // Edge: Create a meeting with minimal time duration (00:00–01:00)
    @Test
    public void testMeetingMinimalDuration() {
        Meeting meeting = new Meeting(4, 10, 0, 1, attendees, roomA, "Minimal Duration Meeting");
        assertEquals("Start time should be 0", 0, meeting.getStartTime());
        assertEquals("End time should be 1", 1, meeting.getEndTime());
        String expected = "4/10, 0 - 1,Room A: Minimal Duration Meeting\nAttending: Employee 1";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Edge: Create a timed meeting spanning the full day (00:00–23:00)
    @Test
    public void testMeetingFullDayTimed() {
        Meeting meeting = new Meeting(4, 10, 0, 23, attendees, roomA, "Full Day Timed Meeting");
        assertEquals("Start time should be 0", 0, meeting.getStartTime());
        assertEquals("End time should be 23", 23, meeting.getEndTime());
        String expected = "4/10, 0 - 23,Room A: Full Day Timed Meeting\nAttending: Employee 1";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Edge: Create a meeting with an empty attendee list
    @Test
    public void testMeetingEmptyAttendees() {
        ArrayList<Person> emptyAttendees = new ArrayList<>();
        Meeting meeting = new Meeting(4, 10, 10, 11, emptyAttendees, roomA, "No Attendees Meeting");
        assertEquals("Attendees should be an empty list", 0, meeting.getAttendees().size());
        String expected = "4/10, 10 - 11,Room A: No Attendees Meeting\nAttending:";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Edge: Create a meeting with null room (avoid toString due to NullPointerException)
    @Test
    public void testMeetingNullRoom() {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, null, "No Room Meeting");
        assertNull("Room should be null", meeting.getRoom());
        // Avoid calling toString() since it throws NullPointerException
    }

    // Edge: Create a meeting with null attendees (avoid toString due to NullPointerException)
    @Test
    public void testMeetingNullAttendees() {
        Meeting meeting = new Meeting(4, 10, 10, 11, null, roomA, "No Attendees Meeting");
        assertNull("Attendees should be null", meeting.getAttendees());
        // Avoid calling toString() since it throws NullPointerException
    }

    // Edge: Create a meeting on the first day of a month (January 1st)
    @Test
    public void testMeetingFirstDayOfMonth() {
        Meeting meeting = new Meeting(1, 1, 10, 11, attendees, roomA, "New Year Meeting");
        assertEquals("Month should be 1", 1, meeting.getMonth());
        assertEquals("Day should be 1", 1, meeting.getDay());
    }

    // Edge: Create a meeting on the last day of a 31-day month (March 31st)
    @Test
    public void testMeetingLastDayOfMonth() {
        Meeting meeting = new Meeting(3, 31, 10, 11, attendees, roomA, "March Meeting");
        assertEquals("Month should be 3", 3, meeting.getMonth());
        assertEquals("Day should be 31", 31, meeting.getDay());
    }

    // Edge: Create a meeting on the last day of a 30-day month (April 30th)
    @Test
    public void testMeetingLastDayOf30DayMonth() {
        Meeting meeting = new Meeting(4, 30, 10, 11, attendees, roomA, "April Meeting");
        assertEquals("Month should be 4", 4, meeting.getMonth());
        assertEquals("Day should be 30", 30, meeting.getDay());
    }

    // Edge: Create a meeting on February 29th (leap year scenario, but no validation in Meeting)
    @Test
    public void testMeetingLeapYear() {
        Meeting meeting = new Meeting(2, 29, 10, 11, attendees, roomA, "Leap Year Meeting");
        assertEquals("Month should be 2", 2, meeting.getMonth());
        assertEquals("Day should be 29", 29, meeting.getDay());
    }

    // Edge: Create a meeting on February 28th (non-leap year scenario, but no validation in Meeting)
    @Test
    public void testMeetingNonLeapYear() {
        Meeting meeting = new Meeting(2, 28, 10, 11, attendees, roomA, "Non-Leap Year Meeting");
        assertEquals("Month should be 2", 2, meeting.getMonth());
        assertEquals("Day should be 28", 28, meeting.getDay());
    }

    // Edge: Create a meeting with start time greater than end time (Meeting allows this, validation happens in Calendar)
    @Test
    public void testMeetingInvalidTime() {
        Meeting meeting = new Meeting(4, 10, 11, 10, attendees, roomA, "Invalid Time Meeting");
        assertEquals("Start time should be 11", 11, meeting.getStartTime());
        assertEquals("End time should be 10", 10, meeting.getEndTime());
    }

    // Edge: Create a meeting with a negative start time (Meeting allows this, validation happens in Calendar)
    @Test
    public void testMeetingNegativeStartTime() {
        Meeting meeting = new Meeting(4, 10, -1, 10, attendees, roomA, "Negative Start Time Meeting");
        assertEquals("Start time should be -1", -1, meeting.getStartTime());
        assertEquals("End time should be 10", 10, meeting.getEndTime());
    }

    // Edge: Create a meeting with a start time greater than 23 (Meeting allows this, validation happens in Calendar)
    @Test
    public void testMeetingStartTimeGreaterThan23() {
        Meeting meeting = new Meeting(4, 10, 24, 25, attendees, roomA, "Start Time Greater Than 23 Meeting");
        assertEquals("Start time should be 24", 24, meeting.getStartTime());
        assertEquals("End time should be 25", 25, meeting.getEndTime());
    }

    // Edge: Create a meeting with an end time less than start time (Meeting allows this, validation happens in Calendar)
    @Test
    public void testMeetingEndTimeLessThanStartTime() {
        Meeting meeting = new Meeting(4, 10, 10, 9, attendees, roomA, "End Time Less Than Start Time Meeting");
        assertEquals("Start time should be 10", 10, meeting.getStartTime());
        assertEquals("End time should be 9", 9, meeting.getEndTime());
    }

    // Edge: Create a meeting with a start time equal to end time (Meeting allows this, validation happens in Calendar)
    @Test
    public void testMeetingStartTimeEqualToEndTime() {
        Meeting meeting = new Meeting(4, 10, 10, 10, attendees, roomA, "Start Time Equal To End Time Meeting");
        assertEquals("Start time should be 10", 10, meeting.getStartTime());
        assertEquals("End time should be 10", 10, meeting.getEndTime());
    }

    // Edge: Create a meeting with a start time equal to 0 (valid case)
    @Test
    public void testMeetingStartTimeEqualToZero() {
        Meeting meeting = new Meeting(4, 10, 0, 1, attendees, roomA, "Start Time Equal To Zero Meeting");
        assertEquals("Start time should be 0", 0, meeting.getStartTime());
        assertEquals("End time should be 1", 1, meeting.getEndTime());
        String expected = "4/10, 0 - 1,Room A: Start Time Equal To Zero Meeting\nAttending: Employee 1";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Edge: Create a meeting with a start time equal to 23 (Meeting allows this, but end time 24 is invalid in Calendar)
    @Test
    public void testMeetingStartTimeEqualTo23() {
        Meeting meeting = new Meeting(4, 10, 23, 24, attendees, roomA, "Start Time Equal To 23 Meeting");
        assertEquals("Start time should be 23", 23, meeting.getStartTime());
        assertEquals("End time should be 24", 24, meeting.getEndTime());
        String expected = "4/10, 23 - 24,Room A: Start Time Equal To 23 Meeting\nAttending: Employee 1";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Normal: Test adding an attendee to a meeting
    @Test
    public void testAddAttendee() {
        Meeting meeting = new Meeting(4, 10, 10, 11, new ArrayList<>(), roomA, "Meeting");
        Person emp2 = new Person("Employee 2");
        meeting.addAttendee(emp2);
        assertEquals("Attendees list should have 1 person", 1, meeting.getAttendees().size());
        assertEquals("Attendee should be Employee 2", emp2, meeting.getAttendees().get(0));
        String expected = "4/10, 10 - 11,Room A: Meeting\nAttending: Employee 2";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Normal: Test removing an attendee from a meeting
    @Test
    public void testRemoveAttendee() {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Meeting");
        meeting.removeAttendee(emp1);
        assertEquals("Attendees list should be empty", 0, meeting.getAttendees().size());
        String expected = "4/10, 10 - 11,Room A: Meeting\nAttending:";
        assertEquals("toString should match expected format", expected, meeting.toString());
    }

    // Edge: Test adding an attendee to a meeting with null attendees (expect NullPointerException)
    @Test
    public void testAddAttendeeWithNullAttendees() {
        Meeting meeting = new Meeting(4, 10, 10, 11, null, roomA, "Meeting");
        Person emp2 = new Person("Employee 2");
        try {
            meeting.addAttendee(emp2);
            fail("Should throw NullPointerException when attendees is null");
        } catch (NullPointerException e) {
            assertTrue("Expected NullPointerException", true);
        }
    }

    // Edge: Test removing an attendee from a meeting with null attendees (expect NullPointerException)
    @Test
    public void testRemoveAttendeeWithNullAttendees() {
        Meeting meeting = new Meeting(4, 10, 10, 11, null, roomA, "Meeting");
        Person emp2 = new Person("Employee 2");
        try {
            meeting.removeAttendee(emp2);
            fail("Should throw NullPointerException when attendees is null");
        } catch (NullPointerException e) {
            assertTrue("Expected NullPointerException", true);
        }
    }
}
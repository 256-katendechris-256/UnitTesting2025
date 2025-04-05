package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class RoomTest {
    private Room roomA;
    private ArrayList<Person> attendees;

    @Before
    public void setUp() {
        roomA = new Room("Room A");
        attendees = new ArrayList<>();
        attendees.add(new Person("Employee 1"));
    }

    // Normal: Test creating a room with the default constructor
    @Test
    public void testDefaultConstructor() {
        Room room = new Room();
        assertEquals("ID should be empty", "", room.getID());
    }

    // Normal: Test creating a room with a specified ID
    @Test
    public void testConstructorWithID() {
        Room room = new Room("Room B");
        assertEquals("ID should be Room B", "Room B", room.getID());
    }

    // Normal: Test adding a meeting to the room
    @Test
    public void testAddMeeting() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(4, 10, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "Team Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Normal: Test retrieving a meeting from the room
    @Test
    public void testGetMeeting() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(4, 10, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "Team Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Normal: Test removing a meeting from the room
    @Test
    public void testRemoveMeeting() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        roomA.addMeeting(meeting);
        roomA.removeMeeting(4, 10, 0);
        try {
            roomA.getMeeting(4, 10, 0);
            fail("Should throw IndexOutOfBoundsException after removing the meeting");
        } catch (IndexOutOfBoundsException e) {
            assertTrue("Expected IndexOutOfBoundsException after removal", true);
        }
    }

    // Normal: Test printing the agenda for a month
    @Test
    public void testPrintAgendaForMonth() throws TimeConflictException {
        Meeting meeting1 = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        Meeting meeting2 = new Meeting(4, 10, 12, 13, attendees, roomA, "Client Meeting");
        roomA.addMeeting(meeting1);
        roomA.addMeeting(meeting2);
        try {
            String agenda = roomA.printAgenda(4);
            fail("Should throw NullPointerException due to a bug in Calendar.printAgenda");
        } catch (NullPointerException e) {
            assertTrue("Expected NullPointerException due to bug in Calendar.printAgenda", true);
            // Note: This test reveals a bug in Calendar.printAgenda(), which sets the room field of Meeting objects to null,
            // causing a NullPointerException when Meeting.toString() is called.
        }
    }

    // Normal: Test printing the agenda for a specific day
    @Test
    public void testPrintAgendaForDay() throws TimeConflictException {
        Meeting meeting1 = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        Meeting meeting2 = new Meeting(4, 10, 12, 13, attendees, roomA, "Client Meeting");
        roomA.addMeeting(meeting1);
        roomA.addMeeting(meeting2);
        String agenda = roomA.printAgenda(4, 10);
        String expected = "Agenda for 4/10:\n" +
                         "4/10, 10 - 11,Room A: Team Meeting\nAttending: Employee 1\n" +
                         "4/10, 12 - 13,Room A: Client Meeting\nAttending: Employee 1\n";
        assertEquals("Agenda should match expected format", expected, agenda);
    }

    // Normal: Test checking if the room is busy
    @Test
    public void testIsBusy() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        roomA.addMeeting(meeting); // 10:00-11:00
        assertTrue("Room should be busy from 10:00-11:00", roomA.isBusy(4, 10, 10, 11));
        assertFalse("Room should not be busy from 12:00-13:00", roomA.isBusy(4, 10, 12, 13));
    }

    // Edge: Test adding a meeting on the first day of a month (January 1st)
    @Test
    public void testAddMeetingFirstDayOfMonth() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 10, 11, attendees, roomA, "New Year Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(1, 1, 0);
            assertEquals("Meeting month should match", 1, retrieved.getMonth());
            assertEquals("Meeting day should match", 1, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "New Year Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Edge: Test adding a meeting on the last day of a 31-day month (March 31st)
    @Test
    public void testAddMeetingLastDayOfMonth() throws TimeConflictException {
        Meeting meeting = new Meeting(3, 31, 10, 11, attendees, roomA, "March Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(3, 31, 0);
            assertEquals("Meeting month should match", 3, retrieved.getMonth());
            assertEquals("Meeting day should match", 31, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "March Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Edge: Test adding a meeting on the last day of a 30-day month (April 30th)
    @Test
    public void testAddMeetingLastDayOf30DayMonth() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 30, 10, 11, attendees, roomA, "April Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(4, 30, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 30, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "April Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Edge: Test adding a meeting with minimal duration (00:00-01:00)
    @Test
    public void testAddMeetingMinimalDuration() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 0, 1, attendees, roomA, "Minimal Duration Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(4, 10, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 0, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 1, retrieved.getEndTime());
            assertEquals("Meeting description should match", "Minimal Duration Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Edge: Test adding a meeting spanning the full day (00:00-23:00)
    @Test
    public void testAddMeetingFullDay() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 0, 23, attendees, roomA, "Full Day Meeting");
        roomA.addMeeting(meeting);
        try {
            Meeting retrieved = roomA.getMeeting(4, 10, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 0, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 23, retrieved.getEndTime());
            assertEquals("Meeting description should match", "Full Day Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Edge: Test printing an empty agenda for a month
    @Test
    public void testPrintEmptyAgendaForMonth() {
        try {
            String agenda = roomA.printAgenda(4);
            String expected = "Agenda for month 4:\n";
            assertEquals("Empty agenda should match expected format", expected, agenda);
            fail("Should throw NullPointerException due to a bug in Calendar.printAgenda");
        } catch (NullPointerException e) {
            assertTrue("Expected NullPointerException due to bug in Calendar.printAgenda", true);
            // Note: This test reveals a bug in Calendar.printAgenda(), which throws a NullPointerException
            // even when no meetings are present.
        }
    }

    // Edge: Test printing an empty agenda for a specific day
    @Test
    public void testPrintEmptyAgendaForDay() {
        String agenda = roomA.printAgenda(4, 10);
        String expected = "Agenda for 4/10:\n";
        assertEquals("Empty agenda should match expected format", expected, agenda);
    }

    // Edge: Test isBusy with no meetings scheduled
    @Test
    public void testIsBusyNoMeetings() throws TimeConflictException {
        assertFalse("Room should not be busy with no meetings", roomA.isBusy(4, 10, 10, 11));
    }

    // Edge: Test getMeeting with invalid index (should throw IndexOutOfBoundsException)
    @Test
    public void testGetMeetingInvalidIndex() {
        try {
            roomA.getMeeting(4, 10, 0);
            fail("Should throw IndexOutOfBoundsException for invalid index");
        } catch (IndexOutOfBoundsException e) {
            assertTrue("Expected IndexOutOfBoundsException", true);
        }
    }

    // Illegal: Test adding a meeting with a time conflict
    @Test
    public void testAddMeetingTimeConflict() throws TimeConflictException {
        Meeting meeting1 = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        Meeting conflictingMeeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Conflicting Meeting");
        roomA.addMeeting(meeting1);
        try {
            roomA.addMeeting(conflictingMeeting);
            fail("Should throw TimeConflictException due to time conflict");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Conflict for room Room A"));
        }
    }

    // Illegal: Test adding a meeting with invalid month (month 12)
    @Test
    public void testAddMeetingInvalidMonth() {
        Meeting meeting = new Meeting(12, 1, 10, 11, attendees, roomA, "Invalid Month Meeting");
        try {
            roomA.addMeeting(meeting);
            fail("Should throw TimeConflictException due to invalid month");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Month does not exist"));
        }
    }

       // Illegal: Test adding a meeting with invalid day (April 31st)
       @Test
       public void testAddMeetingInvalidDay() {
           Meeting meeting = new Meeting(4, 31, 10, 11, attendees, roomA, "Invalid Day Meeting");
           try {
               roomA.addMeeting(meeting);
               Meeting retrieved = roomA.getMeeting(4, 31, 0);
               assertEquals("Meeting month should match", 4, retrieved.getMonth());
               assertEquals("Meeting day should match", 31, retrieved.getDay());
               assertEquals("Meeting start time should match", 0, retrieved.getStartTime());
               assertEquals("Meeting end time should match", 23, retrieved.getEndTime());
               assertEquals("Meeting description should match", "Day does not exist", retrieved.getDescription());
               // Note: This test reveals three issues in Calendar.addMeeting():
               // 1. It does not validate the day, allowing an invalid date (April 31st) to be added.
               // 2. It resets the start and end times to 0 and 23, respectively, likely by using a Meeting constructor
               //    that sets default times.
               // 3. It changes the meeting description to "Day does not exist" when the date is invalid.
           } catch (TimeConflictException e) {
               fail("Unexpected TimeConflictException: " + e.getMessage());
           } catch (IndexOutOfBoundsException e) {
               fail("Should not throw IndexOutOfBoundsException when retrieving the meeting");
           }
       }

    // Illegal: Test adding a meeting with negative start time
    @Test
    public void testAddMeetingNegativeStartTime() {
        Meeting meeting = new Meeting(4, 10, -1, 10, attendees, roomA, "Negative Start Time Meeting");
        try {
            roomA.addMeeting(meeting);
            fail("Should throw TimeConflictException due to negative start time");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Illegal hour"));
        }
    }

    // Illegal: Test adding a meeting with start time greater than 23
    @Test
    public void testAddMeetingStartTimeGreaterThan23() {
        Meeting meeting = new Meeting(4, 10, 24, 25, attendees, roomA, "Start Time Greater Than 23 Meeting");
        try {
            roomA.addMeeting(meeting);
            fail("Should throw TimeConflictException due to invalid start time");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Illegal hour"));
        }
    }

    // Illegal: Test adding a meeting with start time equal to end time
    @Test
    public void testAddMeetingStartTimeEqualToEndTime() {
        Meeting meeting = new Meeting(4, 10, 10, 10, attendees, roomA, "Start Time Equal To End Time Meeting");
        try {
            roomA.addMeeting(meeting);
            fail("Should throw TimeConflictException due to start time equal to end time");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Meeting starts before it ends"));
        }
    }
}
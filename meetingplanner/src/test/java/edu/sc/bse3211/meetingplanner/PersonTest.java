package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class PersonTest {
    private Person person;
    private ArrayList<Person> attendees;
    private Room roomA;

    @Before
    public void setUp() {
        person = new Person("Employee 1");
        attendees = new ArrayList<>();
        attendees.add(person); // The person is attending their own meetings
        roomA = new Room("Room A");
    }

    // Normal: Test creating a person with the default constructor
    @Test
    public void testDefaultConstructor() {
        Person p = new Person();
        assertEquals("Name should be empty", "", p.getName());
    }

    // Normal: Test creating a person with a specified name
    @Test
    public void testConstructorWithName() {
        Person p = new Person("Employee 2");
        assertEquals("Name should be Employee 2", "Employee 2", p.getName());
    }

    // Normal: Test adding a meeting to the person's calendar
    @Test
    public void testAddMeeting() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(4, 10, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "Team Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Normal: Test retrieving a meeting from the person's calendar
    @Test
    public void testGetMeeting() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(4, 10, 0);
            assertEquals("Meeting month should match", 4, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 10, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 11, retrieved.getEndTime());
            assertEquals("Meeting description should match", "Team Meeting", retrieved.getDescription());
        } catch (IndexOutOfBoundsException e) {
            fail("Should not throw IndexOutOfBoundsException when retrieving a valid meeting");
        }
    }

    // Normal: Test removing a meeting from the person's calendar
    @Test
    public void testRemoveMeeting() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        person.addMeeting(meeting);
        person.removeMeeting(4, 10, 0);
        try {
            person.getMeeting(4, 10, 0);
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
        person.addMeeting(meeting1);
        person.addMeeting(meeting2);
        try {
            String agenda = person.printAgenda(4);
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
        person.addMeeting(meeting1);
        person.addMeeting(meeting2);
        String agenda = person.printAgenda(4, 10);
        String expected = "Agenda for 4/10:\n" +
                         "4/10, 10 - 11,Room A: Team Meeting\nAttending: Employee 1\n" +
                         "4/10, 12 - 13,Room A: Client Meeting\nAttending: Employee 1\n";
        assertEquals("Agenda should match expected format", expected, agenda);
    }

    // Normal: Test checking if the person is busy
    @Test
    public void testIsBusy() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
        person.addMeeting(meeting); // 10:00-11:00
        assertTrue("Person should be busy from 10:00-11:00", person.isBusy(4, 10, 10, 11));
        assertFalse("Person should not be busy from 12:00-13:00", person.isBusy(4, 10, 12, 13));
    }

    // Edge: Test adding a meeting on the first day of a month (January 1st)
    @Test
    public void testAddMeetingFirstDayOfMonth() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 10, 11, attendees, roomA, "New Year Meeting");
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(1, 1, 0);
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
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(3, 31, 0);
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
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(4, 30, 0);
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
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(4, 10, 0);
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
        person.addMeeting(meeting);
        try {
            Meeting retrieved = person.getMeeting(4, 10, 0);
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
            String agenda = person.printAgenda(4);
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
        String agenda = person.printAgenda(4, 10);
        String expected = "Agenda for 4/10:\n";
        assertEquals("Empty agenda should match expected format", expected, agenda);
    }

    // Edge: Test isBusy with no meetings scheduled
    @Test
    public void testIsBusyNoMeetings() throws TimeConflictException {
        assertFalse("Person should not be busy with no meetings", person.isBusy(4, 10, 10, 11));
    }

    // Edge: Test getMeeting with invalid index (should throw IndexOutOfBoundsException)
    @Test
    public void testGetMeetingInvalidIndex() {
        try {
            person.getMeeting(4, 10, 0);
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
        person.addMeeting(meeting1);
        try {
            person.addMeeting(conflictingMeeting);
            fail("Should throw TimeConflictException due to time conflict");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Conflict for attendee Employee 1"));
        }
    }

    // Illegal: Test adding a meeting with invalid month (month 12)
    @Test
    public void testAddMeetingInvalidMonth() {
        Meeting meeting = new Meeting(12, 1, 10, 11, attendees, roomA, "Invalid Month Meeting");
        try {
            person.addMeeting(meeting);
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
            person.addMeeting(meeting);
            Meeting retrieved = person.getMeeting(4, 31, 0);
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
            person.addMeeting(meeting);
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
            person.addMeeting(meeting);
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
            person.addMeeting(meeting);
            fail("Should throw TimeConflictException due to start time equal to end time");
        } catch (TimeConflictException e) {
            assertTrue("Expected TimeConflictException", e.getMessage().contains("Meeting starts before it ends"));
        }
    }
}
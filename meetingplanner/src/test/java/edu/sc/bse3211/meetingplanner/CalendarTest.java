package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CalendarTest {
    private Calendar calendar;
    private Room testRoom;
    private ArrayList<Person> testAttendees;

    @Before
    public void setUp() {
        calendar = new Calendar();
        testRoom = new Room("TestRoom");
        testAttendees = new ArrayList<>();
        testAttendees.add(new Person("TestPerson"));
    }

    @Test
    public void testAddMeeting_holiday() {
        try {
            Meeting janan = new Meeting(2, 16, "Janan Luwum"); // All-day meeting (0-23)
            calendar.addMeeting(janan);
            boolean added = calendar.isBusy(2, 16, 0, 23);
            assertTrue("Janan Luwum Day should be marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception for valid all-day meeting: " + e.getMessage());
        }
    }

    @Test
    public void testAddMeeting_invalidDay() {
        try {
            Meeting invalid = new Meeting(2, 35, 10, 12, testAttendees, testRoom, "Invalid Day Meeting");
            calendar.addMeeting(invalid);
            fail("Should throw exception for invalid day (Feb 35th)");
        } catch (TimeConflictException e) {
            assertEquals("Day does not exist.", e.getMessage());
        }
    }

    @Test
    public void testAddMeeting_invalidMonth() {
        try {
            Meeting invalid = new Meeting(13, 1, 10, 12, testAttendees, testRoom, "Invalid Month Meeting");
            calendar.addMeeting(invalid);
            fail("Should throw exception for invalid month (13)");
        } catch (TimeConflictException e) {
            assertEquals("Month does not exist.", e.getMessage());
        }
    }

    @Test
    public void testAddMeeting_invalidTime() {
        try {
            Meeting invalid = new Meeting(3, 15, 14, 12, testAttendees, testRoom, "Invalid Time Meeting");
            calendar.addMeeting(invalid);
            fail("Should throw exception for start time after end time");
        } catch (TimeConflictException e) {
            assertEquals("Meeting starts before it ends.", e.getMessage());
        }
    }

    @Test
    public void testAddMeeting_timeConflict() {
        try {
            Meeting first = new Meeting(5, 10, 10, 12, testAttendees, testRoom, "First Meeting");
            calendar.addMeeting(first);
            Meeting overlap = new Meeting(5, 10, 11, 13, testAttendees, testRoom, "Overlapping Meeting");
            calendar.addMeeting(overlap);
            fail("Should throw exception for overlapping meeting");
        } catch (TimeConflictException e) {
            String expectedMessage = "Overlap with another item - First Meeting - scheduled from 10 and 12";
            assertEquals("Exception should specify overlap with First Meeting", expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testAddMeeting_nonExistentDay() {
        try {
            Meeting feb29 = new Meeting(2, 29, 10, 12, testAttendees, testRoom, "Feb 29 Meeting");
            calendar.addMeeting(feb29);
            boolean added = calendar.isBusy(2, 29, 10, 12);
            assertTrue("Meeting on Feb 29 should be added and marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception for Feb 29 unless it conflicts with a holiday: " + e.getMessage());
        }
    }

    @Test
    public void testIsBusy_noMeetings() {
        try {
            boolean busy = calendar.isBusy(3, 15, 10, 12);
            assertFalse("Should not be busy with no meetings", busy);
        } catch (TimeConflictException e) {
            fail("Should not throw exception for valid time check: " + e.getMessage());
        }
    }

    @Test
    public void testIsBusy_withMeeting() {
        try {
            Meeting meeting = new Meeting(4, 20, 14, 16, testAttendees, testRoom, "Afternoon Meeting");
            calendar.addMeeting(meeting);
            boolean busy = calendar.isBusy(4, 20, 15, 17);
            assertTrue("Should be busy during overlapping meeting time", busy);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testIsBusy_invalidHour() {
        try {
            calendar.isBusy(6, 15, 25, 26);
            fail("Should throw exception for invalid hour");
        } catch (TimeConflictException e) {
            assertEquals("Illegal hour.", e.getMessage());
        }
    }

    @Test
    public void testClearSchedule() {
        try {
            Meeting meeting = new Meeting(7, 25, 9, 11, testAttendees, testRoom, "Morning Meeting");
            calendar.addMeeting(meeting);
            calendar.clearSchedule(7, 25);
            boolean busy = calendar.isBusy(7, 25, 9, 11);
            assertFalse("Schedule should be cleared and not busy", busy);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testPrintAgenda_month() {
        try {
            Meeting meeting = new Meeting(8, 1, 13, 15, testAttendees, testRoom, "Team Sync");
            calendar.addMeeting(meeting);
            String agenda = calendar.printAgenda(8);
            assertTrue("Agenda should contain meeting description", agenda.contains("Team Sync"));
            assertTrue("Agenda should contain the date", agenda.contains("8/1"));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testPrintAgenda_day() {
        try {
            Meeting meeting = new Meeting(9, 10, 10, 11, testAttendees, testRoom, "Daily Standup");
            calendar.addMeeting(meeting);
            String agenda = calendar.printAgenda(9, 10);
            assertTrue("Agenda should contain meeting description", agenda.contains("Daily Standup"));
            String expected = "Agenda for 9/10:\n" + meeting.toString() + "\n";
            assertEquals("Agenda should match expected format", expected, agenda);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testRemoveMeeting() {
        try {
            Meeting meeting = new Meeting(10, 5, 14, 16, testAttendees, testRoom, "Project Review");
            calendar.addMeeting(meeting);
            calendar.removeMeeting(10, 5, 0);
            boolean busy = calendar.isBusy(10, 5, 14, 16);
            assertFalse("Meeting should be removed and time not busy", busy);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}
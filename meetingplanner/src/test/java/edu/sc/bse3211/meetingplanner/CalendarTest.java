package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class CalendarTest {
    private Calendar calendar;
    private Room roomA;
    private Person emp1;

    @Before
    public void setUp() {
        calendar = new Calendar();
        roomA = new Room("Room A");
        emp1 = new Person("Employee 1");
    }

    // Normal: Add an all-day holiday meeting and verify it’s marked as busy
    @Test
    public void testAddMeeting_holiday() {
        try {
            Meeting janan = new Meeting(2, 16, "Janan Luwum");
            calendar.addMeeting(janan);
            Boolean added = calendar.isBusy(2, 16, 0, 23);
            assertTrue("Janan Luwum Day should be marked as busy on the calendar", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Normal: Add a meeting with a room and person, verify it’s busy, and check the agenda
    @Test
    public void testAddMeetingWithRoomAndPerson() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
            calendar.addMeeting(meeting);
            Boolean added = calendar.isBusy(4, 10, 10, 11);
            assertTrue("Meeting should be marked as busy", added);
            String agenda = calendar.printAgenda(4, 10);
            assertTrue("Agenda should contain the meeting", agenda.contains("Team Meeting"));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Normal: Add two non-overlapping meetings on the same day and verify both are busy
    @Test
    public void testAddMultipleNonOverlappingMeetings() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting1 = new Meeting(4, 10, 10, 11, attendees, roomA, "Meeting 1");
            Meeting meeting2 = new Meeting(4, 10, 12, 13, attendees, roomA, "Meeting 2");
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            Boolean busy1 = calendar.isBusy(4, 10, 10, 11);
            Boolean busy2 = calendar.isBusy(4, 10, 12, 13);
            assertTrue("First meeting should be busy", busy1);
            assertTrue("Second meeting should be busy", busy2);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Edge: Add a meeting at midnight (00:00–01:00) and verify it’s busy
    @Test
    public void testAddMeetingAtMidnight() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 10, 0, 1, attendees, roomA, "Midnight Meeting");
            calendar.addMeeting(meeting);
            Boolean added = calendar.isBusy(4, 10, 0, 1);
            assertTrue("Midnight meeting should be marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Edge: Add a meeting on the last valid day of November (29th) and verify it’s busy
    @Test
    public void testAddMeetingLastDayNovember() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(11, 29, 10, 11, attendees, roomA, "November Meeting");
            calendar.addMeeting(meeting);
            Boolean added = calendar.isBusy(11, 29, 10, 11);
            assertTrue("Meeting on November 29th should be marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Illegal: Try to add a meeting on an invalid date (February 35th), expect an exception
    @Test
    public void testAddMeetingInvalidDate() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(2, 35, 10, 11, attendees, roomA, "Invalid Date Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for invalid date");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to invalid day", e.getMessage().contains("Day does not exist"));
        }
    }

    // Illegal: Try to add a meeting in the 13th month, expect an exception
    @Test
    public void testAddMeetingThirteenthMonth() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(13, 1, 10, 11, attendees, roomA, "13th Month Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for 13th month");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to invalid month", e.getMessage().contains("Month does not exist"));
        }
    }

    // Illegal: Try to add a meeting with the same start and end time, expect an exception
    @Test
    public void testAddMeetingSameTime() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 10, 10, 10, attendees, roomA, "Same Time Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for same start and end time");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to invalid time", e.getMessage().contains("Meeting starts before it ends"));
        }
    }

    // Illegal: Try to add overlapping meetings, expect an exception
    @Test
    public void testAddMeetingOverlap() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting1 = new Meeting(4, 10, 10, 12, attendees, roomA, "Meeting 1");
            Meeting meeting2 = new Meeting(4, 10, 11, 13, attendees, roomA, "Meeting 2");
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            fail("Should throw exception for overlapping meetings");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to overlap", e.getMessage().contains("Overlap with another item"));
        }
    }

    // Illegal: Try to add a meeting in December, expect an exception
    @Test
    public void testAddMeetingDecember() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(12, 1, 10, 11, attendees, roomA, "December Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for December");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to December rejection", e.getMessage().contains("Month does not exist"));
        }
    }

    // Normal: Add an all-day vacation and verify it’s marked as busy
    @Test
    public void testAddVacationValid() {
        try {
            Meeting vacation = new Meeting(4, 15, "Vacation");
            calendar.addMeeting(vacation);
            Boolean added = calendar.isBusy(4, 15, 0, 23);
            assertTrue("Vacation should be marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Edge: Add a 1-hour vacation and verify it’s marked as busy
    @Test
    public void testAddVacationOneHour() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting vacation = new Meeting(4, 15, 10, 11, attendees, null, "Short Vacation");
            calendar.addMeeting(vacation);
            Boolean added = calendar.isBusy(4, 15, 10, 11);
            assertTrue("Short vacation should be marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Illegal: Try to add a vacation that overlaps with a meeting, expect an exception
    @Test
    public void testAddVacationOverlap() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 15, 10, 12, attendees, roomA, "Meeting");
            Meeting vacation = new Meeting(4, 15, 11, 13, attendees, null, "Vacation");
            calendar.addMeeting(meeting);
            calendar.addMeeting(vacation);
            fail("Should throw exception for overlapping vacation");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to overlap", e.getMessage().contains("Overlap with another item"));
        }
    }

    // Edge: Add a vacation on an invalid date (November 31st), verify the day is busy
    @Test
    public void testAddVacationInvalidDate() {
        try {
            Meeting vacation = new Meeting(11, 31, "Invalid Vacation");
            calendar.addMeeting(vacation);
            Boolean busy = calendar.isBusy(11, 31, 0, 23);
            assertTrue("November 31st should be busy due to pre-filled 'Day does not exist' meeting", busy);
        } catch (TimeConflictException e) {
            fail("Should not throw exception since 'Day does not exist' is ignored in overlap check: " + e.getMessage());
        }
    }

    // Illegal: Try to add a vacation in the 13th month, expect an exception
    @Test
    public void testAddVacationThirteenthMonth() {
        try {
            Meeting vacation = new Meeting(13, 1, "13th Month Vacation");
            calendar.addMeeting(vacation);
            fail("Should throw exception for 13th month");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to invalid month", e.getMessage().contains("Month does not exist"));
        }
    }

    // Normal: Add a meeting, clear the schedule for that day, and verify the day is no longer busy
    @Test
    public void testClearSchedule() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 10, 10, 11, attendees, roomA, "Team Meeting");
            calendar.addMeeting(meeting);
            // Verify the meeting was added
            assertTrue("Meeting should be marked as busy before clearing", calendar.isBusy(4, 10, 10, 11));
            // Clear the schedule for that day
            calendar.clearSchedule(4, 10);
            // Verify the day is no longer busy
            assertFalse("Day should not be busy after clearing schedule", calendar.isBusy(4, 10, 10, 11));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Normal: Add two meetings in a month, print the agenda for the month, and verify both meetings are listed
    @Test
    public void testPrintAgendaForMonth() {
        try {
            // Clear the schedule for all days in April to remove pre-filled "Day does not exist" meetings
            for (int day = 1; day <= 31; day++) {
                calendar.clearSchedule(4, day);
            }
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting1 = new Meeting(4, 10, 10, 11, attendees, roomA, "Meeting 1");
            Meeting meeting2 = new Meeting(4, 15, 14, 15, attendees, roomA, "Meeting 2");
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            String agenda = calendar.printAgenda(4);
            assertTrue("Agenda should contain Meeting 1", agenda.contains("Meeting 1"));
            assertTrue("Agenda should contain Meeting 2", agenda.contains("Meeting 2"));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Normal: Add two meetings, retrieve and remove one, and verify the remaining meeting
    @Test
    public void testGetAndRemoveMeeting() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting1 = new Meeting(4, 10, 10, 11, attendees, roomA, "Meeting 1");
            Meeting meeting2 = new Meeting(4, 10, 12, 13, attendees, roomA, "Meeting 2");
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            // Retrieve the first meeting
            Meeting retrieved = calendar.getMeeting(4, 10, 0);
            assertTrue("Retrieved meeting should be Meeting 1", retrieved.getDescription().equals("Meeting 1"));
            // Remove the first meeting
            calendar.removeMeeting(4, 10, 0);
            // Verify the second meeting is now at index 0
            retrieved = calendar.getMeeting(4, 10, 0);
            assertTrue("Retrieved meeting should be Meeting 2 after removal", retrieved.getDescription().equals("Meeting 2"));
            // Verify the day is no longer busy for the removed meeting's time slot
            assertFalse("Time slot should not be busy after removal", calendar.isBusy(4, 10, 10, 11));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Illegal: Try to retrieve a meeting with an invalid index, expect an exception
    @Test
    public void testGetMeetingInvalidIndex() {
        try {
            calendar.getMeeting(4, 10, 999); // No meetings exist at this index
            fail("Should throw IndexOutOfBoundsException for invalid index");
        } catch (IndexOutOfBoundsException e) {
            assertTrue("Expected IndexOutOfBoundsException", true);
        }
    }

    // Illegal: Try to remove a meeting with an invalid index, expect an exception
    @Test
    public void testRemoveMeetingInvalidIndex() {
        try {
            calendar.removeMeeting(4, 10, 999); // No meetings exist at this index
            fail("Should throw IndexOutOfBoundsException for invalid index");
        } catch (IndexOutOfBoundsException e) {
            assertTrue("Expected IndexOutOfBoundsException", true);
        }
    }

    // Illegal: Try to add a meeting in the last hour of the day with same start and end time, expect an exception
    @Test
    public void testAddMeetingLastHourOfDay() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 10, 23, 23, attendees, roomA, "Last Hour Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for same start and end time");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to invalid time", e.getMessage().contains("Meeting starts before it ends"));
        }
    }

    // Edge: Add a meeting on the first day of a month (January 1st) and verify it’s busy
    @Test
    public void testAddMeetingFirstDayOfMonth() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(1, 1, 10, 11, attendees, roomA, "New Year Meeting");
            calendar.addMeeting(meeting);
            Boolean added = calendar.isBusy(1, 1, 10, 11);
            assertTrue("Meeting on January 1st should be marked as busy", added);
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // Illegal: Try to add a meeting with a negative start time, expect an exception
    @Test
    public void testAddMeetingNegativeTime() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(emp1);
            Meeting meeting = new Meeting(4, 10, -1, 11, attendees, roomA, "Negative Time Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for negative start time");
        } catch (TimeConflictException e) {
            assertTrue("Should fail due to illegal hour", e.getMessage().contains("Illegal hour"));
        }
    }
}
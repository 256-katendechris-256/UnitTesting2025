package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class RoomTest {
    private Room room;
    private Meeting validMeeting;
    private ArrayList<Person> attendees;

    @Before
    public void setUp() throws Exception {
        room = new Room("Conference Room 1");
        attendees = new ArrayList<>();
        attendees.add(new Person("John Doe"));
        validMeeting = new Meeting(6, 15, 10, 12, attendees, room, "Team Meeting");
    }

    // Constructor Tests
    @Test
    public void testDefaultConstructor() {
        Room defaultRoom = new Room();
        assertEquals("Default room ID should be empty", "", defaultRoom.getID());
    }

    @Test
    public void testParameterizedConstructor() {
        assertEquals("Room ID should match constructor parameter", 
                    "Conference Room 1", room.getID());
    }

    // Meeting Management Tests
    @Test
    public void testAddValidMeeting() throws TimeConflictException {
        room.addMeeting(validMeeting);
        Meeting retrieved = room.getMeeting(6, 15, 0);
        assertEquals("Added meeting should match retrieved meeting", 
                    validMeeting, retrieved);
    }

    @Test(expected = TimeConflictException.class)
    public void testAddConflictingMeeting() throws TimeConflictException {
        Meeting conflictingMeeting = new Meeting(6, 15, 11, 13, attendees, room, "Conflict Meeting");
        room.addMeeting(validMeeting);
        room.addMeeting(conflictingMeeting); // Should throw exception
    }

    @Test
    public void testRemoveMeeting() throws TimeConflictException {
        room.addMeeting(validMeeting);
        room.removeMeeting(6, 15, 0);
        try {
            room.getMeeting(6, 15, 0);
            fail("Should throw IndexOutOfBoundsException after removal");
        } catch (IndexOutOfBoundsException e) {
            // Expected behavior
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetInvalidMeetingIndex() {
        room.getMeeting(1, 1, 0); // No meetings added
    }

    @Test
    public void testPrintAgendaForDay() throws TimeConflictException {
        room.addMeeting(validMeeting);
        String agenda = room.printAgenda(6, 15);
        String expected = "Agenda for 6/15:\n" +
                         "6/15, 10 - 12,Conference Room 1: Team Meeting\nAttending: John Doe\n";
        assertEquals("Day agenda should match expected format", expected, agenda);
    }

    @Test
    public void testPrintEmptyAgendaForDay() {
        String agenda = room.printAgenda(6, 15);
        assertEquals("Empty day agenda should have proper header", 
                    "Agenda for 6/15:\n", agenda);
    }

    // Busy Status Tests
    @Test
    public void testIsBusyDuringMeeting() throws TimeConflictException {
        room.addMeeting(validMeeting);
        assertTrue("Room should be busy during meeting time", 
                 room.isBusy(6, 15, 10, 12));
    }

    @Test
    public void testIsNotBusyOutsideMeeting() throws TimeConflictException {
        room.addMeeting(validMeeting);
        assertFalse("Room should not be busy outside meeting time", 
                   room.isBusy(6, 15, 13, 14));
    }

    @Test(expected = TimeConflictException.class)
    public void testIsBusyInvalidTimeRange() throws TimeConflictException {
        room.isBusy(6, 15, 12, 10); // End before start
    }

    // Edge Cases - Modified to use valid months/days
    @Test
    public void testAddMeetingFirstDayOfYear() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 9, 10, attendees, room, "New Year Meeting");
        room.addMeeting(meeting);
        assertTrue("Meeting on Jan 1 should be added", 
                  room.isBusy(1, 1, 9, 10));
    }

    @Test
    public void testAddMeetingLastDayOfMonth() throws TimeConflictException {
        // Using June 30th (30-day month) instead of December 31st
        Meeting meeting = new Meeting(6, 30, 14, 15, attendees, room, "Month End Meeting");
        room.addMeeting(meeting);
        assertTrue("Meeting on last day of month should be added", 
                  room.isBusy(6, 30, 14, 15));
    }

    // Exception Handling Tests
    @Test(expected = NullPointerException.class)
    public void testAddNullMeeting() throws TimeConflictException {
        room.addMeeting(null);
    }

    @Test
    public void testAddMeetingWithTimeConflictMessage() throws TimeConflictException {
        try {
            room.addMeeting(validMeeting);
            room.addMeeting(new Meeting(6, 15, 11, 13, attendees, room, "Conflict"));
            fail("Should throw TimeConflictException");
        } catch (TimeConflictException e) {
            assertTrue("Error message should mention room ID",
                      e.getMessage().contains("Conference Room 1"));
            assertTrue("Error message should show conflict details",
                      e.getMessage().contains("Conflict"));
        }
    }
}
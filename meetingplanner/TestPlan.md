Overall Test Plan for Meeting Planner Exercise


Informal Test Plan for MeetingTest.java
=======================================

The following tests verify the functionality of the Meeting class in the meeting planner application. Each test is categorized as Normal, Edge, or Illegal, as the Meeting class does not perform input validation (validation is handled by Calendar).

1. testAllDayMeetingCreation (Normal)
   - Purpose: Create an all-day meeting and verify its fields (month, day, start, end, description, room, attendees).
   - Expected Outcome: Fields should match the provided values; start=0, end=23, room and attendees should be null.

2. testTimedMeetingCreation (Normal)
   - Purpose: Create a timed meeting with room and attendees, and verify its fields.
   - Expected Outcome: Fields should match the provided values, including room and attendees.

3. testToStringTimedMeeting (Normal)
   - Purpose: Verify the toString method for a timed meeting with room and attendees.
   - Expected Outcome: String should be "4/10, 10 - 11,Room A: Timed Meeting\nAttending: Employee 1".

4. testMeetingMinimalDuration (Edge)
   - Purpose: Create a meeting with the minimal valid duration (00:00–01:00) and verify its fields and toString.
   - Expected Outcome: Start=0, end=1, toString should match the expected format.

5. testMeetingFullDayTimed (Edge)
   - Purpose: Create a timed meeting spanning the full day (00:00–23:00) and verify its fields and toString.
   - Expected Outcome: Start=0, end=23, toString should match the expected format.

6. testMeetingEmptyAttendees (Edge)
   - Purpose: Create a meeting with an empty attendee list and verify its fields and toString.
   - Expected Outcome: Attendees should be an empty list, toString should include "Attending:" with no names.

7. testMeetingNullRoom (Edge)
   - Purpose: Create a meeting with a null room and verify its fields.
   - Expected Outcome: Room should be null (toString not tested due to NullPointerException).

8. testMeetingNullAttendees (Edge)
   - Purpose: Create a meeting with null attendees and verify its fields.
   - Expected Outcome: Attendees should be null (toString not tested due to NullPointerException).

9. testMeetingFirstDayOfMonth (Edge)
   - Purpose: Create a meeting on the first day of a month (January 1st) and verify its fields.
   - Expected Outcome: Month=1, day=1.

10. testMeetingLastDayOfMonth (Edge)
    - Purpose: Create a meeting on the last day of a 31-day month (March 31st) and verify its fields.
    - Expected Outcome: Month=3, day=31.

11. testMeetingLastDayOf30DayMonth (Edge)
    - Purpose: Create a meeting on the last day of a 30-day month (April 30th) and verify its fields.
    - Expected Outcome: Month=4, day=30.

12. testMeetingLeapYear (Edge)
    - Purpose: Create a meeting on February 29th (leap year scenario, but no validation in Meeting) and verify its fields.
    - Expected Outcome: Month=2, day=29.

13. testMeetingNonLeapYear (Edge)
    - Purpose: Create a meeting on February 28th (non-leap year scenario, but no validation in Meeting) and verify its fields.
    - Expected Outcome: Month=2, day=28.

14. testMeetingInvalidTime (Edge)
    - Purpose: Create a meeting with start time greater than end time (Meeting allows this, validation happens in Calendar).
    - Expected Outcome: Start=11, end=10.

15. testMeetingNegativeStartTime (Edge)
    - Purpose: Create a meeting with a negative start time (Meeting allows this, validation happens in Calendar).
    - Expected Outcome: Start=-1, end=10.

16. testMeetingStartTimeGreaterThan23 (Edge)
    - Purpose: Create a meeting with a start time greater than 23 (Meeting allows this, validation happens in Calendar).
    - Expected Outcome: Start=24, end=25.

17. testMeetingEndTimeLessThanStartTime (Edge)
    - Purpose: Create a meeting with an end time less than start time (Meeting allows this, validation happens in Calendar).
    - Expected Outcome: Start=10, end=9.

18. testMeetingStartTimeEqualToEndTime (Edge)
    - Purpose: Create a meeting with a start time equal to end time (Meeting allows this, validation happens in Calendar).
    - Expected Outcome: Start=10, end=10.

19. testMeetingStartTimeEqualToZero (Edge)
    - Purpose: Create a meeting with a start time equal to 0 (valid case) and verify its fields and toString.
    - Expected Outcome: Start=0, end=1, toString should match the expected format.

20. testMeetingStartTimeEqualTo23 (Edge)
    - Purpose: Create a meeting with a start time equal to 23 (Meeting allows this, but end time 24 is invalid in Calendar).
    - Expected Outcome: Start=23, end=24, toString should match the expected format.

21. testAddAttendee (Normal)
    - Purpose: Test adding an attendee to a meeting with an initially empty attendee list.
    - Expected Outcome: Attendees list should have 1 person, toString should include the new attendee.

22. testRemoveAttendee (Normal)
    - Purpose: Test removing an attendee from a meeting.
    - Expected Outcome: Attendees list should be empty after removal, toString should reflect no attendees.

23. testAddAttendeeWithNullAttendees (Illegal)
    - Purpose: Test adding an attendee to a meeting with a null attendees list.
    - Expected Outcome: Should throw a NullPointerException.

24. testRemoveAttendeeWithNullAttendees (Illegal)
    - Purpose: Test removing an attendee from a meeting with a null attendees list.
    - Expected Outcome: Should throw a NullPointerException.

Summary:
- Total Tests: 24
- Normal Tests: 5
- Edge Tests: 17
- Illegal Tests: 2 (due to NullPointerExceptions when handling null attendees).


Informal Test Plan for CalendarTest.java
=======================================

The following tests verify the functionality of the Calendar class in the meeting planner application. Each test is categorized as Normal, Edge, or Illegal, and addresses specific requirements or class faults (13th month, December rejection, same start/end time, invalid dates).

1. testAddMeeting_holiday (Normal)
   - Purpose: Add an all-day holiday meeting (Janan Luwum Day) and verify it’s marked as busy.
   - Expected Outcome: The day (February 16th) should be busy from 00:00 to 23:00.

2. testAddMeetingWithRoomAndPerson (Normal)
   - Purpose: Add a meeting with a room and person, verify it’s busy, and check the agenda.
   - Expected Outcome: The meeting should be marked as busy, and the agenda should include the meeting description.

3. testAddMultipleNonOverlappingMeetings (Normal)
   - Purpose: Add two non-overlapping meetings on the same day and verify both are busy.
   - Expected Outcome: Both time slots should be marked as busy.

4. testAddMeetingAtMidnight (Edge)
   - Purpose: Add a meeting at midnight (00:00–01:00) and verify it’s busy.
   - Expected Outcome: The time slot should be marked as busy.

5. testAddMeetingLastDayNovember (Edge)
   - Purpose: Add a meeting on the last valid day of November (29th) and verify it’s busy.
   - Expected Outcome: The time slot should be marked as busy.

6. testAddMeetingInvalidDate (Illegal)
   - Purpose: Try to add a meeting on an invalid date (February 35th), expect an exception.
   - Expected Outcome: A TimeConflictException should be thrown with "Day does not exist".

7. testAddMeetingThirteenthMonth (Illegal)
   - Purpose: Try to add a meeting in the 13th month, expect an exception.
   - Expected Outcome: A TimeConflictException should be thrown with "Month does not exist".

8. testAddMeetingSameTime (Illegal)
   - Purpose: Try to add a meeting with the same start and end time, expect an exception.
   - Expected Outcome: A TimeConflictException should be thrown with "Meeting starts before it ends".

9. testAddMeetingOverlap (Illegal)
   - Purpose: Try to add overlapping meetings, expect an exception.
   - Expected Outcome: A TimeConflictException should be thrown with "Overlap with another item".

10. testAddMeetingDecember (Illegal)
    - Purpose: Try to add a meeting in December, expect an exception.
    - Expected Outcome: A TimeConflictException should be thrown with "Month does not exist".

11. testAddVacationValid (Normal)
    - Purpose: Add an all-day vacation and verify it’s marked as busy.
    - Expected Outcome: The day should be busy from 00:00 to 23:00.

12. testAddVacationOneHour (Edge)
    - Purpose: Add a 1-hour vacation and verify it’s marked as busy.
    - Expected Outcome: The time slot should be marked as busy.

13. testAddVacationOverlap (Illegal)
    - Purpose: Try to add a vacation that overlaps with a meeting, expect an exception.
    - Expected Outcome: A TimeConflictException should be thrown with "Overlap with another item".

14. testAddVacationInvalidDate (Edge)
    - Purpose: Add a vacation on an invalid date (November 31st), verify the day is busy.
    - Expected Outcome: The day should be busy due to the pre-filled "Day does not exist" meeting.

15. testAddVacationThirteenthMonth (Illegal)
    - Purpose: Try to add a vacation in the 13th month, expect an exception.
    - Expected Outcome: A TimeConflictException should be thrown with "Month does not exist".

16. testClearSchedule (Normal)
    - Purpose: Add a meeting, clear the schedule for that day, and verify the day is no longer busy.
    - Expected Outcome: The day should not be busy after clearing the schedule.

17. testPrintAgendaForMonth (Normal)
    - Purpose: Add two meetings in a month, print the agenda for the month, and verify both meetings are listed.
    - Expected Outcome: The agenda should include both meeting descriptions.

18. testGetAndRemoveMeeting (Normal)
    - Purpose: Add two meetings, retrieve and remove one, and verify the remaining meeting.
    - Expected Outcome: The first meeting should be retrieved and removed, the second meeting should be at index 0, and the removed time slot should not be busy.

19. testGetMeetingInvalidIndex (Illegal)
    - Purpose: Try to retrieve a meeting with an invalid index, expect an exception.
    - Expected Outcome: An IndexOutOfBoundsException should be thrown.

20. testRemoveMeetingInvalidIndex (Illegal)
    - Purpose: Try to remove a meeting with an invalid index, expect an exception.
    - Expected Outcome: An IndexOutOfBoundsException should be thrown.

21. testAddMeetingLastHourOfDay (Illegal)
    - Purpose: Try to add a meeting in the last hour of the day with same start and end time, expect an exception.
    - Expected Outcome: A TimeConflictException should be thrown with "Meeting starts before it ends".

22. testAddMeetingFirstDayOfMonth (Edge)
    - Purpose: Add a meeting on the first day of a month (January 1st) and verify it’s busy.
    - Expected Outcome: The time slot should be marked as busy.

23. testAddMeetingNegativeTime (Illegal)
    - Purpose: Try to add a meeting with a negative start time, expect an exception.
    - Expected Outcome: A TimeConflictException should be thrown with "Illegal hour".

24. testAddMeetingLastDayMarch (Edge)
    - Purpose: Add a meeting on the last day of a 31-day month (March 31st) and verify it’s busy.
    - Expected Outcome: The time slot should be marked as busy.

25. testAddFullDayMeeting (Edge)
    - Purpose: Add a full-day meeting (00:00–23:00) using the timed constructor and verify it’s busy.
    - Expected Outcome: The day should be busy from 00:00 to 23:00.

26. testAddMeetingNegativeMonth (Illegal)
    - Purpose: Try to add a meeting with a negative month, expect an exception.
    - Expected Outcome: A TimeConflictException should be thrown with "Month does not exist".

27. testIsBusyNegativeMonth (Illegal)
    - Purpose: Try to check isBusy with a negative month, expect an exception.
    - Expected Outcome: A TimeConflictException or IndexOutOfBoundsException should be thrown.

Summary:
- Total Tests: 27
- Normal Tests: 7
- Edge Tests: 8
- Illegal Tests: 12


Informal Test Plan for PersonTest.java
=======================================

The following tests verify the functionality of the Person class in the meeting planner application. Each test is categorized as Normal, Edge, or Illegal.

1. testDefaultConstructor (Normal)
   - Purpose: Test creating a person with the default constructor.
   - Expected Outcome: Name should be an empty string.

2. testConstructorWithName (Normal)
   - Purpose: Test creating a person with a specified name.
   - Expected Outcome: Name should match the provided value ("Employee 2").

3. testAddMeeting (Normal)
   - Purpose: Test adding a meeting to the person's calendar.
   - Expected Outcome: Meeting should be added and retrievable via getMeeting, with matching fields.

4. testGetMeeting (Normal)
   - Purpose: Test retrieving a meeting from the person's calendar.
   - Expected Outcome: Should retrieve the correct meeting with matching fields.

5. testRemoveMeeting (Normal)
   - Purpose: Test removing a meeting from the person's calendar.
   - Expected Outcome: Should throw IndexOutOfBoundsException when retrieving the meeting after removal.

6. testPrintAgendaForMonth (Normal)
   - Purpose: Test printing the agenda for a month with meetings.
   - Expected Outcome: Should throw NullPointerException due to a bug in Calendar.printAgenda().

7. testPrintAgendaForDay (Normal)
   - Purpose: Test printing the agenda for a specific day with meetings.
   - Expected Outcome: Agenda should include all meetings for the day.

8. testIsBusy (Normal)
   - Purpose: Test checking if the person is busy during a specific time.
   - Expected Outcome: Should return true during a meeting, false outside the meeting time (e.g., 12:00-13:00).

9. testAddMeetingFirstDayOfMonth (Edge)
   - Purpose: Test adding a meeting on the first day of a month (January 1st).
   - Expected Outcome: Meeting should be added successfully.

10. testAddMeetingLastDayOfMonth (Edge)
    - Purpose: Test adding a meeting on the last day of a 31-day month (March 31st).
    - Expected Outcome: Meeting should be added successfully.

11. testAddMeetingLastDayOf30DayMonth (Edge)
    - Purpose: Test adding a meeting on the last day of a 30-day month (April 30th).
    - Expected Outcome: Meeting should be added successfully.

12. testAddMeetingMinimalDuration (Edge)
    - Purpose: Test adding a meeting with the minimal duration (00:00-01:00).
    - Expected Outcome: Meeting should be added successfully.

13. testAddMeetingFullDay (Edge)
    - Purpose: Test adding a meeting spanning the full day (00:00-23:00).
    - Expected Outcome: Meeting should be added successfully.

14. testPrintEmptyAgendaForMonth (Edge)
    - Purpose: Test printing the agenda for a month with no meetings.
    - Expected Outcome: Should throw NullPointerException due to a bug in Calendar.printAgenda().

15. testPrintEmptyAgendaForDay (Edge)
    - Purpose: Test printing the agenda for a day with no meetings.
    - Expected Outcome: Agenda should be empty ("Agenda for 4/10:\n").

16. testIsBusyNoMeetings (Edge)
    - Purpose: Test isBusy when no meetings are scheduled.
    - Expected Outcome: Should return false.

17. testGetMeetingInvalidIndex (Edge)
    - Purpose: Test retrieving a meeting with an invalid index.
    - Expected Outcome: Should throw an IndexOutOfBoundsException.

18. testAddMeetingTimeConflict (Illegal)
    - Purpose: Test adding a meeting that conflicts with an existing meeting.
    - Expected Outcome: Should throw a TimeConflictException with a message including the person's name.

19. testAddMeetingInvalidMonth (Illegal)
    - Purpose: Test adding a meeting with an invalid month (month 12).
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid month.

20. testAddMeetingInvalidDay (Illegal)
    - Purpose: Test adding a meeting with an invalid day (April 31st).
    - Expected Outcome: Meeting is added successfully due to bugs in Calendar.addMeeting(), which:
      1. Does not validate the day, allowing an invalid date (April 31st).
      2. Resets the start and end times to 0 and 23, respectively.
      3. Changes the meeting description to "Day does not exist."

21. testAddMeetingNegativeStartTime (Illegal)
    - Purpose: Test adding a meeting with a negative start time.
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid time.

22. testAddMeetingStartTimeGreaterThan23 (Illegal)
    - Purpose: Test adding a meeting with a start time greater than 23.
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid time.

23. testAddMeetingStartTimeEqualToEndTime (Illegal)
    - Purpose: Test adding a meeting where start time equals end time.
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid time.

Summary:
- Total Tests: 23
- Normal Tests: 8
- Edge Tests: 9
- Illegal Tests: 6


Informal Test Plan for RoomTest.java
=======================================

The following tests verify the functionality of the Room class in the meeting planner application. Each test is categorized as Normal, Edge, or Illegal.

1. testDefaultConstructor (Normal)
   - Purpose: Test creating a room with the default constructor.
   - Expected Outcome: ID should be an empty string.

2. testConstructorWithID (Normal)
   - Purpose: Test creating a room with a specified ID.
   - Expected Outcome: ID should match the provided value ("Room B").

3. testAddMeeting (Normal)
   - Purpose: Test adding a meeting to the room.
   - Expected Outcome: Meeting should be added and retrievable via getMeeting, with matching fields.

4. testGetMeeting (Normal)
   - Purpose: Test retrieving a meeting from the room.
   - Expected Outcome: Should retrieve the correct meeting with matching fields.

5. testRemoveMeeting (Normal)
   - Purpose: Test removing a meeting from the room.
   - Expected Outcome: Should throw IndexOutOfBoundsException when retrieving the meeting after removal.

6. testPrintAgendaForMonth (Normal)
   - Purpose: Test printing the agenda for a month with meetings.
   - Expected Outcome: Should throw NullPointerException due to a bug in Calendar.printAgenda().

7. testPrintAgendaForDay (Normal)
   - Purpose: Test printing the agenda for a specific day with meetings.
   - Expected Outcome: Agenda should include all meetings for the day.

8. testIsBusy (Normal)
   - Purpose: Test checking if the room is busy during a specific time.
   - Expected Outcome: Should return true during a meeting, false outside the meeting time (e.g., 12:00-13:00).

9. testAddMeetingFirstDayOfMonth (Edge)
   - Purpose: Test adding a meeting on the first day of a month (January 1st).
   - Expected Outcome: Meeting should be added successfully.

10. testAddMeetingLastDayOfMonth (Edge)
    - Purpose: Test adding a meeting on the last day of a 31-day month (March 31st).
    - Expected Outcome: Meeting should be added successfully.

11. testAddMeetingLastDayOf30DayMonth (Edge)
    - Purpose: Test adding a meeting on the last day of a 30-day month (April 30th).
    - Expected Outcome: Meeting should be added successfully.

12. testAddMeetingMinimalDuration (Edge)
    - Purpose: Test adding a meeting with the minimal duration (00:00-01:00).
    - Expected Outcome: Meeting should be added successfully.

13. testAddMeetingFullDay (Edge)
    - Purpose: Test adding a meeting spanning the full day (00:00-23:00).
    - Expected Outcome: Meeting should be added successfully.

14. testPrintEmptyAgendaForMonth (Edge)
    - Purpose: Test printing the agenda for a month with no meetings.
    - Expected Outcome: Should throw NullPointerException due to a bug in Calendar.printAgenda().

15. testPrintEmptyAgendaForDay (Edge)
    - Purpose: Test printing the agenda for a day with no meetings.
    - Expected Outcome: Agenda should be empty ("Agenda for 4/10:\n").

16. testIsBusyNoMeetings (Edge)
    - Purpose: Test isBusy when no meetings are scheduled.
    - Expected Outcome: Should return false.

17. testGetMeetingInvalidIndex (Edge)
    - Purpose: Test retrieving a meeting with an invalid index.
    - Expected Outcome: Should throw an IndexOutOfBoundsException.

18. testAddMeetingTimeConflict (Illegal)
    - Purpose: Test adding a meeting that conflicts with an existing meeting.
    - Expected Outcome: Should throw a TimeConflictException with a message including the room ID.

19. testAddMeetingInvalidMonth (Illegal)
    - Purpose: Test adding a meeting with an invalid month (month 12).
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid month.

20. testAddMeetingInvalidDay (Illegal)
    - Purpose: Test adding a meeting with an invalid day (April 31st).
    - Expected Outcome: Meeting is added successfully due to bugs in Calendar.addMeeting(), which:
      1. Does not validate the day, allowing an invalid date (April 31st).
      2. Resets the start and end times to 0 and 23, respectively.
      3. Changes the meeting description to "Day does not exist."

21. testAddMeetingNegativeStartTime (Illegal)
    - Purpose: Test adding a meeting with a negative start time.
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid time.

22. testAddMeetingStartTimeGreaterThan23 (Illegal)
    - Purpose: Test adding a meeting with a start time greater than 23.
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid time.

23. testAddMeetingStartTimeEqualToEndTime (Illegal)
    - Purpose: Test adding a meeting where start time equals end time.
    - Expected Outcome: Should throw a TimeConflictException with a message about invalid time.

Summary:
- Total Tests: 23
- Normal Tests: 8
- Edge Tests: 9
- Illegal Tests: 6



Informal Test Plan for OrganizationTest.java
=======================================

The following tests verify the functionality of the Organization class in the meeting planner application. Each test is categorized as Normal, Edge, or Illegal.

1. testDefaultConstructor (Normal)
   - Purpose: Test the default constructor for correct initialization of employees and rooms.
   - Expected Outcome: Should initialize 5 employees and 5 rooms with the correct names and IDs.

2. testGetEmployees (Normal)
   - Purpose: Test the getEmployees method.
   - Expected Outcome: Should return a list of 5 employees with the correct names.

3. testGetRooms (Normal)
   - Purpose: Test the getRooms method.
   - Expected Outcome: Should return a list of 5 rooms with the correct IDs.

4. testGetRoomValid (Normal)
   - Purpose: Test retrieving a room with a valid room ID (LLT3A).
   - Expected Outcome: Should return the room with ID "LLT3A".

5. testGetEmployeeValid (Normal)
   - Purpose: Test retrieving an employee with a valid name (Acan Brenda).
   - Expected Outcome: Should return the employee with name "Acan Brenda".

6. testGetRoomFirst (Edge)
   - Purpose: Test retrieving the first room in the list (LLT6A).
   - Expected Outcome: Should return the room with ID "LLT6A".

7. testGetRoomLast (Edge)
   - Purpose: Test retrieving the last room in the list (LAB2).
   - Expected Outcome: Should return the room with ID "LAB2".

8. testGetEmployeeFirst (Edge)
   - Purpose: Test retrieving the first employee in the list (Namugga Martha).
   - Expected Outcome: Should return the employee with name "Namugga Martha".

9. testGetEmployeeLast (Edge)
   - Purpose: Test retrieving the last employee in the list (Kukunda Lynn).
   - Expected Outcome: Should return the employee with name "Kukunda Lynn".

10. testGetRoomInvalid (Illegal)
    - Purpose: Test retrieving a room with an invalid room ID.
    - Expected Outcome: Should throw an Exception with message "Requested room does not exist".

11. testGetEmployeeInvalid (Illegal)
    - Purpose: Test retrieving an employee with an invalid name.
    - Expected Outcome: Should throw an Exception with message "Requested employee does not exist".

12. testGetRoomEmptyID (Illegal)
    - Purpose: Test retrieving a room with an empty string as the ID.
    - Expected Outcome: Should throw an Exception with message "Requested room does not exist".

13. testGetEmployeeEmptyName (Illegal)
    - Purpose: Test retrieving an employee with an empty string as the name.
    - Expected Outcome: Should throw an Exception with message "Requested employee does not exist".

14. testGetRoomNullID (Illegal)
    - Purpose: Test retrieving a room with a null ID.
    - Expected Outcome: Should throw an Exception with message "Requested room does not exist".

15. testGetEmployeeNullName (Illegal)
    - Purpose: Test retrieving an employee with a null name.
    - Expected Outcome: Should throw an Exception with message "Requested employee does not exist".

Summary:
- Total Tests: 15
- Normal Tests: 5
- Edge Tests: 4
- Illegal Tests: 6





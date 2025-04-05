package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class OrganizationTest {
    private Organization org;

    @Before
    public void setUp() {
        org = new Organization();
    }

    // Normal: Test the default constructor for correct initialization
    @Test
    public void testDefaultConstructor() {
        ArrayList<Person> employees = org.getEmployees();
        ArrayList<Room> rooms = org.getRooms();

        // Check the number of employees and rooms
        assertEquals("Should have 5 employees", 5, employees.size());
        assertEquals("Should have 5 rooms", 5, rooms.size());

        // Check employee names
        assertEquals("First employee should be Namugga Martha", "Namugga Martha", employees.get(0).getName());
        assertEquals("Second employee should be Shema Collins", "Shema Collins", employees.get(1).getName());
        assertEquals("Third employee should be Acan Brenda", "Acan Brenda", employees.get(2).getName());
        assertEquals("Fourth employee should be Kazibwe Julius", "Kazibwe Julius", employees.get(3).getName());
        assertEquals("Fifth employee should be Kukunda Lynn", "Kukunda Lynn", employees.get(4).getName());

        // Check room IDs
        assertEquals("First room should be LLT6A", "LLT6A", rooms.get(0).getID());
        assertEquals("Second room should be LLT6B", "LLT6B", rooms.get(1).getID());
        assertEquals("Third room should be LLT3A", "LLT3A", rooms.get(2).getID());
        assertEquals("Fourth room should be LLT2C", "LLT2C", rooms.get(3).getID());
        assertEquals("Fifth room should be LAB2", "LAB2", rooms.get(4).getID());
    }

    // Normal: Test getEmployees method
    @Test
    public void testGetEmployees() {
        ArrayList<Person> employees = org.getEmployees();
        assertEquals("Should return 5 employees", 5, employees.size());
        assertEquals("First employee should be Namugga Martha", "Namugga Martha", employees.get(0).getName());
        assertEquals("Last employee should be Kukunda Lynn", "Kukunda Lynn", employees.get(4).getName());
    }

    // Normal: Test getRooms method
    @Test
    public void testGetRooms() {
        ArrayList<Room> rooms = org.getRooms();
        assertEquals("Should return 5 rooms", 5, rooms.size());
        assertEquals("First room should be LLT6A", "LLT6A", rooms.get(0).getID());
        assertEquals("Last room should be LAB2", "LAB2", rooms.get(4).getID());
    }

    // Normal: Test getRoom with a valid room ID
    @Test
    public void testGetRoomValid() {
        try {
            Room room = org.getRoom("LLT3A");
            assertEquals("Room ID should be LLT3A", "LLT3A", room.getID());
        } catch (Exception e) {
            fail("Should not throw an exception for a valid room ID: " + e.getMessage());
        }
    }

    // Normal: Test getEmployee with a valid employee name
    @Test
    public void testGetEmployeeValid() {
        try {
            Person employee = org.getEmployee("Acan Brenda");
            assertEquals("Employee name should be Acan Brenda", "Acan Brenda", employee.getName());
        } catch (Exception e) {
            fail("Should not throw an exception for a valid employee name: " + e.getMessage());
        }
    }

    // Edge: Test getRoom with the first room ID (LLT6A)
    @Test
    public void testGetRoomFirst() {
        try {
            Room room = org.getRoom("LLT6A");
            assertEquals("Room ID should be LLT6A", "LLT6A", room.getID());
        } catch (Exception e) {
            fail("Should not throw an exception for the first room ID: " + e.getMessage());
        }
    }

    // Edge: Test getRoom with the last room ID (LAB2)
    @Test
    public void testGetRoomLast() {
        try {
            Room room = org.getRoom("LAB2");
            assertEquals("Room ID should be LAB2", "LAB2", room.getID());
        } catch (Exception e) {
            fail("Should not throw an exception for the last room ID: " + e.getMessage());
        }
    }

    // Edge: Test getEmployee with the first employee name (Namugga Martha)
    @Test
    public void testGetEmployeeFirst() {
        try {
            Person employee = org.getEmployee("Namugga Martha");
            assertEquals("Employee name should be Namugga Martha", "Namugga Martha", employee.getName());
        } catch (Exception e) {
            fail("Should not throw an exception for the first employee name: " + e.getMessage());
        }
    }

    // Edge: Test getEmployee with the last employee name (Kukunda Lynn)
    @Test
    public void testGetEmployeeLast() {
        try {
            Person employee = org.getEmployee("Kukunda Lynn");
            assertEquals("Employee name should be Kukunda Lynn", "Kukunda Lynn", employee.getName());
        } catch (Exception e) {
            fail("Should not throw an exception for the last employee name: " + e.getMessage());
        }
    }

    // Illegal: Test getRoom with an invalid room ID
    @Test
    public void testGetRoomInvalid() {
        try {
            org.getRoom("INVALID_ROOM");
            fail("Should throw an exception for an invalid room ID");
        } catch (Exception e) {
            assertTrue("Expected exception for invalid room ID", e.getMessage().contains("Requested room does not exist"));
        }
    }

    // Illegal: Test getEmployee with an invalid employee name
    @Test
    public void testGetEmployeeInvalid() {
        try {
            org.getEmployee("Invalid Employee");
            fail("Should throw an exception for an invalid employee name");
        } catch (Exception e) {
            assertTrue("Expected exception for invalid employee name", e.getMessage().contains("Requested employee does not exist"));
        }
    }

    // Illegal: Test getRoom with an empty string as the ID
    @Test
    public void testGetRoomEmptyID() {
        try {
            org.getRoom("");
            fail("Should throw an exception for an empty room ID");
        } catch (Exception e) {
            assertTrue("Expected exception for empty room ID", e.getMessage().contains("Requested room does not exist"));
        }
    }

    // Illegal: Test getEmployee with an empty string as the name
    @Test
    public void testGetEmployeeEmptyName() {
        try {
            org.getEmployee("");
            fail("Should throw an exception for an empty employee name");
        } catch (Exception e) {
            assertTrue("Expected exception for empty employee name", e.getMessage().contains("Requested employee does not exist"));
        }
    }

    // Illegal: Test getRoom with a null ID
    @Test
    public void testGetRoomNullID() {
        try {
            org.getRoom(null);
            fail("Should throw an Exception for a null room ID");
        } catch (Exception e) {
            assertTrue("Expected Exception for null room ID", e.getMessage().contains("Requested room does not exist"));
        }
    }

    // Illegal: Test getEmployee with a null name
    @Test
    public void testGetEmployeeNullName() {
        try {
            org.getEmployee(null);
            fail("Should throw an Exception for a null employee name");
        } catch (Exception e) {
            assertTrue("Expected Exception for null employee name", e.getMessage().contains("Requested employee does not exist"));
        }
    }
}
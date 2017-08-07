package model;

import com.lali576.cinema.maven.model.Room;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoomTest {
    private static Room room;
    
    @BeforeClass
    public static void setUpClass() {
        room = new Room(1, "Andy Vajna", 10, 6);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCheckRoomName() {
        assertTrue("Fail! Room roomName is incorrect!", !(room.getRoomName().trim().equals("")));
    }

    @Test
    public void testCheckRoomRow() {
        assertTrue("Fail! Room roomRow is incorrect!", (room.getRoomRows() >= 1 && room.getRoomRows() <= 10));
    }

    @Test
    public void testCheckRoomColumn() {
        assertTrue("Fail! Room roomColumn is incorrect!", (room.getRoomColumns() >= 1 && room.getRoomColumns() <= 20));
    }    
}
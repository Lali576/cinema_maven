package model;

import com.lali576.cinema.maven.model.Seat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeatTest {
    private static Seat seat;
    
    @BeforeClass
    public static void setUpClass() {
        seat = new Seat(1, 1, 1);
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
    public void testCheckRowNumber() {
        assertTrue("Fail! Seat rowNumber is incorrect!", (seat.getRowNumber() >= 1 && seat.getRowNumber() <= 10));
    }
    
    @Test
    public void testCheckColumnNumber() {
        assertTrue("Fail! Seat columnNumber is incorrect!", (seat.getColumnNumber() >= 1 && seat.getColumnNumber() <= 20));
    }
}

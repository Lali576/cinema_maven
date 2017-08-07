package model;

import com.lali576.cinema.maven.model.Movie;
import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Show;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShowTest {
    private static Show show;
    
    @BeforeClass
    public static void setUpClass() {
        show = new Show(1, "10:00");
        show.setMovie(new Movie(1,
                "Ryan kozlegeny megmentese",
                "USA",
                true,
                "Steven Spielberg",
                "War is everywhere",
                120));
        show.setRoom(new Room(1, "Andy Vajna", 10, 6));
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
    public void testShowMovie() {
        assertTrue("Fail! Show movie is incorrect!", (show.getMovie() != null));
    }
    
    @Test
    public void testShowRoom() {
        assertTrue("Fail! Show room is incorrect!", (show.getRoom() != null));
    }
       
    @Test
    public void testChechStartTime() {
        assertTrue("Fail! Show startTime is incorrect!", (Pattern.compile("[0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}").matcher(show.getStartTime()).find()));
    }
}

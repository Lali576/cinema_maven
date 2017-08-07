package model;

import com.lali576.cinema.maven.model.Movie;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MovieTest {   
    private static Movie movie;
    
    @BeforeClass
    public static void setUpClass() {
        movie = new Movie(
                1,
                "Ryan kozlegeny megmentese",
                "USA",
                true,
                "Steven Spielberg",
                "War is everywhere",
                120             
        );
        movie.setAge(4);
        movie.setSoldTickets(50);
        movie.setMaxPlay(7);
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
    public void testCheckTitle() {
        assertTrue("Fail! Movie title is incorrect!", !movie.getTitle().trim().equals(""));    
    }
    
    @Test
    public void testCheckCountry() {
        assertTrue("Test movie country", !movie.getCountry().trim().equals(""));
    }
    
    @Test
    public void testCheckDirector() {
        assertTrue("Fail! Movie director is incorrect!", !movie.getDirector().trim().equals(""));
    }
    
    @Test
    public void testCheckSynopsis() {
        assertTrue("Fail! Movie synopsis is incorrect!", !movie.getSynopsis().trim().equals(""));
    }
    
    @Test
    public void testCheckLength() {
        assertTrue("Fail! Movie length is incorrect!", (movie.getLength() >= 60 && movie.getLength() <= 180));
    }
    
    @Test
    public void testCheckAge() {
        assertTrue("Fail! Movie age is incorrect!", (movie.getAge() >= 1 && movie.getAge() <= 5));
    }
    
    @Test
    public void testCheckSoldTickets() {
        assertTrue("Fail! Movie soldTicket is incorrect!", (movie.getSoldTickets() >= 0));
    }
    
    @Test
    public void testCheckMaxPlay() {
        assertTrue("Fail! Movie maxPlay is incorrect!", (movie.getMaxPlay() >= 5 && movie.getMaxPlay() <= 15));
    }
}

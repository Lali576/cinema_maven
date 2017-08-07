package model;

import com.lali576.cinema.maven.model.Register;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegisterTest {
    private static Register register;
    
    @BeforeClass
    public static void setUpClass() {
        register = new Register("lali576", true);
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
    public void testCheckUserName() {
        assertTrue("Fail! Register username is incorrect", !(register.getName().trim().equals("")));
    }
}

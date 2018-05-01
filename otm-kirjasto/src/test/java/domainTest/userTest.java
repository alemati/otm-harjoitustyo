package domainTest;

import dao.UserDao;
import domain.Book;
import domain.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class userTest {

    private static UserDao userDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void equalWhenSame() {
        User u1 = new User("aleKus", "uu8fJ1");
        User u2 = new User("aleKus", "uu8fJ1");
        assertTrue(u1.equals(u2));
    }

    @Test
    public void nonEqualWhenNotSame() { 
        User u1 = new User("aleKus", "uu8fJ1");
        User u2 = new User("allu", "uu8fJ1");
        assertFalse(u1.equals(u2));
    }

    @Test
    public void nonEqualsWhenDifferentTypes() { 

        User u1 = new User("jj", "lsdd3");
        String st = "w";
        assertFalse(u1.equals(new Book(0, st, st, 0)));

    }

    @Test
    public void toStringOK() {
        User u1 = new User("allu", "1111");
        assertEquals(u1.toString(), "allu, 1111");
    }

}

package domainTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.BookDao;
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

/**
 *
 * @author AM
 */
public class bookTest {

    @Before
    public void setUp() throws Exception {

    }


    @After
    public void tearDown() {

    }

    @Test
    public void equalsWhenSame() {
        Book b1 = new Book(1, "1", "1", 1);
        Book b2 = new Book(1, "1", "1", 1);
        assertTrue(b1.equals(b2));
    }

    @Test
    public void nonEqualsWhenNotSame() {
        Book b1 = new Book(1, "1", "1", 1);
        Book b2 = new Book(2, "2", "2", 2);
        assertFalse(b1.equals(b2));

    }

    @Test
    public void nonEqualsWhenDifferentTypes() {
        Book b1 = new Book(1, "1", "1", 1);
        assertFalse(b1.equals(new User("e", "d")));
    }

    @Test
    public void toStringOK() {
        Book b1 = new Book(1, "1", "1", 1);
        assertEquals("1, 1, 1", b1.toString());
    }
 
}

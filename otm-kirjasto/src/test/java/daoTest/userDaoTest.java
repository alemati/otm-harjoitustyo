package daoTest;


import domainTest.userTest;
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

public class userDaoTest {

    private static UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDao("testdatabase.db");
        userDao.save(new User("user1", "1111"));
        userDao.save(new User("user2", "2222"));
        userDao.save(new User("user3", "3333"));
    }

    @After
    public void tearDown() {
        try {
            userDao.deleteAll();
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void existingUserIsFound() {
        try {
            User u1 = userDao.findByUsername("user1");
            User u2 =new User("user1", "1111");
            assertTrue(u1.equals(u2));
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @Test
    public void nonExistingUserIsNotFound() {
        try {
            User u1 = userDao.findByUsername("user4");
            assertNull(u1);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @Test
    public void allUsersAreFound() {
        try {
            assertSame(userDao.findAll().size(), 3);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @Test
    public void deleteOperationIsWorking() {
        try {
            userDao.delete("user2");
            User u = userDao.findByUsername("user2");
            assertNull(u);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @Test
    public void newUserNotUniqueName() {
        try {
            User u = new User("user1", "yyds");
            assertNull(userDao.save(u));
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

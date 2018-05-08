package library.daoTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import library.domainTest.userTest;
import library.dao.BookDao;
import library.dao.UserDao;
import library.domain.Book;
import library.domain.User;
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
public class bookDaoTest {

    private static BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        bookDao = new BookDao("testdatabase.db");
        Book b1 = new Book(1, "1", "1", 1);
        Book b2 = new Book(2, "2", "2", 2);
        Book b3 = new Book(3, "3", "3", 3);
        Book b4 = new Book(4, "4", "4", 4);
        Book b5 = new Book(5, "5", "5", 5);
        b3.setOmistaja("nikoH");
        b4.setOmistaja("nikoH");
        b5.setOmistaja("allu");
        bookDao.save(b1);
        bookDao.save(b2);
        bookDao.save(b3);
        bookDao.save(b4);
        bookDao.save(b5);
    }

    @After
    public void tearDown() {
        try {
            bookDao.deleteAll();
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void nonExistingUserIsNotFound() {
        try {
            Book b1 = bookDao.findById(6);
            assertNull(b1);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void allUsersAreFound() {
        try {
            assertSame(bookDao.findAll().size(), 5);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void deleteOperationIsWorking() {
        try {
            bookDao.delete(1);
            Book u = bookDao.findById(1);
            assertNull(u);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void newBookNotUniqueId() {
        try {
            Book b = new Book(1, "1", "1", 1);
            assertNull(bookDao.save(b));
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void allBorrowedFound() {
        try {
            assertSame(bookDao.findAllBorrowed().size(), 3);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void borrowedByUserFound() {
        try {
            assertSame(bookDao.findAllWhichBelongsTo(new User("allu", "1")).size(), 1);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Test
    public void allAvailableFound() {
        try {
            assertSame(bookDao.findAllFree().size(), 2);
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void ownerChanging() {
        try {
            bookDao.changeOwner(bookDao.findById(1), new User("allu", "c"));
            assertEquals(bookDao.findById(1).getOmistaja(), "allu");
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void returning() {
        try {
            bookDao.returnBook(bookDao.findById(5));
            assertEquals(bookDao.findById(5).getOmistaja(), "admin");
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void idOfLastBook() {
        try {
            bookDao.returnBook(bookDao.findById(5));
            assertEquals(bookDao.findById(1).getOmistaja(), "admin");
            assertSame(5, bookDao.idOfLastBook());
        } catch (SQLException ex) {
            Logger.getLogger(userTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
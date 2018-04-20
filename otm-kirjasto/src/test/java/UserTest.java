/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.User;

/**
 *
 * @author AM
 */
public class UserTest {
    
    @Test
    public void equalOK() {
        User u1 = new User("aleKus", "uu8fJ1");
        User u2 = new User("aleKus", "uu8fJ1");
        assertTrue(u1.equals(u2));
    }
    
    @Test
    public void nonEqualOK() {
        User u1 = new User("jUnmbo", "1122121212");
        User u2 = new User("vakn", "sdfww");
        assertFalse(u1.equals(u2));
    } 
    
}

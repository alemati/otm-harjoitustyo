
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassapaate;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate(); 
        
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void kassapaateOk() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void edullisestiToimii() {
        assertEquals(10 ,kassapaate.syoEdullisesti(250));
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty()); 
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaastiToimii() {
        assertEquals(100 ,kassapaate.syoMaukkaasti(500));
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty()); 
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEiRahaaEdullisesti() {
        assertEquals(200 ,kassapaate.syoEdullisesti(200));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty()); 
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEiRahaaMaukkaasti() {
        assertEquals(100 ,kassapaate.syoMaukkaasti(100));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty()); 
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttimaksuToimiiE(){
        Maksukortti kortti = new Maksukortti(1000); 
        assertTrue(kassapaate.syoEdullisesti(kortti));
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());       
    }
    
    @Test 
    public void korttimaksuToimiiM() {
        Maksukortti kortti = new Maksukortti(1000); 
        assertTrue(kassapaate.syoMaukkaasti(kortti));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());        
    }
    
    @Test
    public void korttimaksuEiRahaaEdullisesti() {
        Maksukortti kortti = new Maksukortti(0);
        assertFalse(kassapaate.syoEdullisesti(kortti));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());    
        assertEquals(100000, kassapaate.kassassaRahaa());   
    }
    
    @Test
    public void korttimaksuEiRahaaMaukkaasti() {
        Maksukortti kortti = new Maksukortti(0);
        assertFalse(kassapaate.syoMaukkaasti(kortti));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());    
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void lataaRahaaKortilleOnnistuu() {
        Maksukortti kortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti, 1000); 
        assertEquals(101000, kassapaate.kassassaRahaa());
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void lataaRahaaKortilleEiOnnistu() {
        Maksukortti kortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti, -10);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kortti.saldo());
    }
    
}
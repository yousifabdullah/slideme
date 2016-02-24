package com.yousif.slideme.struc;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * UniqueSet-tietorakenteen yksikkötestit.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class UniqueSetTest {
    
    private static UniqueSet test;
    
    @BeforeClass
    public static void setUpClass() {
        UniqueSetTest.test = new UniqueSet();
    }
    
    // UniqueSet.optimize()-metodin yksikkötestit.
    @Test
    public void referenceWithZeroInLastIndex() {
        int key = UniqueSetTest.test.optimize(123456780);
        
        assertEquals(12345678 - 1, key);
    }
    
    @Test
    public void referenceWithZeroInSecondIndex() {
        int key = UniqueSetTest.test.optimize(102345678);
        
        assertEquals(10234567 - 1, key);
    }
    
    @Test
    public void referenceWithOnlyZero() {
        int key = UniqueSetTest.test.optimize(0);
        
        assertEquals(-1, key);
    }
    
    @Test
    public void referenceWithNegativeInteger() {
        int key = UniqueSetTest.test.optimize(-876543210);
        
        assertEquals(-87654321 - 1, key);
    }
    
    // UniqueSet.strike() ja .check() -metodien yksikkötestit.
    @Test
    public void strikeAndCheckWithinBoundaries() {
        UniqueSetTest.test.strike(123456780);
        
        assertTrue(UniqueSetTest.test.check(123456780));
    }
    
    @Test
    public void strikeAndCheckOutsideBoundaries() {
        UniqueSetTest.test.strike(12345677);
        
        assertFalse(UniqueSetTest.test.check(12345677));
    }
    
    @Test
    public void checkWithoutStrikes() {
        assertFalse(UniqueSetTest.test.check(123456780));
    }
}
package com.yousif.slideme.struc;

import com.yousif.slideme.ai.State;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MinHeap-tietorakenteen yksikkötestit.
 * 
 * @author Yousif Abdullah (yousif.abdullah@helsinki.fi)
 */
public class MinHeapTest {
    
    private MinHeap test;
    
    private static State a;
    private static State b;
    private static State c;
    private static State d;
    
    @Before
    public void setUpBefore() {
        this.test = new MinHeap<>();
        
        /*
        Yksikkötestit suoritetaan tarpeeseen räätälöidyillä State-tietueilla,
        joiden prioriteetti on laskettu valmiiksi. Huom. yksikkötestit eivät
        mene läpi, mikäli Solver-luokan vakiomuuttuja USE_BESTFIRST on true.
        */
        
        MinHeapTest.a = new State(new int[]{1, 2, 3, 4}); // 3
        MinHeapTest.b = new State(new int[]{4, 2, 1, 3}); // 2
        MinHeapTest.c = new State(new int[]{1, 2}); // 1
        MinHeapTest.d = new State(new int[]{0}); // 0
    }
    
    // MinHeap.isEmpty()-metodin yksikkötestit.
    
    @Test
    public void isEmptyOnInitialization() {
        assertTrue(this.test.isEmpty());
    }
    
    @Test
    public void isNotEmptyAfterInsertion() {
        this.test.insert(MinHeapTest.a);
        
        assertFalse(this.test.isEmpty());
    }
    
    @Test
    public void isEmptyAfterInsertionAndRetrieval() {
        this.test.insert(MinHeapTest.a);
        this.test.retrieve();
        
        assertTrue(this.test.isEmpty());
    }
    
    // MinHeap.retrieve() ja .insert() -metodien yksikkötestit.
    
    @Test
    public void retrieveFromEmpty() {
        assertEquals(null, this.test.retrieve());
    }
    
    @Test
    public void retrieveAfterInsertion() {
        this.test.insert(MinHeapTest.a);
        State retrieved = (State) this.test.retrieve();
        
        assertEquals(MinHeapTest.a, retrieved);
    }
    
    @Test
    public void retrieveLowestPriority() {        
        this.test.insert(MinHeapTest.a);
        this.test.insert(MinHeapTest.b);
        this.test.insert(MinHeapTest.d);
        this.test.insert(MinHeapTest.c);
        
        State retrieved = (State) this.test.retrieve();
        
        assertEquals(MinHeapTest.d, retrieved);
    }
}
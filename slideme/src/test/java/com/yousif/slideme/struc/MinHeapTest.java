package com.yousif.slideme.struc;

import com.yousif.slideme.ai.State;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MinHeap-tietorakenteen yksikkötestit.
 * 
 * @author Yousif Abdullah {@literal<yousif.abdullah@helsinki.fi>}
 */
public class MinHeapTest {
    
    private static State a;
    private static State b;
    private static State c;
    private static State d;
    
    @BeforeClass
    public static void setUpClass() {
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
        MinHeap test = new MinHeap<>();
        
        assertTrue(test.isEmpty());
    }
    
    @Test
    public void isNotEmptyAfterInsertion() {
        MinHeap test = new MinHeap<>();
        test.insert(MinHeapTest.a);
        
        assertFalse(test.isEmpty());
    }
    
    @Test
    public void isEmptyAfterInsertionAndRetrieval() {
        MinHeap test = new MinHeap<>();
        
        test.insert(MinHeapTest.a);
        test.retrieve();
        
        assertTrue(test.isEmpty());
    }
    
    // MinHeap.retrieve() ja .insert() -metodien yksikkötestit.
    
    @Test
    public void retrieveFromEmpty() {
        MinHeap test = new MinHeap<>();
        
        assertEquals(null, test.retrieve());
    }
    
    @Test
    public void retrieveAfterInsertion() {
        MinHeap test = new MinHeap<>();
        
        test.insert(MinHeapTest.a);
        State retrieved = (State) test.retrieve();
        
        assertEquals(MinHeapTest.a, retrieved);
    }
    
    @Test
    public void retrieveLowestPriority() {
        MinHeap test = new MinHeap<>();
        
        test.insert(MinHeapTest.a);
        test.insert(MinHeapTest.b);
        test.insert(MinHeapTest.d);
        test.insert(MinHeapTest.c);
        
        State retrieved = (State) test.retrieve();
        
        assertEquals(MinHeapTest.d, retrieved);
    }
}
package com.yousif.slideme.ai;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * State-apuluokan yksikkötestit.
 * 
 * @author Yousif Abdullah (yousif.abdullah@helsinki.fi)
 */
public class StateTest {
    
    // State.getNextState()-metodin yksikkötestit.
    
    @Test
    public void nextStateUpWhenPossible() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        State state2 = state1.getNextState(0);
        
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 6}, state2.getCurrentIteration()));
    }
    
    @Test
    public void nextStateDownWhenPossible() {
        State state1 = new State(new int[]{1, 2, 0, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(1);
        
        assertTrue(Arrays.equals(new int[]{1, 2, 5, 3, 4, 0, 6, 7, 8}, state2.getCurrentIteration()));
    }
    
    @Test
    public void nextStateLeftWhenPossible() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8});
        State state2 = state1.getNextState(2);
        
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 5, 6, 0, 7, 8}, state2.getCurrentIteration()));
    }
    
    @Test
    public void nextStateRightWhenPossible() {
        State state1 = new State(new int[]{1, 2, 3, 0, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(3);
        
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 0, 5, 6, 7, 8}, state2.getCurrentIteration()));
    }
    
    @Test
    public void nextStateUpWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 0, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(0);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateDownWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        State state2 = state1.getNextState(1);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateLeftWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 3, 0, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(2);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateRightWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 0, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(3);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateUpWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(0);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateDownWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(1);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateLeftWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(2);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateRightWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.getNextState(3);
        
        assertEquals(null, state2);
    }
    
    @Test
    public void nextStateWithIllegalDirection() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        State state2 = state1.getNextState(-1);
        
        assertEquals(null, state2);
    }
}
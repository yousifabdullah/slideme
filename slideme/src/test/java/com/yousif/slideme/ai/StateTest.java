package com.yousif.slideme.ai;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Array-apuluokan yksikkötestit.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class StateTest {
    
    // State.moveTile()-metodin yksikkötestit.
    @Test
    public void moveTileUpWhenPossible() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        State state2 = state1.moveTile("up");
        
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 6}, state2.getCurrentIteration()));
    }
    
    @Test
    public void moveTileDownWhenPossible() {
        State state1 = new State(new int[]{1, 2, 0, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("down");
        
        assertTrue(Arrays.equals(new int[]{1, 2, 5, 3, 4, 0, 6, 7, 8}, state2.getCurrentIteration()));
    }
    
    @Test
    public void moveTileLeftWhenPossible() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8});
        State state2 = state1.moveTile("left");
        
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 5, 6, 0, 7, 8}, state2.getCurrentIteration()));
    }
    
    @Test
    public void moveTileRightWhenPossible() {
        State state1 = new State(new int[]{1, 2, 3, 0, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("right");
        
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 0, 5, 6, 7, 8}, state2.getCurrentIteration()));
    }
    
    @Test
    public void moveTileUpWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 0, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("up");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileDownWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        State state2 = state1.moveTile("down");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileLeftWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 3, 0, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("left");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileRightWhenImpossible() {
        State state1 = new State(new int[]{1, 2, 0, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("right");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileUpWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("up");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileDownWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("down");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileLeftWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("left");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileRightWithoutZero() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        State state2 = state1.moveTile("right");
        
        assertEquals(null, state2);
    }
    
    @Test
    public void moveTileWithIllegalDirection() {
        State state1 = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        State state2 = state1.moveTile("northwest");
        
        assertEquals(null, state2);
    }
}
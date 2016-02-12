package com.yousif.slideme.core;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Array-apuluokan yksikkötestit.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class ArrayTest {
    
    // Array.swap()-metodin yksikkötestit.
    @Test
    public void swapIndexesWithinBoundaries() {
        int[] array = new int[]{1, 2, 3, 4};
        Array.swap(array, 2, 1);
        
        assertTrue(Arrays.equals(array, new int[]{1, 3, 2, 4}));
    }
    
    @Test
    public void swapIndexesOutsideBoundaries() {
        int[] array = new int[]{1, 2, 3, 4};
        Array.swap(array, 5, -1);
        
        assertTrue(Arrays.equals(array, new int[]{1, 2, 3, 4}));
    }
    
    @Test
    public void swapIdenticalIndexes() {
        int[] array = new int[]{1, 2, 3, 4};
        Array.swap(array, 1, 1);
        
        assertTrue(Arrays.equals(array, new int[]{1, 2, 3, 4}));
    }
    
    // Array.indexOf()-metodin yksikkötestit.
    @Test
    public void indexOfValueWithinBoundaries() {
        int[] array = new int[]{1, 2, 3, 4};
        
        assertEquals(2, Array.indexOf(array, 3));
    }
    
    @Test
    public void indexOfValueOutsideBoundaries() {
        int[] array = new int[]{1, 2, 3, 4};
        
        assertEquals(-1, Array.indexOf(array, 5));
    }
    
    // Array.copy()-metodin yksikkötestit.
    @Test
    public void copyPopulatedArray() {
        int[] array1 = new int[]{1, 2, 3, 4};
        int[] array2 = Array.copy(array1);
        
        assertTrue(Arrays.equals(array1, array2));
    }
    
    @Test
    public void copyEmptyArray() {
        int[] array1 = new int[0];
        int[] array2 = Array.copy(array1);
        
        assertTrue(Arrays.equals(array1, array2));
    }
    
    // Array.matches()-metodin yksikkötestit.
    @Test
    public void matchesPopulatedArray() {
        int[] array1 = new int[]{1, 2, 3, 4};
        int[] array2 = new int[]{1, 2, 3, 4};
        
        assertTrue(Array.matches(array1, array2));
    }
    
    @Test
    public void matchesEmptyArray() {
        int[] array1 = new int[0];
        int[] array2 = new int[0];
        
        assertTrue(Array.matches(array1, array2));
    }
    
    @Test
    public void matchesMismatchedArrayOrder() {
        int[] array1 = new int[]{1, 2, 3, 4};
        int[] array2 = new int[]{4, 3, 2, 1};
        
        assertFalse(Array.matches(array1, array2));
    }
    
    @Test
    public void matchesMismatchedArraySize() {
        int[] array1 = new int[]{1, 2, 3, 4};
        int[] array2 = new int[]{1, 2, 3};
        
        assertFalse(Array.matches(array1, array2));
    }
    
    // Array.swapFirstInversion()-metodin yksikkötestit.
    @Test
    public void swapFirstInversionWithoutInversions() {
        int[] array = new int[]{1, 2, 3, 0};
        Array.swapFirstInversion(array);
        
        assertTrue(Arrays.equals(array, new int[]{1, 2, 3, 0}));
    }
    
    @Test
    public void swapFirstInversionWithoutInversionsOrZero() {
        int[] array = new int[]{1, 2, 3, 4};
        Array.swapFirstInversion(array);
        
        assertTrue(Arrays.equals(array, new int[]{1, 2, 3, 4}));
    }
    
    @Test
    public void swapFirstInversionWithoutZero() {
        int[] array = new int[]{1, 2, 4, 3};
        Array.swapFirstInversion(array);
        
        assertTrue(Arrays.equals(array, new int[]{1, 2, 3, 4}));
    }
    
    @Test
    public void swapFirstInversionWithZeroInFirstIndex() {
        int[] array = new int[]{0, 2, 4, 3};
        Array.swapFirstInversion(array);
        
        assertTrue(Arrays.equals(array, new int[]{0, 2, 3, 4}));
    }
    
    @Test
    public void swapFirstInversionWithZeroInSecondIndex() {
        int[] array = new int[]{1, 0, 4, 3};
        Array.swapFirstInversion(array);
        
        assertTrue(Arrays.equals(array, new int[]{1, 0, 3, 4}));
    }
    
    // Array.countInversions()-metodin yksikkötestit.
    @Test
    public void countInversionsWithoutInversions() {
        int[] array = new int[]{1, 2, 3, 0};
        
        assertEquals(0, Array.countInversions(array));
    }
    
    @Test
    public void countInversionsWithoutInversionsOrZero() {
        int[] array = new int[]{1, 2, 3, 4};
        
        assertEquals(0, Array.countInversions(array));
    }
    
    @Test
    public void countInversionsWithEvenInversions() {
        int[] array = new int[]{2, 3, 1, 0};
        
        assertEquals(2, Array.countInversions(array));
    }
    
    @Test
    public void countInversionsWithOddInversions() {
        int[] array = new int[]{1, 3, 2, 0};
        
        assertEquals(1, Array.countInversions(array));
    }
    
    // Array.shuffleWithEvenInversions()-metodin yksikkötestit.
    @Test
    public void shuffleWithEvenInversionsNoOddInversions() {
        int[] array = new int[]{2, 1, 3, 4};
        Array.shuffleWithEvenInversions(array);
        
        assertEquals(0, Array.countInversions(array) % 2);
    }
}
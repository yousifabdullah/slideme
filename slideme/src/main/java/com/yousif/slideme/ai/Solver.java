package com.yousif.slideme.ai;

/**
 * Tekoälyn toiminnallisuuden määrittelevä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class Solver {
    
    /*
    Lasketaan Manhattan-etäisyys indeksien a ja b välillä oleville
    peliruuduille.
    */
    public static int manhattanDistance(int a, int b) {
        return Math.abs((a / 3) - (b / 3)) + Math.abs((a % 3) - (b % 3));
    }
    
    /*
    A*-algoritmin heuristiikkana käytetään maksimia kaikkien peliruutujen
    Manhattan-etäisyydestä.
    */
    public static int heuristic(int[] tiles) {
        int h = 0;
        
        for (int i = 0; i < tiles.length; i++) {
            // Huom. 0 merkitsee vapaaruutua, ei lukua 0!
            if (tiles[i] != 0) {
                h = Math.max(h, manhattanDistance(i, tiles[i]));
            }
        }
        
        return h;
    }
}
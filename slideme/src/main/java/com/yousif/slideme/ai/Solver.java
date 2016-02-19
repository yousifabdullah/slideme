package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;
import com.yousif.slideme.core.Board;

import java.util.PriorityQueue;

/**
 * Tekoälyn toiminnallisuuden määrittävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class Solver {
    
    // HUOM! NÄMÄ KORVATAAN OMILLA TIETORAKENTEILLA SEURAAVASSA PÄIVITYKSESSÄ!
    private final PriorityQueue <State> queue = new PriorityQueue<>(100, (State a, State b) -> a.priority() - b.priority());
    
    private final Board board;
    
    /**
     * Alustaa tekoälyn nykyisen pelitilanteen mukaisesti.
     * 
     * @param board nykyinen pelitilanne Board-tietueena
     */
    public Solver(Board board) {
        this.board = board;
    }
    
    public int[] findPath() {
        
        queue.clear();
        boolean[] closed = new boolean[876543210];
        
        queue.add(new State(this.board.getCurrentState()));
        
        while (!queue.isEmpty()) {
            
            State state = queue.poll();
            
            if (Array.matches(state.getCurrentIteration(), this.board.getFinalState())) {
                return this.tracePath(state);
            }
            
            closed[this.getReference(state)] = true;
            
            for (int i = 0; i < 4; i++) {
                State successor = state.nextState(i);
                
                if (successor != null && closed[this.getReference(successor)] == false) {
                    queue.add(successor);
                }
            }
        }
        
        return null;
    }
    
    int getReference(State state) {
        return Array.asInteger(state.getCurrentIteration()) - 1;
    }
    
    /*
    Lasketaan Manhattan-etäisyys indeksien a ja b välillä oleville
    peliruuduille.
    */
    static int manhattanDistance(int a, int b) {
        return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
    }
    
    /*
    A*-algoritmin heuristiikkana käytetään maksimia kaikkien peliruutujen
    Manhattan-etäisyydestä.
    */
    static int heuristic(int[] tiles) {
        int heuristic = 0;
        
        for (int i = 0; i < tiles.length; i++) {
            // Huom. 0 merkitsee vapaaruutua, ei lukua 0!
            if (tiles[i] != 0) {
                heuristic = Math.max(heuristic, manhattanDistance(i, tiles[i]));
            }
        }
        
        return heuristic;
    }
    
    private int[] tracePath(State state) {
        int[] path = new int[state.getDistance() + 1];
        
        // AIKAVAATIVUUS?
        
        /*int iteration = path.length - 1;
        
        while (state != null) {
            path[iteration] = state.getIndexOfZero();
            iteration--;
            
            state = state.getPreviousState();
        }*/
        
        return path;
    }
}
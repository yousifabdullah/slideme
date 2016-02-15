package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;
import com.yousif.slideme.core.Board;

// HUOM! NÄMÄ KORVATAAN OMILLA TIETORAKENTEILLA SEURAAVASSA PÄIVITYKSESSÄ!
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Tekoälyn toiminnallisuuden määrittävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class Solver {
    
    // HUOM! NÄMÄ KORVATAAN OMILLA TIETORAKENTEILLA SEURAAVASSA PÄIVITYKSESSÄ!
    private final PriorityQueue <State> queue = new PriorityQueue<>(100, (State a, State b) -> a.priority() - b.priority());
    private final HashSet <State> closed = new HashSet<>();
    
    private final Board board;
    
    public Solver(Board board) {
        this.board = board;
    }
    
    public void findPath() {
        
        queue.clear();
        closed.clear();
        
        queue.add(new State(this.board.getCurrentState()));
        
        while (!queue.isEmpty()) {
            
            State state = queue.poll();
            
            if (Array.matches(state.getCurrentIteration(), this.board.getFinalState())) {
                tracePath(state);
                
                return;
            }
            
            closed.add(state);
            
            addSuccessor(state.moveTile("up"));
            addSuccessor(state.moveTile("down"));
            addSuccessor(state.moveTile("left"));
            addSuccessor(state.moveTile("right"));
        }
    }
    
    private void addSuccessor(State successor) {
        if (successor != null && !closed.contains(successor)) {
            queue.add(successor);
        }
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
    
    private void tracePath(State state) {
        while (state != null) {
            System.out.print(state.getIndexOfZero() + " ");
            state = state.getPreviousState();
        }
    }
}
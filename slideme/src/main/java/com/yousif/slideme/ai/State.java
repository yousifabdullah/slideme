package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;
import com.yousif.slideme.core.Board;

import java.util.Objects;

/**
 * Tekoälyn välivaiheita ylläpitävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
class State {
    
    /*
    Alustetaan muistiin nykyinen pelitilanne ja ratkaisu sekä mm. väli-
    vaiheisiin tarvittavat tiedot edellisestä pelitilanteesta.
    */
    private Board board;
    
    private final int[] tiles;
    private final int indexOfZero;
    
    private final int g;
    private final int h;
    
    // Pidetään yllä ketjua edellisistä pelitilanteista.
    private final State previous;
    
    // Luodaan ketjulle lähtötilanne nykyisen pelitilanteen mukaisesti.
    State(Board board) {
        // Luetaan muistiin nykyinen pelitilanne.
        this.board = board;
        this.tiles = this.board.getCurrentState();
        
        // Haetaan vapaaruudun sijainti ja kirjataan se muistiin.
        this.indexOfZero = Array.indexOf(this.tiles, 0);
        
        this.g = 0;
        this.h = Solver.heuristic(this.tiles);
        
        this.previous = null;
    }
    
    // Luodaan seuraavia iteraatioitaa ketjussa edellisen perusteella.
    State(State previous, int from) {
        // Luetaan muistiin edellinen pelitilanne.
        this.tiles = Array.copy(previous.tiles);
        
        // Päivitetään vapaaruudun sijainti nykyisessä iteraatiossa.
        Array.swap(this.tiles, previous.indexOfZero, from);
        indexOfZero = from;
        
        this.g = previous.g + 1;
        this.h = Solver.heuristic(this.tiles);
        
        this.previous = previous;
    }
    
    boolean foundSolution() {
        return Array.matches(this.tiles, this.board.getSolvedState());
    }
    
    // VAPAARUUDUN SIIRTO YLÖS, ALAS, VASEMMALLE JA OIKEALLE. TARKISTA.
    
    State moveTileDown() {
        if (this.indexOfZero > 2) {
            return new State(this, this.indexOfZero - 3);
        }
        
        return null;
    }
    
    State moveTileUp() {
        if (this.indexOfZero < 6) {
            return new State(this, this.indexOfZero + 3);
        }
        
        return null;
    }
    
    State moveTileLeft() {
        if (this.indexOfZero % 3 < 2) {
            return new State(this, this.indexOfZero + 1);
        }
        
        return null;
    }
    
    State moveTileRight() {
        if (this.indexOfZero % 3 > 0) {
            return new State(this, this.indexOfZero - 1);
        }
        
        return null;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof State) {
            State other = (State) object;
            
            return Array.matches(this.tiles, other.tiles);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.tiles);
    }
}
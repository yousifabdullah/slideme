package com.yousif.slideme.core;

/**
 * Pelitilannetta ylläpitävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public final class Board {
    
    // Alustetaan muistiin sekä nykyinen pelitilanne että ratkaisu
    private final int[] board;
    private final int[] solution;
    
    public Board() {
        this.board = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0};
        this.solution = Array.copy(this.board);
        
        // Luodaan aina uusi sekoitettu järjestys olion luontihetkellä
        this.shuffleOrder();
    }
    
    /**
     * Palauttaa nykyisen pelitilanteen taulukkona.
     * 
     * @return nykyinen pelitilanne int[]-taulukkona
     */
    public int[] getCurrentState() {
        return this.board;
    }
    
    /**
     * Tarkistaa nykyisen pelitilanteen ja palauttaa arvon true, mikäli
     * se vastaa ratkaisua.
     * 
     * @return true, kun pelitilanne vastaa ratkaisua ja muutoin false
     */
    public boolean foundSolution() {
        return Array.matches(this.board, this.solution);
    }
    
    /**
     * Luo uuden pelitilanteen sekoittamalla nykyisen järjestyksen
     * satunnaiseksi.
     */
    public void shuffleOrder() {
        Array.shuffleWithEvenInversions(this.board);
    }
}
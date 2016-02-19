package com.yousif.slideme.core;

/**
 * Pelitilannetta ylläpitävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public final class Board {
    
    private final int[] board;
    private final int[] solution;
    
    private final int[][] movesMap;
    
    /**
     * Alustaa muistiin nykyisen pelitilanteen ja ratkaisun sekä kartan
     * hyväksyttävistä siirroista.
     */
    public Board() {
        this.board = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0};
        this.solution = Array.copy(this.board);
        
        /*
        Hyväksyttävät siirrot ovat oikeastaan suuntia, joihin jokaisesta
        peliruudusta on mahdollista siirtyä seuraavaksi. Esim. jos 2.
        indeksin peliruutu halutaan siirtää, onnistuu se vain, kun joko
        1. tai 5. indeksin kohdalla on vapaaruutu (eli vasemmalle tai
        alas siirtymällä).
        
        Indeksit luetaan 3*3-ruudun suuruista alustaa noudattaen vasemmalta
        ylhäältä eli esim. 5. indeksi sijoittuisi toisen rivin viimeiseksi
        peliruuduksi kuten alla olevassa kuvassa havainnoillistettuna:
        
        + - + - + - +
        | 0 | 1 | 2 |
        + - + - + - +
        | 3 | 4 | 5 | <-
        + - + - + - +
        | 6 | 7 | 8 |
        + - + - + - +
        
        Huom. ensimmäinen peliruutu sijaitsee siis indeksissä 0 ja siirto-
        kartalla -1 merkitsee ei-hyväksyttävää siirtoa.
        */
        this.movesMap = new int[][] {
            {1, 3, -1, -1}, // 0.
            {0, 2, 4, -1},  // 1.
            {1, 5, -1, -1}, // 2.
            {0, 4, 6, -1},  // 3.
            {1, 3, 5, 7},   // 4.
            {2, 4, 8, -1},  // 5.
            {3, 7, -1, -1}, // 6.
            {4, 6, 8, -1},  // 7.
            {5, 7, -1, -1}  // 8. peliruudun hyväksyttävät siirrot.
        };
        
        // Luodaan aina uusi sekoitettu järjestys jo luontihetkellä.
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
     * Palauttaa ratkaistun pelitilanteen taulukkona.
     * 
     * @return ratkaistu pelitilanne int[]-taulukkona
     */
    public int[] getFinalState() {
        return this.solution;
    }
    
    /**
     * Luo uuden pelitilanteen sekoittamalla nykyisen järjestyksen
     * satunnaiseksi.
     */
    public void shuffleOrder() {
        Array.shuffleWithEvenInversions(this.board);
    }
    
    /**
     * Päivittää pelitilanteen siirtämällä annetun indeksin kohdalla
     * sijaitsevan peliruudun vapaaruudun tilalle, mikäli se on mahdollista.
     * 
     * Koska siirtokartasta tarkastellaan annettua indeksiä vastaavat
     * mahdolliset hyväksyttävät siirrot, on aikavaativuus O(n).
     * 
     * @param from siirrettävän peliruudun indeksi
     * @return true, kun siirto on onnistunut ja muutoin false
     */
    public boolean moveTile(int from) {
        // Huom. aluksi tarkistetaan, ettei kyseessä ole vapaaruutu.
        if (this.board[from] != 0) {
            for (int move = 0; move < 4; move++) {
                // Luetaan muistiin siirtokartasta hyväksyttävät siirot.
                int to = this.movesMap[from][move];
                
                /*
                Kun siirtokartta palauttaa arvon -1, eli hyväksyttäviä
                siirtoja ei ole enempää, loppuu algoritmin toiminta heti.
                */
                if (to == -1) {
                    break;
                } else {
                    /*
                    Mikäli siirrettävästä peliruudusta on mahdollista
                    siirtyä vapaaruutuun, vaihdetaan kyseisten peliruutujen
                    paikat keskenään.
                    */
                    if (this.board[to] == 0) {
                        Array.swap(this.board, from, to);
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}
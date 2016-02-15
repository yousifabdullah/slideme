package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;

/**
 * Tekoälyn välivaiheita ylläpitävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class State {
    
    /*
    Alustetaan muistiin nykyinen iteraatio eli sen hetkinen pelitilanne
    sekä tekoälyn välivaiheisiin tarvittavat muuttujat.
    */
    private final int[] iteration;
    private final int indexOfZero;
    
    // A*-algoritmia varten käytettävät g- ja h-arvot.
    private final int g;
    private final int h;
    
    // Pidetään yllä ketjua edellisistä pelitilanteista.
    private final State previous;
    
    // Luodaan ketjulle lähtötilanne nykyisen pelitilanteen mukaisesti.
    public State(int[] iteration) {
        // Luetaan muistiin nykyinen pelitilanne.
        this.iteration = iteration;
        
        // Haetaan vapaaruudun sijainti ja kirjataan se ylös.
        this.indexOfZero = Array.indexOf(this.iteration, 0);
        
        // Asetetaan lähtöarvo ja päivitetään heuristinen etäisyys.
        this.g = 0;
        this.h = Solver.heuristic(this.iteration);
        
        this.previous = null;
    }
    
    // Luodaan seuraava iteraatio ketjussa edellisen perusteella.
    public State(State previous, int newIndex) {
        // Luetaan muistiin edellinen pelitilanne.
        this.iteration = Array.copy(previous.iteration);
        
        // Päivitetään vapaaruudun sijainti nykyisessä iteraatiossa.
        Array.swap(this.iteration, previous.indexOfZero, newIndex);
        this.indexOfZero = newIndex;
        
        // Korotetaan lähtöarvoa ja päivitetään heuristinen etäisyys.
        this.g = previous.g + 1;
        this.h = Solver.heuristic(this.iteration);
        
        this.previous = previous;
    }
    
    /**
     * Palauttaa nykyisen iteraation taulukkona.
     * 
     * @return nykyinen iteraatio int[]-taulukkona
     */
    int[] getCurrentIteration() {
        return this.iteration;
    }
    
    /**
     * Palauttaa edellisen iteraation State-tietueena.
     * 
     * @return edellinen iteraatio State-tietueena
     */
    State getPreviousState() {
        return this.previous;
    }
    
    /**
     * Palauttaa vapaaruudun sijainnin eli indeksin nykyisessä iteraatiossa.
     * 
     * @return vapaaruudun indeksi nykyisessä iteraatiossa
     */
    int getIndexOfZero() {
        return this.indexOfZero;
    }
    
    /**
     * Laskee nykyisen iteraation f-arvon etäisyyden (siis g-arvon) ja
     * heuristisen funktion perusteella. A*-algoritmin käyttämä minimikeko
     * ylläpitää iteraatioiden järjestystä juuri f-arvoilla.
     * 
     * @return nykyisen iteraation f-arvo
     */
    int priority() {
        return this.g + this.h;
    }
    /**
     * Siirtää vapaaruutua muuttamalla sen indeksiä ja samalla luo uuden
     * iteraation. Toisin kuin 8-peliä pelatessa, jossa pelaaja siirtää
     * aina muita peliruutuja vapaaruudun tilalle, siirtyy vapaaruutu
     * tekoälyssä. Tämän takia on tekoälyn kannalta edullista pitää kirjaa
     * vapaaruudun sijainnista, jolloin riittää kasvattaa/vähentää sen
     * indeksiä uuden iteraation luomiseksi.
     * 
     * @param direction suunta, johon vapaaruutu siirtyy seuraavaksi
     * @return uusi iteraatio State-tietueena
     */
    State moveTile(String direction) {
        /*
        Mikäli vapaaruutua ei annetussa iteraatiossa ole olemassakaan,
        annetaan paluuarvona null.
        */
        if (this.indexOfZero == -1) {
            return null;
        }
        
        switch (direction) {
            case "up":
                return this.indexOfZero > 2 ? new State(this, this.indexOfZero - 3) : null;
            case "down":
                return this.indexOfZero < 6 ? new State(this, this.indexOfZero + 3) : null;
            case "left":
                return this.indexOfZero % 3 > 0 ? new State(this, this.indexOfZero - 1) : null;
            case "right":
                return this.indexOfZero % 3 < 2 ? new State(this, this.indexOfZero + 1) : null;
        }
        
        return null;
    }
    
    /**
     * Aputoiminto HashSet-tietorakenteelle, joka vertailee iteraatioita
     * keskenään ja palauttaa arvon true, mikäli annettu iteraatio on
     * identtinen nykyisen iteraation kanssa.
     * 
     * @param object iteraatio Object-tietueena
     * @return true, kun iteraatiot ovat identtiset ja muutoin false
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof State) {
            State other = (State) object;
            
            return Array.matches(this.iteration, other.iteration);
        }
        
        return false;
    }
    
    /**
     * Aputoiminto HashSet-tietorakenteelle, joka palauttaa nykyiselle
     * iteraatiolle oman hajautusarvon.
     * 
     * @return nykyisen iteraation hajautusarvo
     */
    @Override
    public int hashCode() {
        return Array.asInteger(this.iteration);
    }
}
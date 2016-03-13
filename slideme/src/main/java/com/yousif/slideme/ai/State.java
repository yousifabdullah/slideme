package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;

/**
 * Tekoälyn välivaiheita ylläpitävä luokka.
 * 
 * @author Yousif Abdullah (yousif.abdullah@helsinki.fi)
 */
public class State implements Comparable<State> {
    
    private final int[] iteration;
    private final int indexOfZero;
    
    private final int distance;
    private final int heuristic;
    
    private final State previous;
    
    /**
     * Luo ketjulle lähtötilanteen nykyisen pelitilanteen mukaisesti ja
     * alustaa muistiin nykyisen iteraation eli pelitilanteen.
     * 
     * @param initial nykyinen pelitilanne int[]-taulukkona
     */
    public State(int[] initial) {
        // Luetaan muistiin nykyinen pelitilanne.
        this.iteration = initial;
        
        // Haetaan vapaaruudun sijainti ja kirjataan se ylös.
        this.indexOfZero = Array.indexOf(this.iteration, 0);
        
        // Asetetaan lähtöarvo ja päivitetään heuristinen etäisyys.
        this.distance = 0;
        this.heuristic = Solver.heuristic(this.iteration);
        
        // Pidetään yllä ketjua edellisistä pelitilanteista.
        this.previous = null;
    }
    
    /**
     * Luo seuraavan iteraation ketjussa edellisen perusteella.
     * 
     * @param previous edellinen iteraatio State-tietueena
     * @param newIndex vapaaruudun indeksi uudessa iteraatiossa
     */
    public State(State previous, int newIndex) {
        // Luetaan muistiin edellinen pelitilanne.
        this.iteration = Array.copy(previous.iteration);
        
        // Päivitetään vapaaruudun sijainti nykyisessä iteraatiossa.
        Array.swap(this.iteration, previous.indexOfZero, newIndex);
        this.indexOfZero = newIndex;
        
        // Korotetaan lähtöarvoa ja päivitetään heuristinen etäisyys.
        this.distance = previous.distance + 1;
        this.heuristic = Solver.heuristic(this.iteration);
        
        // Yhdistetään edellinen iteraatio nykyiseen ketjussa.
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
     * Esittää nykyisen iteraation kokonaislukuna, joka vastaa iteraatiossa
     * esiintyviä alkioita numeerisesti.
     * 
     * @return nykyisen iteraation esitys kokonaislukuna
     */
    int getCurrentAsInteger() {
        return Array.asInteger(this.iteration);
    }
    
    /**
     * Siirtää vapaaruutua muuttamalla sen indeksiä ja samalla luo uuden
     * iteraation. Toisin kuin 8-peliä pelatessa, jossa pelaaja siirtää
     * aina muita peliruutuja vapaaruudun tilalle, siirtyy vapaaruutu
     * tekoälyssä. Tämän takia on tekoälyn kannalta edullista pitää kirjaa
     * vapaaruudun sijainnista, jolloin riittää kasvattaa/pienentää indeksiä
     * uuden iteraation luomiseksi. Toiminnon aikavaativuus on O(1).
     * 
     * Hyväksyttävät suunnat: 0 (ylös), 1 (alas), 2 (vasemmalle) ja
     * 3 (oikealle).
     * 
     * @param direction suunta, johon vapaaruutu siirtyy seuraavaksi
     * @return uusi iteraatio State-tietueena
     */
    State getNextState(int direction) {
        /*
        Mikäli vapaaruutua ei annetussa iteraatiossa ole olemassakaan,
        annetaan paluuarvona null.
        */
        if (this.indexOfZero == -1) {
            return null;
        }
        
        switch (direction) {
            case 0: // ylös
                if (this.indexOfZero > 2) { return new State(this, this.indexOfZero - 3); }
                break;
            case 1: // alas
                if (this.indexOfZero < 6) { return new State(this, this.indexOfZero + 3); }
                break;
            case 2: // vasemmalle
                if (this.indexOfZero % 3 > 0) { return new State(this, this.indexOfZero - 1); }
                break;
            case 3: // oikealle
                if (this.indexOfZero % 3 < 2) { return new State(this, this.indexOfZero + 1); }
                break;
        }
        
        return null;
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
     * Palauttaa etäisyyden lähtötilanteesta nykyisessä iteraatiossa.
     * 
     * @return nykyisen iteraation etäisyys lähtötilanteesta
     */
    int getDistance() {
        return this.distance;
    }
    
    /**
     * Laskee nykyisen iteraation prioriteetin etäisyyden ja heuristisen
     * funktion perusteella. Hakualgoritmin käyttämä minimikeko ylläpitää
     * iteraatioiden järjestystä prioriteetin avulla. Toiminnon aika-
     * vaativuus on O(1).
     * 
     * @param other vertailtava iteraatio State-tietueena
     * @return nykyisen iteraation prioriteetti
     */
    @Override
    public int compareTo(State other) {
        return (this.distance + this.heuristic) - (other.distance + other.heuristic);
    }
}
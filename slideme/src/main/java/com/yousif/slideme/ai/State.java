package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;

/**
 * Tekoälyn välivaiheita ylläpitävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class State {
    
    /*
    Alustetaan muistiin nykyinen iteraatio eli pelitilanne sekä tekoälyn
    välivaiheisiin tarvittavat muuttujat.
    */
    private final int[] iteration;
    private final int indexOfZero;
    
    // Hakualgoritmia varten käytettävät g- ja h-arvot.
    private final int g;
    private final int h;
    
    // Pidetään yllä ketjua edellisistä pelitilanteista.
    private final State previous;
    
    /**
     * Luo ketjulle lähtötilanteen nykyisen pelitilanteen mukaisesti.
     * 
     * @param initial nykyinen pelitilanne int[]-taulukkona
     */
    public State(int[] initial) {
        // Luetaan muistiin nykyinen pelitilanne.
        this.iteration = initial;
        
        // Haetaan vapaaruudun sijainti ja kirjataan se ylös.
        this.indexOfZero = Array.indexOf(this.iteration, 0);
        
        // Asetetaan lähtöarvo ja päivitetään heuristinen etäisyys.
        this.g = 0;
        this.h = Solver.heuristic(this.iteration);
        
        this.previous = null;
    }
    
    /**
     * Luo seuraavan iteraatio ketjussa edellisen perusteella.
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
        
        // Korotetaan lähtöarvo ja päivitetään heuristinen etäisyys.
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
     * Palauttaa etäisyyden eli g-arvon nykyisessä iteraatiossa.
     * 
     * @return nykyisen iteraation g-arvo
     */
    int getDistance() {
        return this.g;
    }
    
    /**
     * Laskee nykyisen iteraation f-arvon etäisyyden (siis g-arvon) ja
     * heuristisen funktion perusteella. Hakualgoritmin käyttämä minimikeko
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
     * vapaaruudun sijainnista, jolloin riittää kasvattaa/pienentää indeksiä
     * uuden iteraation luomiseksi. Toiminnon aikavaativuus on O(1).
     * 
     * Hyväksyttävät suunnat: 0 (ylös), 1 (alas), 2 (vasemmalle) ja
     * 3 (oikealle).
     * 
     * @param direction suunta, johon vapaaruutu siirtyy seuraavaksi
     * @return uusi iteraatio State-tietueena
     */
    State nextState(int direction) {
        /*
        Mikäli vapaaruutua ei annetussa iteraatiossa ole olemassakaan,
        annetaan paluuarvona null. Huom. tämän ei pitäisi koskaan tapahtua!
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
     * Aputoiminto UniqueSet-tietorakenteelle, joka vertailee iteraatioita
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
     * Aputoiminto UniqueSet-tietorakenteelle, joka palauttaa nykyiselle
     * iteraatiolle oman hajautusarvon.
     * 
     * @return nykyisen iteraation hajautusarvo
     */
    @Override
    public int hashCode() {
        return Array.asInteger(this.iteration);
    }
}
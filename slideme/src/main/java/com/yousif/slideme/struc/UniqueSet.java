package com.yousif.slideme.struc;

/**
 * Erikoistietorakenne 8-pelille, joka korvaa HashSet-tietorakenteen.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class UniqueSet {
    
    private final boolean[] state;
    
    /**
     * Luo taulukon, joka sisältää kaikki mahdolliset 8-pelin permutaatiot
     * numeerisessa muodossa, kuten 012345678 ja 876543210. Vaikka taulun
     * tilavaativuus on suuri, noin O(241 * n), käyttää se boolean[]-
     * taulukkona muistia vain kohtuullisesti.
     * 
     * Suuren tilavaativuuden ansiosta voidaan kuitenkin taata pahimmassa
     * tapauksessa O(1) aikavaativuuden toiminnot, joilla nopeutetaan haku-
     * algoritmia.
     */
    public UniqueSet() {
        this.state = new boolean[87654321];
    }
    
    /**
     * Yliviivaa eli merkkaa läpikäydyksi annetun iteraation taulukossa.
     * Toiminnon aikavaativuus on O(1).
     * 
     * @param key iteraatio numeerisessa muodossa
     */
    public void strike(int key) {
        // Huom. aluksi tarkistetaan, että indeksit löytyvät taulukosta.
        if (12345678 <= key && key <= 876543210) {
            this.state[optimize(key)] = true;
        }
    }
    
    /**
     * Tarkistaa, onko annettu iteraatio yliviivattu taulukossa.
     * Toiminnon aikavaativuus on O(1).
     * 
     * @param key iteraatio numeerisessa muodossa
     * @return true, kun iteraatio on yliviivattu ja muutoin false
     */
    public boolean check(int key) {
        // Huom. aluksi tarkistetaan, että indeksit löytyvät taulukosta.
        if (12345678 <= key && key <= 876543210) {
            return this.state[optimize(key)];
        }
        
        return false;
    }
    
    /**
     * Kriittinen optimointi: muuntaa annetun iteraation numeerisen muodon
     * pienempään kokoon. Koska annettu iteraatio aina vastaa numeerista
     * esitystä joukosta {0, 1, 2, 3, 4, 5, 6, 7, 8}, voidaan jättää aina
     * viimeinen luku esityksessä huomioimatta jakamalla luvun kymmenellä.
     * 
     * Todistus: koska alkiot joukossa ovat uniikkeja, saamme edelleen
     * kaikki permutaatiot esitettyä ilman viimeistä alkiota. Välin [0, 8]
     * muodostavat alkiot voidaan permutoida 9! kertaa. Tätä tietoa apuna
     * käyttäen vaihtoehdot permutaatiossa suppenevat loppua myöten, eli
     * 9! = 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1, jolloin huomataan viimeisen
     * alkion merkitys permutaatiossa. Koska luku kerrottuna yhdellä on yhtä
     * kuin luku itsessään, voidaan viimeinen laskutoimitus jättää väliin,
     * toisin sanoen voidaan jättää viimeinen alkio huomioimatta.
     * 
     * Tarkistus: Heapin algoritmia apuna käyttäen kaikkien permutaatioiden
     * esitys välin [0, 8] muodostaville alkioille tuottaa 362880 erilaista
     * permutaatiota eli tasan 9!:n verran. Samoin optimoitua esitysmuotoa
     * käyttäen päädytään jälleen 362880 eri permutaatioon. Optimointi on
     * toimiva ja sen ansiosta tilavaativuus on vain 1/10 alkuperäisestä eli
     * naiivista toteutuksesta.
     * 
     * Optimoinnilla tietorakenteen tilavaativuus saadaan siis kuriin ja
     * taatut O(1) aikavaativuuden toiminnot tasapainottavat suurta tila-
     * vaativuutta. Myös optimoinnin aikavaativuus on O(1).
     * 
     * @param key iteraatio numeerisessa muodossa
     * @return optimoitu esitysmuoto
     */
    public int optimize(int key) {
        return (int) Math.floor(key / 10) - 1;
    }
}
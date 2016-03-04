package com.yousif.slideme.struc;

/**
 * Oma toteutus minimikeosta, joka korvaa PriorityQueue-tietorakenteen.
 * 
 * @author Yousif Abdullah {@literal<yousif.abdullah@helsinki.fi>}
 * @param <State> State-tietueet vertailtavina solmuina
 */
public final class MinHeap<State extends Comparable<State>> {
    
    private State[] heap;
    private int size;
    
    /**
     * Alustaa minimikeon tyhjällä taulukolla. Oletusarvoisesti minimikeon
     * taulukon koko on 2:n alkion suuruinen, joka voidaan tarpeen mukaan
     * kasvattaa resize()-metodin avulla aina kaksinkertaiseksi.
     * 
     * @see com.yousif.slideme.struc.MinHeap#resize()
     */
    public MinHeap() {
        this.heap = (State[]) new Comparable[2];
        this.size = 0;
    }
    
    /**
     * Rakentaa minimikeon kekoehtoa noudattaen. Minimikeko rakennetaan eli
     * korjataan kekoehtoa noudattaen bubbleDown()-metodin avulla.
     * 
     * Vaikka algoritmi kutsuu bubbleDown()-metodia n / 2 kertaa eli läpi-
     * käytävien solmujen verran, on aikavaativuus pahimmassa tapauksessa
     * O(n), siitä huolimatta, että bubbleDown()-metodin oma aikavaativuus on
     * O(log n). Tarvittavien askeleiden määrä n-kokoisen keon korjaamiseen
     * voidaan esittää matemaattisesti N = summa kaikista i = 0 käy log(n),
     * jossa laskukaava on (n / 2 ^ (i + 1) * i). Koska algoritmi käy vain
     * n / 2 solmua läpi, voidaan yleisemmin muotoilla (n / 2) * 2 = n ja
     * n >= N, siis aikavaativuus on myös O(n) eikä O(n log n).
     * 
     * @see com.yousif.slideme.struc.MinHeap#bubbleDown(int)
     */
    private void construct() {
        for (int i = this.size / 2; i > 0; i--) {
            this.bubbleDown(i);
        }
    }
    
    /**
     * Suorittaa "heapify"-toiminnon kekoehdon täyttämiseksi annetusta
     * solmusta alaspäin. Toiminto siirtää käsiteltävän solmun omalle
     * paikalleen minimikeossa sen prioriteetin perusteella ja jatkaa
     * toimintaansa rekursiivisesti, kunnes kaikki solmut annetusta solmun
     * indeksistä täyttävät kekoehdon. Toiminnon aikavaativuus on O(log n).
     * 
     * @param index käsiteltävän solmun indeksi minimikeon taulukossa
     */
    private void bubbleDown(int index) {
        State current = this.heap[index];
        int child;
        
        while (index * 2 <= this.size) {
            child = index * 2;
            
            if (child != this.size) {
                if (this.heap[child].compareTo(this.heap[child + 1]) > 0) {
                    child++;
                }
            }
            
            if (current.compareTo(this.heap[child]) > 0) {
                this.heap[index] = this.heap[child];
            } else {
                break;
            }
            
            index = child;
        }
        
        this.heap[index] = current;
    }
    
    /**
     * Lisää annetun iteraation minimikekoon sen prioriteetin perusteella.
     * Toiminnon aikavaativuus on O(log n).
     * 
     * @param state lisättävä State-tietue
     */
    public void insert(State state) {
        // Mikäli minimikeon taulukko on liian pieni, kasvatetaan taulukkoa.
        if (this.size == this.heap.length - 1) {
            this.resize();
        }
        
        this.size++;
        int index = this.size;
        
        while (index > 1 && state.compareTo(this.heap[index / 2]) < 0) {
            this.heap[index] = this.heap[index / 2];
            index = index / 2;
        }
        
        this.heap[index] = state;
    }
    
    /**
     * Palauttaa sekä poistaa pienimmän prioriteetin solmun minimikeossa.
     * Toiminnon aikavaativuus on O(log n).
     * 
     * @return pienimmän prioriteetin State-tietue
     */
    public State retrieve() {
        if (this.size != 0) {
            State state = this.heap[1];
            
            this.heap[1] = this.heap[this.size];
            this.size--;
            
            this.bubbleDown(1);
            return state;
        }
        
        // Mikäli minimikeko on tyhjä, palautetaan null.
        return null;
    }
    
    /**
     * Tarkistaa, onko minimikeko tyhjä. Toiminnon aikavaativuus on O(1).
     * 
     * @return true, kun minimikeko on tyhjä ja muutoin false
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }
    
    /**
     * Kasvattaa minimikeon taulukon koon tarvittaessa. Koska minimikeon
     * käyttämän taulukon alkiot manuaalisesti kopioidaan uuteen taulukkoon,
     * on toiminnon aikavaativuus O(n).
     */
    private void resize() {
        // Luetaan muistiin nykyinen taulukko ja luodaan uusi taulukko.
        State[] current = this.heap;
        this.heap = (State[]) new Comparable[this.heap.length * 2];
        
        // Manuaalisesti kopioi nykyisen taulukon alkiot uuteen taulukkoon.
        for (int i = 0; i < this.size; i++) {
            this.heap[i + 1] = current[i + 1];
        }
    }
}
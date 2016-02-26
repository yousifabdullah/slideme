package com.yousif.slideme.struc;

/**
 * Oma toteutus minimikeosta, joka korvaa PriorityQueue-tietorakenteen.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 * @param <State> State-tietueet vertailtavina solmuina
 */
public final class MinHeap<State extends Comparable<State>> {
    
    private State[] heap;
    private int size;
    
    /**
     * Alustaa minimikeon tyhjällä taulukolla.
     */
    public MinHeap() {
        this.clear();
    }
    
    /**
     * Luo uuden minimikeon tyhjällä taulukolla.
     */
    public void clear() {
        this.heap = (State[]) new Comparable[2];
        this.size = 0;
    }
    
    /**
     * Tarkistaa, onko minimikeko tyhjä.
     * 
     * @return true, kun minimikeko on tyhjä ja muutoin false
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }
    
    /**
     * Rakentaa minimikeon kekoehtoa noudattaen.
     */
    private void buildHeap() {
        for (int i = this.size / 2; i > 0; i--) {
            percolatingDown(i);
        }
    }
    
    /**
     * Suorittaa "heapify"-toiminnon kekoehdon täyttämiseksi annetusta
     * solmusta alaspäin.
     * 
     * @param index käsiteltävän solmun indeksi minimikeon taulukossa
     */
    private void percolatingDown(int index) {
        State current = this.heap[index];
        int child;
        
        while (2 * index <= this.size) {
            child = 2 * index;
            
            if (child != this.size && this.heap[child].compareTo(this.heap[child + 1]) > 0) {
                child++;
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
     * Poistaa sekä palauttaa pienimmän prioriteetin solmun minimikeossa.
     * 
     * @return pienimmän prioriteetin State-tietue
     */
    public State poll() {
        if (this.size != 0) {
            State state = this.heap[1];
            
            this.heap[1] = this.heap[this.size];
            this.size--;
            
            percolatingDown(1);
            return state;
        }
        
        // Mikäli minimikeko on tyhjä, palautetaan null.
        return null;
    }
    
    /**
     * Lisää annetun iteraation minimikekoon sen prioriteetin perusteella.
     * 
     * @param state lisättävä State-tietue
     */
    public void add(State state) {
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
     * Kasvattaa minimikeon taulukon koon tarvittaessa.
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
package com.yousif.slideme.struc;

/**
 * Oma toteutus minimikeosta, joka korvaa PriorityQueue-tietorakenteen.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 * @param <State> State-tiet
 */
public final class MinHeap<State extends Comparable<State>> {
    
    private State[] heap;
    private int size;
    
    /**
     * Alustetaan minimikeko
     */
    public MinHeap() {
        clear();
    }
    
    /**
     * Tyhjentää minimikeon siis luo uuden
     */
    public void clear() {
        this.heap = (State[]) new Comparable[2];
        this.size = 0;
    }
    
    /**
     * Rakentaa keon
     */
    private void buildHeap() {
        for (int k = this.size / 2; k > 0; k--) {
            percolatingDown(k);
        }
    }
    
    /**
     * "heapify" alaspäin
     * 
     * @param k 
     */
    private void percolatingDown(int k) {
        State tmp = this.heap[k];
        int child;
        
        while(2*k <= this.size) {
            child = 2*k;
            
            if (child != this.size && this.heap[child].compareTo(this.heap[child + 1]) > 0) {
                child++;
            }
            
            if (tmp.compareTo(this.heap[child]) > 0) {
                this.heap[k] = this.heap[child];
            } else {
                break;
            }
            
            k = child;
        }
        
        this.heap[k] = tmp;
    }
    
    /**
     * Tarkistaa onko keko tyhjä
     * 
     * @return 
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }
    
    /**
     * Poistaa sekä palauttaa pienimmän arvon keossa (delmin)
     * 
     * @return 
     */
    public State poll() {
        if (this.size != 0) {
            State min = this.heap[1];
            this.heap[1] = this.heap[this.size];
            this.size--;
            percolatingDown(1);
            
            return min;
        }
        
        return null;
    }
    
    /**
     * Lisää iteraation kekoon
     * 
     * @param x 
     */
    public void add(State x) {
        if (this.size == this.heap.length - 1) {
            resize();
        }
        
        this.size++;
        int pos = this.size;
        
        while(pos > 1 && x.compareTo(this.heap[pos/2]) < 0) {
            this.heap[pos] = this.heap[pos/2];
            pos = pos/2;
        }
        
        this.heap[pos] = x;
    }
    
    
    /**
     * Kasvattaa keon käyttämän taulun kokoa
     */
    private void resize() {
        State [] old = this.heap;
        this.heap = (State[]) new Comparable[this.heap.length * 2];
        
        for (int i = 0; i < this.size; i++) {
            this.heap[i + 1] = old[i + 1];
        }
    }
}
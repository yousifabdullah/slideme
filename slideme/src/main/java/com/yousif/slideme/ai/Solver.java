package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;
import com.yousif.slideme.core.Board;
import com.yousif.slideme.struc.MinHeap;
import com.yousif.slideme.struc.UniqueSet;

/**
 * Tekoälyn toiminnallisuuden määrittävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class Solver {
    
    private final UniqueSet closed;
    private final MinHeap<State> queue;
    
    private final Board board;
    
    /**
     * Alustaa tekoälyn nykyisen pelitilanteen mukaisesti.
     * 
     * @param board nykyinen pelitilanne Board-tietueena
     */
    public Solver(Board board) {
        this.board = board;
        
        this.closed = new UniqueSet();
        this.queue = new MinHeap<>();
    }
    
    /**
     * Suorittaa hakualgoritmin ja palauttaa polun välivaiheista nykyisestä
     * pelitilanteesta ratkaisuun. Haku toteuttaa A*-algoritmin tai vaihto-
     * ehtoisesti heuristiikasta riippuen BFS-algoritmin. Seuraavan läpi-
     * käytävän iteraation priorisointi tapahtuu minimikeon avulla ja jo
     * käydyt iteraatiot aina yliviivataan, jottei algoritmi tee ylimääräistä
     * työtä.
     * 
     * Hakualgoritmin aikavaativuus pahimmassa tapauksessa on O(|E| + |V|).
     * 
     * @return välivaiheiden polku lähtötilanteesta ratkaisuun
     */
    public int[] findPath() {
        
        // Alustetaan minimikeko ja lisätään nykyinen iteraatio juureen.
        queue.clear();
        queue.add(new State(this.board.getCurrentState()));
        
        /*
        Suoritetaan haku niin kauan, kunnes minimikeossa ei enää ole
        läpikäytäviä iteraatioita.
        */
        while (!queue.isEmpty()) {
            
            // Luetaan minimikeosta iteraatio, jonka prioriteetti on pienin.
            State state = queue.poll();
            
            // Jos iteraatio vastaa ratkaisua, loppuu algoritmin toiminta heti.
            if (Array.matches(state.getCurrentIteration(), this.board.getSolution())) {
                return this.tracePath(state);
            }
            
            // Merkataan eli yliviivataan tarkasteltava iteraatio käsitellyksi.
            closed.strike(state.getCurrentAsInteger());
            
            /*
            Lisätään minimikekoon enimmillään neljä uutta iteraatiota vapaa-
            ruudun sijoittelun mukaisesti eli ylös, alas, vasemmalle ja
            oikealle.
            */
            for (int i = 0; i < 4; i++) {
                State successor = state.getNextState(i);
                
                /*
                Huom. aluksi tarkistetaan, että iteraatio ei ole tyhjä tai
                yliviivattu.
                */
                if (successor != null && !closed.check(successor.getCurrentAsInteger())) {
                    queue.add(successor);
                }
            }
        }
        
        // Mikäli polkua ei löydy, palautetaan tyhjä taulukko.
        return new int[0];
    }
    
    /**
     * Käy läpi hakualgoritmin toteuttaman polun ja palauttaa sen käänteisessä
     * järjestyksessä. Koska hakualgoritmin toiminta päätyy löytäessään
     * ratkaisun, on ketjun järjestys käytävä läpi käänteisesti, jotta väli-
     * vaiheet voi simuloida lähtötilanteesta ratkaisuun.
     * 
     * @param state iteraatio, joka vastaa ratkaisua
     * @return hakualgoritmin polku käänteisessä järjestyksessä
     */
    private int[] tracePath(State state) {
        // Alustetaan muistiin taulukko välivaiheiden polulle
        int[] path = new int[state.getDistance() + 1];
        int iteration = path.length - 1;
        
        /*
        Mikäli lähtötilanteeseen ei ole vielä päädytty, kirjataan ylös
        välivaihe ja peruutetaan yksi askel ketjussa.
        */
        while (state != null) {
            path[iteration] = state.getIndexOfZero();
            iteration--;
            
            state = state.getPreviousState();
        }
        
        return path;
    }
    
    /**
     * Hakualgoritmin heuristinen funktio, joka palauttaa maksimin kaikkien
     * peliruutujen Manhattan-etäisyydestä.
     * 
     * @param iteration iteraatio peliruuduista int[]-taulukkona
     * @return annetun iteraation heuristinen arvo
     */
    static int heuristic(int[] iteration) {
        int heuristic = 0;
        
        for (int i = 0; i < iteration.length; i++) {
            // Huom. 0 merkitsee vapaaruutua, ei lukua 0!
            if (iteration[i] != 0) {
                heuristic = Math.max(heuristic, manhattanDistance(i, iteration[i]));
            }
        }
        
        return heuristic;
    }
    
    /**
     * Aputoiminto heuristiselle funktiolle, joka laskee Manhattan-etäisyyden
     * annettujen indeksien välillä oleville peliruuduille.
     * 
     * @param a ensimmäinen tarkasteltava indeksi
     * @param b toinen tarkasteltava indeksi
     * @return Manhattan-etäisyys indeksien välillä oleville peliruuduille
     */
    static int manhattanDistance(int a, int b) {
        return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
    }
}
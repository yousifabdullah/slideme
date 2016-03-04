package com.yousif.slideme.ai;

import com.yousif.slideme.core.Array;
import com.yousif.slideme.core.Board;
import com.yousif.slideme.struc.MinHeap;
import com.yousif.slideme.struc.UniqueSet;

/**
 * Tekoälyn toiminnallisuuden määrittävä luokka.
 * 
 * @author Yousif Abdullah {@literal<yousif.abdullah@helsinki.fi>}
 */
public class Solver {
    
    private final Board board;
    
    private MinHeap<State> queue;
    private UniqueSet closed;
    
    // Huom. tämä vakiomuuttuja vaikuttaa heuristiseen funktioon.
    private static final boolean USE_BESTFIRST = false;
    
    /**
     * Alustaa tekoälyn nykyisen pelitilanteen mukaisesti.
     * 
     * @param board nykyinen pelitilanne Board-tietueena
     */
    public Solver(Board board) {
        this.board = board;
    }
    
    /**
     * Suorittaa hakualgoritmin ja palauttaa polun välivaiheista nykyisestä
     * pelitilanteesta ratkaisuun. Haku toteuttaa A*-algoritmin tai vaihto-
     * ehtoisesti heuristiikasta riippuen BFS-algoritmin. Seuraavan läpi-
     * käytävän iteraation priorisointi tapahtuu minimikeon avulla ja jo
     * käydyt iteraatiot aina merkataan, jottei algoritmi tee ylimääräistä
     * työtä.
     * 
     * Hakualgoritmin aikavaativuus pahimmassa tapauksessa on O(|V| + |E|)
     * eli se käy tarvittaessa kaikki solmut ja kaaret läpi. Huom. O(|E|) voi
     * vaihdella O(1) ja O(|V|^2) välillä.
     * 
     * @return välivaiheiden polku lähtötilanteesta ratkaisuun
     */
    public int[] findPath() {
        /*
        Alustetaan hakualgoritmin käyttämät tietorakenteet ja lisätään
        nykyinen iteraatio minimikeon juureen.
        */
        this.queue = new MinHeap<>();
        this.closed = new UniqueSet();
        
        this.queue.insert(new State(this.board.getCurrentState())); 
        
        /*
        Suoritetaan haku niin kauan, kunnes minimikeossa ei enää ole
        läpikäytäviä iteraatioita.
        */
        while (!this.queue.isEmpty()) {
            // Luetaan minimikeosta iteraatio, jonka prioriteetti on pienin.
            State state = this.queue.retrieve();
            
            // Jos iteraatio vastaa ratkaisua, loppuu algoritmin toiminta heti.
            if (Array.matches(state.getCurrentIteration(), this.board.getSolution())) {
                return this.tracePath(state);
            }
            
            // Merkataan eli yliviivataan tarkasteltava iteraatio käsitellyksi.
            this.closed.strike(state.getCurrentAsInteger());
            
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
                if (successor != null) {
                    if (!this.closed.check(successor.getCurrentAsInteger())) {
                        this.queue.insert(successor);
                    }
                }
            }
        }
        
        // Mikäli polkua ei löydy, palautetaan tyhjä polku.
        return new int[0];
    }
    
    /**
     * Käy läpi hakualgoritmin toteuttaman polun ja palauttaa sen käänteisessä
     * järjestyksessä. Koska hakualgoritmin toiminta päätyy löytäessään
     * ratkaisun, on ketjun järjestys käytävä läpi käänteisesti, jotta väli-
     * vaiheet voi simuloida lähtötilanteesta ratkaisuun. Toiminnon aika-
     * vaativuus on O(n).
     * 
     * @param state iteraatio, joka vastaa ratkaisua
     * @return hakualgoritmin polku käänteisessä järjestyksessä
     */
    private int[] tracePath(State state) {
        // Alustetaan muistiin taulukko välivaiheiden polulle.
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
     * peliruutujen Manhattan-etäisyydestä. Luokan vakiomuuttujasta riippuen
     * voidaan toteuttaa BFS-algoritmi, jolloin heuristinen funktio palauttaa
     * aina arvon 0. Heuristisen funktion aikavaativuus pahimmassa tapauksessa
     * on O(n), paitsi BFS-algoritmin toteuttavassa muodossa, jolloin se on
     * O(1).
     * 
     * Tässä erikoistapauksessa hakualgoritmi käyttäytyy samalla tavalla,
     * kuin esim. Dijkstran algoritmi, koska solmuja ei erikseen priorisoida
     * arvioidun etäisyyden perusteella. Tämän lisäksi 8-peli toteuttaa oman-
     * laisen erikoistapauksensa, jossa kaarten etäisyys on aina 1. Toisin
     * sanoen, hakualgoritmi noudattaa best-first -menetelmää solmujen läpi-
     * käyntiin, siis BFS-algoritmin mukaisesti. Tämä pitää paikkansa, koska
     * vapaaruutu siirtyy tekoälyssä vain yhden ruudun kerrallaan: jokaisessa
     * iteraatiossa hakualgoritmi kulkee vain yhtä kaarta pitkin.
     * 
     * @param iteration iteraatio peliruuduista int[]-taulukkona
     * @see com.yousif.slideme.ai.Solver#manhattanDistance(int, int)
     * @return annetun iteraation heuristinen arvo
     */
    public static int heuristic(int[] iteration) {
        int heuristic = 0;
        
        /*
        Mikäli luokan vakiomuuttujassa on määritelty BFS-algoritmin käyttö,
        palautetaan heuristiikaksi aina arvo 0.
        */
        if (Solver.USE_BESTFIRST) {
            return heuristic;
        }
        
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
     * annettujen indeksien välillä oleville peliruuduille. Toiminnon aika-
     * vaativuus on O(1).
     * 
     * @param a ensimmäinen tarkasteltava indeksi
     * @param b toinen tarkasteltava indeksi
     * @return Manhattan-etäisyys indeksien välillä oleville peliruuduille
     */
    private static int manhattanDistance(int a, int b) {
        return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
    }
}
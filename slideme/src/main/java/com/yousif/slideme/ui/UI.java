package com.yousif.slideme.ui;

import com.yousif.slideme.core.Array;
import com.yousif.slideme.core.Board;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Graafisen käyttöliittymän määrittävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class UI implements Runnable, ActionListener {
    
    private JFrame frame;
    private final String appName;
    
    private final Board board;
    private final JButton[] tiles;
    
    private boolean simulating;
    
    /**
     * Alustaa käyttöliittymän uudella pelitilanteella.
     * 
     * @param board uusi pelitilanne Board-tietueena
     * @param appName sovelluksen nimi käyttöliittymässä
     */
    public UI(Board board, String appName) {
        this.appName = appName;
        
        this.board = board;
        this.tiles = new JButton[9];
        
        // Määritetään aluksi, että simulaatiota ei ole vielä käynnistetty.
        this.simulating = false;
    }
    
    /**
     * Suorittaa tarvittavat komennot käyttöliittymän aktivoimiseksi.
     */
    @Override
    public void run() {
        // Määritetään käyttöliittymän yleisilme ja -toiminnallisuus.
        this.frame = new JFrame(this.appName);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        /*
        Asetetaan käyttöliittymän ilmeeksi käytössä olevan käyttö-
        järjestelmän (esim. Windows / OS X / Linux) oletusilme.
        */
        setLookAndFeel: try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            // Huom. poikkeukseen ei reagoida, jos ilmettä ei voi muuttaa.
            break setLookAndFeel;
        }
        
        Container container = this.frame.getContentPane();
        
        container.setPreferredSize(new Dimension(240, 270));
        container.setLayout(new BorderLayout());
        
        // Luodaan itse käyttöliittymä, siis visuaaliset komponentit.
        this.buildComponents(container);
        this.refreshTiles();
        
        // Sovitetaan ikkuna näytölle ja asetetaan se näkyväksi.
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        
        this.frame.setVisible(true);
    }
    
    /**
     * Luo käyttöliittymän komponentit eli sisällön.
     * 
     * @param container komponenttisäiliö, joka pitää sisällön
     */
    private void buildComponents(Container container) {
        /*
        Luodaan peliruuduille pohja, joka käyttää 3*3-ruudun suuruista
        alustaa komponenttien (tässä tapauksessa peliruutujen) asetteluun.
        */
        JPanel top = new JPanel();
        
        top.setBorder(new EmptyBorder(10, 10, 10, 10));
        top.setPreferredSize(new Dimension(240, 240));
        
        top.setLayout(new GridLayout(3, 3, 5, 5));
        
        /*
        Koska peliruutuja käydään läpi kuin taulukkoa, voidaan myös
        tapahtumat (eli hiiren osoitus) käsitellä yksinkertaisesti.
        */
        for (int i = 0; i < 9; i++) {
            this.tiles[i] = new JButton();
            this.tiles[i].setFocusable(false);
            
            // Huom. jokaisen peliruudun "indeksi" on sen tapahtumakomento.
            this.tiles[i].setActionCommand(String.valueOf(i));
            this.tiles[i].addActionListener(this);
            
            top.add(this.tiles[i]);
        }
        
        container.add(top, BorderLayout.CENTER);
        
        // Luodaan muille toiminnallisuuksille erillinen pohja.
        JPanel bottom = new JPanel();
        
        bottom.setBorder(new EmptyBorder(0, 10, 10, 10));
        bottom.setPreferredSize(new Dimension(240, 30));
        
        bottom.setLayout(new BorderLayout(3, 0));
        
        // Lisätään "Slide me!" -painike ja sen tapahtumakomento.
        JButton slideme = new JButton("Slide me!");
        slideme.setFocusable(false);
        
        slideme.setActionCommand("slideme");
        slideme.addActionListener(this);
        
        bottom.add(slideme, BorderLayout.CENTER);
        
        // Lisätään "Shuffle" -painike ja sen tapahtumakomento.
        JButton shuffle = new JButton("Shuffle");
        shuffle.setFocusable(false);
        
        shuffle.setActionCommand("shuffle");
        shuffle.addActionListener(this);
        
        bottom.add(shuffle, BorderLayout.LINE_END);
        
        container.add(bottom, BorderLayout.PAGE_END);
    }
    
    /**
     * Päivittää peliruutujen arvot nykyisen pelitilanteen mukaisesti.
     */
    void refreshTiles() {
        // Luetaan muistiin nykyinen pelitilanne.
        int[] state = this.board.getCurrentState();
        
        for (int i = 0; i < 9; i++) {
            // Päivitetään peliruutujen arvot.
            this.tiles[i].setText(String.valueOf(state[i]));
            
            /*
            Mikäli peliruudun arvo on 0, eli on vapaaruutu, asetetaan se
            näkymättömäksi. Muussa tapauksessa pidetään peliruutu näkyvillä.
            */
            if (state[i] == 0) {
                this.tiles[i].setVisible(false);
            } else {
                this.tiles[i].setVisible(true);
            }
        }
        
        // Päivityksen yhteydessä tarkistetaan, onko peli ratkaistu.
        if (Array.matches(this.board.getCurrentState(), this.board.getSolution())) {
            // Odotetaan 0,1s (kosmeettinen) viive ennen ilmoitusikkunaa.
            delay: try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break delay;
            }
            
            JOptionPane.showMessageDialog(this.frame,
                    "Mahtavaa, peliruudut ovat taas järjestyksessä!\n"
                            + "(Siirtoja: " + this.board.getMovesCount() + ")",
                    this.appName, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Luo uuden pelitilanteen sekoittamalla peliruudut satunnaiseen
     * järjestykseen ja päivittää peliruudut käyttöliittymässä.
     */
    void shuffleTiles() {
        this.board.shuffleOrder();
        this.refreshTiles();
    }
    
    /**
     * Siirtää annetun indeksin kohdalla sijaitsevan peliruudun vapaaruudun
     * tilalle, mikäli se on mahdollista ja päivittää peliruudut käyttö-
     * liittymässä.
     * 
     * @param from siirrettävän peliruudun indeksi
     * @return true, kun siirto on onnistunut ja muutoin false
     */
    void moveTile(int from) {
        if (this.board.moveTile(from)) {
            this.refreshTiles();
        }
    }
    
    /**
     * Palauttaa käyttöliittymän käyttämän Board-tietueen.
     * 
     * @return nykyinen Board-tietue
     */
    Board getBoardInstance() {
        return this.board;
    }
    
    /**
     * Vapauttaa käyttäjän syötteelle asetetun lukon, sallien tapahtuma-
     * komentojen käsittelyn käyttöliittymässä.
     */
    void releaseUILock(Object source) {
        JButton trigger = (JButton) source;
        trigger.setEnabled(true);
        
        this.simulating = false;
    }
    
    /**
     * Käsittelee tapahtumat (eli hiiren osoitukset) tapahtumakomentojen
     * avulla.
     * 
     * @param event tapahtuma ActionEvent-tietueena
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Kun simulaatio on käynnissä, tapahtumakomentoja ei huomioida.
        if (this.simulating) {
            return;
        }
        
        String action = event.getActionCommand();
        
        /*
        Mikäli tapahtumakomento on numero, eli yksi peliruuduista
        on osoitettu hiirellä, välitetään tieto pelitilannetta
        ylläpitävälle luokalle. Muutoin käydään läpi eri vaihtoehdot
        manuaalisesti.
        */
        try {
            int from = Integer.parseInt(action);
            this.moveTile(from);
        } catch (NumberFormatException exception) {
            switch (action) {
                case "slideme":
                    // Käynnistetään tekoälyn simulaatio.
                    new Simulation(this, event.getSource()).run();
                    
                    /*
                    Lukitaan käyttöliittymä simulaation ajaksi käyttäjän
                    syötteiltä.
                    */
                    JButton trigger = (JButton) event.getSource();
                    trigger.setEnabled(false);
                    
                    this.simulating = true;
                    
                    break;
                case "shuffle":
                    // Luodaan uusi pelitilanne sekoittamalla peliruudut.
                    this.shuffleTiles();
                    
                    break;
            }
        }
    }
}
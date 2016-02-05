package com.yousif.slideme.ui;

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
    
    // Alustetaan käyttöliittymälle ikkuna ja pelitilanne.
    private JFrame frame;
    
    private final Board board;
    private final JButton[] tiles;
    
    public UI(Board board) {
        this.board = board;
        this.tiles = new JButton[9];
    }
    
    /**
     * Suorittaa tarvittavat komennot käyttöliittymän aktivoimiseksi.
     */
    @Override
    public void run() {
        // Määritetään käyttöliittymän yleisilme ja -toiminnallisuus.
        this.frame = new JFrame("slideme");
        
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        
        /*
        Asettaa käyttöliittymän ilmeeksi käytössä olevan käyttö-
        järjestelmän (esim. Windows / OS X / Linux) oletusilmeen.
        */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            // Huom. poikkeukseen ei reagoida, jos ilmettä ei voi muuttaa.
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
        Luodaan peliruuduille pohja, joka käyttää 3*3-suuruista ruudukkoa
        komponenttien (tässä tapauksessa peliruutujen) asetteluun.
        */
        JPanel top = new JPanel();
        
        top.setBorder(new EmptyBorder(4, 4, 4, 4));
        top.setPreferredSize(new Dimension(240, 240));
        
        top.setLayout(new GridLayout(3, 3, 4, 4));
        
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
        
        container.add(top, BorderLayout.PAGE_START);
        
        // Luodaan muille toiminnallisuuksille erillinen pohja.
        JPanel bottom = new JPanel();
        
        bottom.setBorder(new EmptyBorder(0, 4, 4, 4));
        bottom.setPreferredSize(new Dimension(240, 30));
        
        bottom.setLayout(new BorderLayout(2, 0));
        
        // "Slide me!" -painike ja sen tapahtumakomennon lisäys.
        JButton slideme = new JButton("Slide me!");
        slideme.setFocusable(false);
        
        slideme.setActionCommand("slideme");
        slideme.addActionListener(this);
        
        bottom.add(slideme, BorderLayout.CENTER);
        
        // "Shuffle" -painike ja sen tapahtumakomennon lisäys.
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
    private void refreshTiles() {
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
    }
    
    /**
     * Käsittelee tapahtumat (eli hiiren osoitukset) tapahtumakomentojen
     * avulla.
     * 
     * @param event tapahtuma ActionEvent-tietueena
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        
        /*
        Mikäli tapahtumakomento on numero, eli yksi peliruuduista
        on osoitettu hiirellä, välitetään tieto pelitilannetta
        ylläpitävälle luokalle. Muutoin käydään läpi eri vaihtoehdot
        manuaalisesti.
        */
        try {
            int index = Integer.parseInt(command);
            this.board.moveTile(index);
        } catch (NumberFormatException exception) {
            switch (command) {
                case "slideme":
                    // Käynnistää tekoälyn simulaation.
                    JOptionPane.showMessageDialog(this.frame, "Can't slide yet!");
                    break;
                case "shuffle":
                    // Luo uuden pelitilanteen sekoittamalla peliruudut.
                    this.board.shuffleOrder();
                    break;
            }
        }
        
        // Päivitetään peliruudut jokaisen tapahtuman jälkeen.
        this.refreshTiles();
        
        // Tarkistetaan, onko peli ratkaistussa tilassa.
        if (this.board.foundSolution()) {
            JOptionPane.showMessageDialog(this.frame, "Mahtavaa, peliruudut ovat taas järjestyksessä!");
        }
    }
}
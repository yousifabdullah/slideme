package com.yousif.slideme.app;

import com.yousif.slideme.core.Board;
import com.yousif.slideme.ui.UI;

import javax.swing.SwingUtilities;

/**
 * slideme:n main-luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class Main {
    
    /**
     * Käynnistää pelin aloittaen UI-luokan käyttöliittymästä.
     * 
     * @param args komentorivillä annettu syöte (ei käytössä)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new UI(new Board(), "slideme v1.1"));
    }
}
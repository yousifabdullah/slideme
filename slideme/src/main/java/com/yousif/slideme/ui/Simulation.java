package com.yousif.slideme.ui;

import com.yousif.slideme.ai.Solver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Tekoälyn simulaation määrittävä luokka.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
public class Simulation extends TimerTask {
    
    private final UI ui;
    private final Object trigger;
    private final Timer timer;
    
    private int[] path;
    private int step;
    
    private boolean running;
    
    /**
     * Alustaa simulaatiota varten tarvittavan käyttöliittymän viitteen
     * sekä Timer-olion, joka simuloi välivaiheet.
     * 
     * @param ui käsiteltävä käyttöliittymä UI-tietueena
     * @param trigger simulaation käynnistävä komponentti
     */
    public Simulation(UI ui, Object trigger) {
        this.ui = ui;
        this.trigger = trigger;
        this.timer = new Timer();
        
        this.path = new int[0];
        this.step = 0;
        
        // Määritetään aluksi, että simulaatio ei ole vielä käynnissä.
        this.running = false;
    }
    
    /**
     * Käynnistää tekoälyn simulaation.
     */
    @Override
    public void run() {
        /*
        Mikäli simulaatio ei ole vielä käynnissä, alustetaan hakualgoritmi
        eli tekoäly ja käynnistetään Timer-olio.
        */
        if (!this.running) {
            this.running = true;
            
            /*
            Alustetaan tekoäly käyttöliittymän käyttämän Board-tietueen
            avulla.
            */
            Solver solver = new Solver(this.ui.getBoardInstance());
            this.path = solver.findPath();
            
            // Määritetään Timer-olio simuloimaan välivaiheet 0,5s välein.
            this.timer.schedule(this, 0, 5);
        } else {
            /*
            Simuloidaan yksi askel kerrallaan käyttöliittymässä, kunnes
            päädytään viimeiseen välivaiheeseen eli ratkaisuun.
            */
            if (this.step < this.path.length) {
                this.ui.moveTile(this.path[this.step]);
                this.step++;
            } else {
                /*
                Mikäli viimeinen välivaihe on simuloitu, puretaan muistista
                Timer-olio ja perutaan mahdolliset jonossa olevat kutsut sekä
                vapautetaan käyttäjän syötteen lukko käyttöliittymässä.
                */
                timer.cancel();
                timer.purge();
                
                this.ui.releaseUILock(this.trigger);
            }
        }
    }
}
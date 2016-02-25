package com.yousif.slideme.ui;

import com.yousif.slideme.ai.Solver;
import com.yousif.slideme.core.Board;

import java.util.Timer;
import java.util.TimerTask;

public class Simulation extends TimerTask {
    
    private final UI ui;
    private final Board board;
    
    private final Timer timer;
    
    private int[] path;
    private int step;
    
    private boolean running;
    
    public Simulation(UI ui, Board board) {
        this.ui = ui;
        this.board = board;
        
        this.timer = new Timer();
        
        this.path = new int[0];
        this.step = 0;
        
        this.running = false;
    }
    
    @Override
    public void run() {
        if (this.running == false) {
            this.running = true;
            
            Solver solver = new Solver(this.board);
            this.path = solver.findPath();
            
            this.timer.schedule(this, 0, 500);
            return;
        }
        
        if (this.step != this.path.length) {
            System.out.println(this.step + ". askel");
            this.ui.moveTile(this.path[this.step]);
            this.step++;
        } else {
            System.out.println("loppu");
            timer.cancel();
            timer.purge();
        }
    }
}
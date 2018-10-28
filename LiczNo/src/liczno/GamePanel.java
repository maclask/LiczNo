/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno;

import liczno.GameStates.GameStateManager;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Maciek
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{
   
    public static final int width = 800;
    public static final int height = 600;
   
    private GameStateManager gsm;
    
    private Thread thread;
    private boolean isRunning = false;
    
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
            
    public GamePanel(){
        setPreferredSize(new Dimension(width, height));
        start();
    }
    
    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run(){
        long start, elapsed, wait;
        gsm = new GameStateManager();
        while(isRunning){
            start = System.nanoTime();
            
            tick();
            repaint();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed /1000;
            
            if(wait <= 0 )
                wait = 5;
            
            try{
            Thread.sleep(wait);}
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void tick(){
        System.out.println("running");
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        gsm.draw(g);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());

    }
        
    @Override
    public void keyTyped(KeyEvent e) {}
    
} 
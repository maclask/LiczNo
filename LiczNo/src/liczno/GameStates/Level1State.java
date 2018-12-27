/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import liczno.Audio;
import liczno.GamePanel;
import liczno.Images;
import liczno.enterties.Block;
import liczno.enterties.Bombs;
import liczno.enterties.Player;
import liczno.mapping.Map;


/**
 *
 * @author Maciek
 */
public class Level1State extends GameState{
    
    public Level1State(GameStateManager gsm){
        super(gsm);
        //init(); 

    }

    private Player player;
    private Map map;
    private Bombs bombs;
    private int time=60;
    private long lasttime=System.nanoTime();
    private MathTaskState task;
    private Audio a;
    private int bombamount;
    
    @Override
    public void init() {
       bombamount = 2;
       player = new Player();
       map = new Map("map1");
       bombs = new Bombs(bombamount,map.getBlocks(),player);
        try {
            a = new Audio();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Level1State.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            a.playMain();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(Level1State.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

    @Override
    public void tick() {
        player.tick(map.getBlocks(), bombs.getBombs());
        
        if(Player.bombTouched)
        {
           do {                
           task=new MathTaskState(gsm);
           a.stopMain();
           gsm.states.push(task);
           }
           while(task == null);
        }
        if(Player.solved) {
            task=null;
            bombamount--;
            Player.solved=false;

        }
        if(System.nanoTime()-lasttime>=1000000000)
        {
            time--;
            lasttime=System.nanoTime();
        }
        if(time<0)gsm.states.add(new MenuState(gsm));
        if(bombamount == 0){
            Player.score += time;
            bombamount--; //to stop adding time
            gsm.states.add(new EndGameState(gsm));
        }
    }

    @Override
    public void draw(Graphics g) {
      
        g.drawImage(Images.bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

       map.draw(g);
       bombs.draw(g);
       player.draw(g);
       
       Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font f2 = Images.f.deriveFont(40F);
        g2d.setFont(f2);
        g2d.setColor(Color.WHITE);
       g2d.drawString(String.valueOf(time), 100, 100);
       g2d.drawString(String.valueOf(Player.score), 950, 100);
       
    }

    @Override
    public void keyPressed(int k) {
        player.keyPressed(k);
        if(k==KeyEvent.VK_T)bombs = new Bombs(bombamount,map.getBlocks(),player);
    }

    @Override
    public void keyReleased(int k) {
        player.keyReleased(k);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

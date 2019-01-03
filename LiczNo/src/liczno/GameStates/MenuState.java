/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 *
 * @author Maciek
 */
public class MenuState extends GameState{

    private final String[] options = {"ROZPOCZNIJ GRĘ", "NAJLEPSZE WYNIKI", "POMOC", "WYJDŹ"};
    private int currentSel = 0;
    private Button topbox;
    private Point click, hover;
    private Button[] buttons;
    
    
    public MenuState(GameStateManager gsm){
        super(gsm);
        buttons = new Button[options.length];
        for(int i=0; i<options.length; i++){
        buttons[i] = new Button(GamePanel.WIDTH/2, GamePanel.HEIGHT/2+80*i, 
                GamePanel.WIDTH/3,GamePanel.HEIGHT/12, new Color(0,0,0,0),Color.WHITE,
                50, options[i]);
       }
        
        topbox = new Button(GamePanel.WIDTH/4, GamePanel.HEIGHT/7, 
                GamePanel.WIDTH/2,GamePanel.HEIGHT/4, 150, "MENU");

        
    }
    
    @Override
    public void init() {
       Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
       Main.audio.playLevelMp3(5);
    }

    @Override
    public void tick() {
       
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


       g2d.drawImage(Images.bg, 0, 0, Images.bgWidth, Images.bgHeight, null);
       g2d.drawImage(Images.logo, GamePanel.WIDTH / 2 - 400, 350, 300, 300, null);
       
       topbox.drawButton(g);
       

       for(int i=0; i<options.length; i++){
           if(i==currentSel){
               buttons[i].txtColor = Color.WHITE;
               buttons[i].bgColor = new Color(255,213,109);
           }
           else{
               buttons[i].txtColor = new Color(255,213,109);
               buttons[i].bgColor = new Color(0,0,0,0);
           }
           buttons[i].drawButton(g);
       }
    }

    @Override
    public void keyPressed(int k) {
       if(k ==KeyEvent.VK_DOWN){
           currentSel++;
           if(currentSel >= options.length){
               currentSel =0;
           }
       }
       else if(k == KeyEvent.VK_UP){
           currentSel--;
           if(currentSel <0){
               currentSel = options.length - 1;
           }
       }
       
       if(k == KeyEvent.VK_ENTER){
           
           switch (currentSel) {
           //start
               case 0:
                   Main.audio.playLevelMp3a();
                   gsm.states.push(new EndGameState(gsm,0,Player.isDead));
                   break;
                   //help
               case 1:
                    Main.audio.playLevelMp3a();
                   gsm.states.push(new ShowScoreState(gsm));
                   break;  
           //help
               case 2:
                    Main.audio.playLevelMp3a();
                   //gsm.states.push(new ShowScoreState(gsm));
                   break;               
           //exit
               case 3:
                   System.exit(0);
                   break;
               default:
                   break;
           }
           
       }
    }

    @Override
    public void keyReleased(int k) {
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = new Point(e.getX(), e.getY());
        checkClick();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hover = new Point(e.getX(), e.getY());
        for(int i=0; i<options.length; i++)
        if(buttons[i].contains(hover)) {
            currentSel=i;
            Main.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
         // else 
            //Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
    }

    private void checkClick(){
        if(buttons[0].contains(click)){
            gsm.states.push(new EndGameState(gsm,0,Player.isDead));
        }
        else if(buttons[1].contains(click)){
            
        }
        else if(buttons[2].contains(click)){
            System.exit(0);
        }
    }
}

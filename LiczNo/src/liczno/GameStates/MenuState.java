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

    private final String[] options = {"ROZPOCZNIJ GRĘ", "NAJLEPSZE WYNIKI","Ustawienia", "POMOC", "o grze","WYJDŹ"};
    private int currentSel = 0;
    private Button topbox;
    private Point click, hover;
    private Button[] buttons;
    
    
    public MenuState(GameStateManager gsm){
        super(gsm);
        buttons = new Button[options.length];
        for(int i=0; i<options.length; i++){
        buttons[i] = new Button(GamePanel.WIDTH/2, GamePanel.HEIGHT/9*3+80*i, 
                GamePanel.WIDTH/3,GamePanel.HEIGHT/12, new Color(0,0,0,0),Color.WHITE,
                50, options[i], true);
       }
        
        topbox = new Button(GamePanel.WIDTH/4, GamePanel.HEIGHT/10, 
                GamePanel.WIDTH/2,GamePanel.HEIGHT/6, 150, "MENU");

        
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
       g2d.drawImage(Images.logo, GamePanel.WIDTH / 9, GamePanel.HEIGHT/5*2, 300, 300, null);
       
       topbox.drawButton(g);
       

       for(int i=0; i<options.length; i++){
           if(i==currentSel){
               buttons[i].txtColor = Color.WHITE;
               buttons[i].bgColor = new Color(243,192,38);
           }
           else{
               buttons[i].txtColor = new Color(243,192,38);
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
           checkClick(currentSel);
           
           
       }
    }

    @Override
    public void keyReleased(int k) {
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = new Point(e.getX(), e.getY());
        checkClick(currentSel);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hover = new Point(e.getX(), e.getY());
        boolean hand = false;
        for(int i=0; i<options.length; i++)
        if(buttons[i].contains(hover)) {
            currentSel=i;
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                hand = true;
                break;
        }
        if(!hand)
        Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        
    }

    private void checkClick(int currentSel){
     
        switch (currentSel) {
           //start
               case 0:
                   Main.audio.playLevelMp3a();
                   gsm.states.push(new EndGameState(gsm,0,Player.isDead));
                   break;
                   //help
               case 1:
                   // Main.audio.playLevelMp3a();
                   gsm.states.push(new ShowScoreState(gsm));
                   break;  
           //help
               case 2:
                   // Main.audio.playLevelMp3a();
                   gsm.states.push(new SettingsState(gsm));
                   break; 
               
               case 3:
                   // Main.audio.playLevelMp3a();
                   //gsm.states.push(new ShowScoreState(gsm));
                   break;               
           //exit
                case 4:
                   
                   break;
               case 5:
                   System.exit(0);
                   break;
               default:
                   break;
           }
    }
}

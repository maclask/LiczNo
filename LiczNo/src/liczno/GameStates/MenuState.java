/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 *
 * @author Maciek
 */
public class MenuState extends GameState{

    private String[] options = {"ROZPOCZNIJ GRĘ", "POMOC", "WYJDŹ"};
    private int currentSel = 0;
    private Font f2;
    private Rectangle topbox;
    private Rectangle[] optionsbox;
    private Point click;
    
    
    public MenuState(GameStateManager gsm){
        super(gsm);
       init();
  
    }
    
    @Override
    public void init() {
       Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
       optionsbox = new Rectangle[3];
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
        f2 = Images.f.deriveFont(150F);
        g2d.setFont(f2);
        g2d.setColor(Color.WHITE);
       g2d.drawImage(Images.bg, 0, 0, Images.bgWidth, Images.bgHeight, null);
       g2d.drawImage(Images.logo, GamePanel.WIDTH / 2 - 400, 350, 300, 300, null);
       
       topbox = new Rectangle(GamePanel.WIDTH/4, GamePanel.HEIGHT/7, GamePanel.WIDTH/2,GamePanel.HEIGHT/4);
       drawCenteredString(g,  "MENU", topbox, f2);
       
       
       
       f2 = f2.deriveFont(50F);
       for(int i=0; i<options.length; i++){
           if(i==currentSel){
               g2d.setColor(new Color(255,213,109));
           }
           else{
               g2d.setColor(Color.WHITE);
           }
           optionsbox[i] = new Rectangle(GamePanel.WIDTH/3*2, GamePanel.HEIGHT/2+50*i, GamePanel.WIDTH/6,GamePanel.HEIGHT/12);
           drawCenteredString(g,  options[i], optionsbox[i], f2);
          // g2d.drawString(options[i], GamePanel.WIDTH / 2, 400 + i *100);
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
                   gsm.states.push(new EndGameState(gsm,0,Player.isDead));
                   break;
           //help
               case 1:
                   break;               
           //exit
               case 2:
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
    }
   
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
    private void checkClick(){
        if(optionsbox[0].contains(click)){
            gsm.states.push(new EndGameState(gsm,0,Player.isDead));
        }
        else if(optionsbox[1].contains(click)){
            
        }
        else if(optionsbox[2].contains(click)){
            System.exit(0);
        }
    }
}

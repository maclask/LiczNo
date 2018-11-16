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
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import liczno.GamePanel;

/**
 *
 * @author Maciek
 */
public class MenuState extends GameState{

    private String[] options = {"ROZPOCZNIJ GRĘ", "POMOC", "WYJDŹ"};
    private int currentSel = 0;
   
    private BufferedImage bg, logo;
    private int bgWidth, bgHeight, logoWidth, logoHeight;
    
    public MenuState(GameStateManager gsm){
        super(gsm);
       loadImages();
  
    }
    
    @Override
    public void init() {
       
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
        Font f2 = liczno.Main.f.deriveFont(100F);
        g2d.setFont(f2);
        g2d.setColor(Color.BLACK);
       g2d.drawImage(bg, 0, 0, bgWidth, bgHeight, null);
       g2d.drawImage(logo, GamePanel.width / 2 - 400, 350, 300, 300, null);
       g2d.drawString("MENU", GamePanel.width/2-140+5,200+5);
       g2d.setColor(Color.WHITE);
        g2d.drawString("MENU", GamePanel.width/2-140,200);
       for(int i=0; i<options.length; i++){
          g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 20)); 
           g2d.setColor(Color.BLACK);
           g2d.drawString(options[i], GamePanel.width / 2 +3, 400 + i * 100 +3);
           if(i==currentSel){
               g2d.setColor(new Color(255,213,109));
           }
           else{
               g2d.setColor(Color.WHITE);
           }
           g2d.drawString(options[i], GamePanel.width / 2, 400 + i *100);
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
    private void loadImages(){
        try{
            bg = ImageIO.read(getClass().getResource("Images/bg.png"));
            logo = ImageIO.read(getClass().getResource("Images/logo.png"));
        }catch(IOException ioe){System.out.println("Unable to open file");}
        bgWidth = bg.getWidth();
       bgHeight = bg.getHeight();  
       logoWidth = logo.getWidth();
       logoHeight = logo.getHeight(); 
    }
}

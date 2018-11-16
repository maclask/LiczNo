/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates.enterties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import liczno.GamePanel;

/**
 *
 * @author Maciek
 */
public class Player{

    private boolean left = false, right = false;
    
    private double x,y;
    private int width, height;
    
    private BufferedImage player, bg;
    private int bgWidth, bgHeight;
    
    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y =y;
        this.width = width;
        this.height = height;
        loadImages();
    }
    
    public void tick(){
        if(left) x--;
        if(right) x++;

    }
    
    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        if(left) g.drawImage(player, (int)x + width, (int)y, - width, height, null);
        else g.drawImage(player, (int)x, (int)y, width, height, null);
    }
    
    public void keyPressed(int k){
        if(k == KeyEvent.VK_A) left = true;
        if(k == KeyEvent.VK_D) right = true;

    }
    
    public void keyReleased(int k){
        if(k == KeyEvent.VK_A) left = false;
        if(k == KeyEvent.VK_D) right = false;
    }
    
    private void loadImages(){
        try{
            player = ImageIO.read(getClass().getResource("../Images/player.png"));
            bg = ImageIO.read(getClass().getResource("../Images/bg.png"));
       }catch(IOException ioe){System.out.println("Unable to open file");}
       
       bgWidth = bg.getWidth();
       bgHeight = bg.getHeight();  

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.enterties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import liczno.GamePanel;
import liczno.Images;

/**
 *
 * @author Maciek
 */
public class Player{

    private boolean left = false, right = false, isJumping=false, isFalling=false, topCollision = false, bottomCollision = false;
    
    private double x,y;
    

    private double jumpSpeed=10;
    private double currentJumpSpeed = jumpSpeed;
    
    private double maxFallSpeed = 10;
    private double currentFallSpeed = .1;
    
    private int width, height;

       
   
    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y =y;
        this.width = width;
        this.height = height;

    }

    //private Point now, then;
    public void tick(ArrayList<Block> b){
 
        //now = new Point((int)x,(int)y);
       //player = new Rectangle((int)x,(int)y,width,height);              
       
       //if(!bottomCollision)isFalling=true;
        //movement
        if(left) x-=1.5;
        if(right) x+=1.5;
        
        if(isJumping && !isFalling){
            y -= currentJumpSpeed;
            currentJumpSpeed -= .2;
            if(currentJumpSpeed<=0){
                currentJumpSpeed=jumpSpeed;
               // isJumping=false;
                isFalling=true;
            }                     
        }
        
        if(isFalling){
            y += currentFallSpeed;
            
            if(currentFallSpeed<maxFallSpeed){
                currentFallSpeed += .2;
                
            }
            
        }
        else{ currentFallSpeed=.1;}
        //then = new Point((int)x,(int)y);
        for(int i=0; i<b.size(); i++){
                   
        //block = new Rectangle((int)b.get(i).x,(int)b.get(i).y,b.get(i).width,b.get(i).height);
        //collisions
        if(getBounds().intersects(b.get(i).getBounds())){
            y=(int)b.get(i).getY()-(int)height+1;
            isFalling=false;
            bottomCollision = true;
           // System.out.println(i);
        }
        else {if(!isJumping) isFalling=true;bottomCollision = false;}
       
        if(getBoundsLeft().intersects(b.get(i).getBounds())){
            x=(int)b.get(i).getX()+(int)b.get(i).getWidth()-30;
            left=false;
        }
        
        if(getBoundsRight().intersects(b.get(i).getBounds())){
            x=(int)b.get(i).getX()-(int)width+25;
            right=false;
        }
        if(getBoundsTop().intersects(b.get(i).getBounds())){
            isJumping=false;
            isFalling=true;
        }
        
        }
    }
    
    public void draw(Graphics g){
        g.drawImage(Images.bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        //if(left) g.drawImage(Images.player, (int)x + width, (int)y, - width, height, null);
       // else 
      // if(bottomCollision)g.drawRect(3, 3, 100, 100);
        g.drawImage(Images.player, (int)x, (int)y, width, height, null);
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(getBounds());
//        g2d.draw(getBoundsRight());
//        g2d.draw(getBoundsLeft());
//        g2d.draw(getBoundsTop());
    }
    
    public void keyPressed(int k){
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) left = true;
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) right = true;
        if((k == KeyEvent.VK_W || k == KeyEvent.VK_UP ) && !isJumping) 
        {isJumping = true;
        isFalling=false;
        }
    //   if(k == KeyEvent.VK_R) x=0;y=0;

    }
    
    public void keyReleased(int k){
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) left = false;
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) right = false;

    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)), (int)(y+(height/2)), (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsTop(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)), (int)y, (int)width/2, (int)height);
    }
    public Rectangle getBoundsRight(){
        return new Rectangle((int)(x+width-30), (int)y+5, (int)5, (int)height-30);
    }
    public Rectangle getBoundsLeft(){
        return new Rectangle((int)x+30, (int)y+5, (int)5, (int)height-30);
    }

 
}

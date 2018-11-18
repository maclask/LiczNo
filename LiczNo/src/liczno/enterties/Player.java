/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.enterties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import liczno.GamePanel;
import liczno.Images;

/**
 *
 * @author Maciek
 */
public class Player{

    private boolean left = false, right = false, isJumping=false, isFalling=true, topCollision = false;
    
    private double x,y;
    

    private double jumpSpeed=5;
    private double currentJumpSpeed = jumpSpeed;
    
    private double maxFallSpeed = 5;
    private double currentFallSpeed = .1;
    
    private int width, height;
    
   
    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y =y;
        this.width = width;
        this.height = height;

    }
    
    public void tick(Block[] b){
        for(int i=0; i<b.length; i++){
        //collisions
        //right 
        if(Collision.playerBlock(new Point((int)x + width, (int)y + 2), b[i])
                || Collision.playerBlock(new Point((int)x+width, (int)y +height -1 ), b[i])){
            right = false;
        }
        //left
        if(Collision.playerBlock(new Point((int)x -1, (int)y+2), b[i])
                || Collision.playerBlock(new Point((int)x-1, (int)y +height-1), b[i])){
            left = false;
        }
        //top
        if(Collision.playerBlock(new Point((int)x + 1, (int)y), b[i])
                || Collision.playerBlock(new Point((int)x+width -1, (int)y ), b[i])){
            isJumping = false;
        }
        //bottom
        if(Collision.playerBlock(new Point((int)x+2, (int)y + height+1), b[i])
                || Collision.playerBlock(new Point((int)x+width-1, (int)y +height+1), b[i])){
            y = b[i].getY() - height;
            isFalling = false;
            topCollision=true;
        }
        else{
        if( !isJumping){
            isFalling=true;
        }
        }

        
        }
        //movement
        if(left) x--;
        if(right) x++;
        
        if(isJumping){
            y -= currentJumpSpeed;
            currentJumpSpeed -= .1;
            if(currentJumpSpeed<=0){
                currentJumpSpeed=jumpSpeed;
                isJumping=false;
                isFalling=true;
            }                     
        }
        
        if(isFalling){
            y += currentFallSpeed;
            
            if(currentFallSpeed<maxFallSpeed){
                currentFallSpeed += .1;
            }
            
        }
        else currentFallSpeed=.1;
        
    }
    
    public void draw(Graphics g){
        g.drawImage(Images.bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        //if(left) g.drawImage(Images.player, (int)x + width, (int)y, - width, height, null);
       // else 
        g.drawImage(Images.player, (int)x, (int)y, width, height, null);
    }
    
    public void keyPressed(int k){
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) left = true;
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) right = true;
        if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) isJumping = true;

    }
    
    public void keyReleased(int k){
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) left = false;
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) right = false;

    }
    
    
}

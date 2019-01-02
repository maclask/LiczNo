/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.enterties;

import liczno.GameStates.MathTaskState;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import liczno.GamePanel;
import liczno.GameStates.GameStateManager;
import liczno.GameStates.LevelState;
import liczno.Images;

/**
 *
 * @author Maciek
 */
public class Player{

    private boolean left = false, right = false, isJumping=false, isFalling=false, topCollision = false, leftfaced=false;
    public static boolean bombTouched = false;
    public static boolean solved = false;
    public static boolean isDead=false;
    
    public double x = 10; 
    public double y = 650;
    public int width = 152;
    public int height = 165;
    
    public static int score = 0;
    
    private double jumpSpeed=10;
    private double currentJumpSpeed = jumpSpeed;
    
    private double maxFallSpeed = 10;
    private double currentFallSpeed = .1;
    
    private long lasttime=System.nanoTime();
    private int iw = 0, ij = 0, ii = 0, id = 0;



    public Player(){
        reset();
    }
    
    
    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        reset();
    }

    private void reset(){
        bombTouched = false;
        solved = false;
        isDead=false;
    }
    public void tick(ArrayList<Block> b, ArrayList<Bomb> bombs){
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
        
        //bombs collisions
        for(int i=0; i<bombs.size(); i++){
        if(getBounds().intersects(bombs.get(i).getBounds())){
            bombTouched = true;
            left=false;
            right=false;
            if(solved) {
                bombs.remove(i);
                bombTouched = false;
                
            }
            
        }
        }
        
        //blocks collisions
        for(int i=0; i<b.size(); i++){
            
        if(getBounds().intersects(b.get(i).getBounds())){
            y=(int)b.get(i).getY()-(int)height;
            isFalling=false;
            isJumping=false;
        }
        else {if(!isJumping) isFalling=true;}
       
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
           // y=(int)b.get(i).getY()+(int)b.get(i).getHeight();
        }
        
        }
        
        if(x+width>GamePanel.WIDTH){
            x=GamePanel.WIDTH-width;
            right=false;
        }
        if(x<0){
            x=0;
            left=false;
        }
    }
    
    public void draw(Graphics g){ 
        if((left || right) && !isJumping && !bombTouched){
           if(System.nanoTime()-lasttime>=1000000000/40) {
               iw++;
               lasttime=System.nanoTime();
           }
           if(left) g.drawImage(Images.playerwalking[iw], (int)x+width, (int)y, -width, height, null);
           else g.drawImage(Images.playerwalking[iw], (int)x, (int)y, width, height, null);
           if(iw==Images.pw-1) iw=0;
        }
        
            
        else if(isJumping && !bombTouched){
            if(System.nanoTime()-lasttime>=1000000000/60) {
               ij++;
               lasttime=System.nanoTime();
           }
           if(leftfaced) g.drawImage(Images.playerjumping[ij], (int)x+width, (int)y, -width, height, null);
           else g.drawImage(Images.playerjumping[ij], (int)x, (int)y, width, height, null);
           if(ij==Images.pj-1) ij=0;
        }
        else if(isDead){
            if(System.nanoTime()-lasttime>=1000000000/60) {
               id++;
               lasttime=System.nanoTime();
           }
           if(leftfaced) g.drawImage(Images.playerdie[id], (int)x+width, (int)y, -width, height, null);
           else g.drawImage(Images.playerdie[id], (int)x, (int)y, width, height, null);
           if(id==Images.pd-1) id=Images.pd-2;//gsm.states.add(new EndGameState(gsm));
        }
        else{
            if(System.nanoTime()-lasttime>=1000000000/30) {
               ii++;
               lasttime=System.nanoTime();
           }
           if(leftfaced) g.drawImage(Images.playeridle[ii], (int)x+width, (int)y, -width, height, null);
           else g.drawImage(Images.playeridle[ii], (int)x, (int)y, width, height, null);
           if(ii==Images.pi-1) ii=0;
        }
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.draw(getBounds());
//        g2d.draw(getBoundsRight());
//        g2d.draw(getBoundsLeft());
//        g2d.draw(getBoundsTop());

    }
    
    public void keyPressed(int k){
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {left = true;leftfaced=true;}
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {right = true;leftfaced=false;}
        if((k == KeyEvent.VK_W || k == KeyEvent.VK_UP ) && !isJumping) 
        {isJumping = true;
        isFalling=false;
        ij=0;
        }


    }
    
    public void keyReleased(int k){
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {left = false;leftfaced=true;}
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {right = false;leftfaced=false;}

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

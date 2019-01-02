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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import liczno.Audio;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Block;
import liczno.enterties.Bombs;
import liczno.enterties.Player;
import liczno.mapping.Map;


/**
 *
 * @author Maciek
 */
public class LevelState extends GameState{
    Player player;
    Map map;
    Bombs bombs;
    int time = 60;
    int level;
    private long lasttime=System.nanoTime();
    private MathTaskState task;
    int bombamount;
    Rectangle backbox;
    Point click;
    
    public LevelState(GameStateManager gsm, int level, int bombamount){
        super(gsm);
        this.bombamount = bombamount;
        this.level = level;
        //init(); 
        player = new Player();
        map = new Map("map2");System.out.println(this.bombamount);
        bombs = new Bombs(bombamount,map.getBlocks(),player);
        this.bombamount = bombs.getBombs().size();
        Main.audio.playLevelMp3(level);
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    
    
    @Override
    public void init() {
        
   
    }

    @Override
    public void tick() {
        player.tick(map.getBlocks(), bombs.getBombs());
        
        if(Player.bombTouched)
        {
           while(task == null){                
           task=new MathTaskState(gsm);
           
           gsm.states.push(task);
           }
           
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
        if(time<0){
            Player.isDead=true;
            gsm.states.add(new EndGameState(gsm, 0, Player.isDead));
        }
        if(bombamount == 0){
            Player.score += time;
            bombamount--; //to stop adding time
            Main.audio.playLevelMp3a();
            
            gsm.states.add(new EndGameState(gsm, level, Player.isDead));
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
        g2d.drawString("CZAS", 50, 50);
       g2d.drawString(String.valueOf(time), 50, 90);
       g2d.drawString("PUNKTY", GamePanel.WIDTH-150, 50);
       g2d.drawString(String.valueOf(Player.score), GamePanel.WIDTH-150, 90);
       
       
       backbox = new Rectangle(50, GamePanel.HEIGHT-65, 100,50);
       g.setColor(new Color(255, 51, 51));
        g.fillRect(50, GamePanel.HEIGHT-65, 100,50);
        g.setColor(Color.WHITE);
        drawCenteredString(g, "Menu", backbox, f2);
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
        click = new Point(e.getX(), e.getY());
    
        if(backbox.contains(click))
                gsm.states.add(new MenuState(gsm));      
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
}

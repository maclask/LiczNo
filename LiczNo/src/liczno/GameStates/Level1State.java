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
    
    @Override
    public void init() {
       player = new Player(10, 10, 152, 165);
       map = new Map("map1");
       bombs = new Bombs(4,map.getBlocks());
    }

    @Override
    public void tick() {
        player.tick(map.getBlocks(), bombs.getBombs());
        
        if(Player.bombTouched)
        {
           task=new MathTaskState(gsm);
           gsm.states.push(task);
        }
        if(Player.solved) task=null;
        if(System.nanoTime()-lasttime>=1000000000)
        {
            time--;
            lasttime=System.nanoTime();
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
        Font f2 = liczno.Main.f.deriveFont(40F);
        g2d.setFont(f2);
        g2d.setColor(Color.WHITE);
       g2d.drawString(String.valueOf(time), 100, 100);
    }

    @Override
    public void keyPressed(int k) {
        player.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        player.keyReleased(k);
    }

}

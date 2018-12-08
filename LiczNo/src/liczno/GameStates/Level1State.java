/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Graphics;
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
    
    @Override
    public void init() {
       player = new Player(10, 10, 170, 170);
       map = new Map("map1");
       bombs = new Bombs(4,map.getBlocks());
    }

    @Override
    public void tick() {
        player.tick(map.getBlocks(), bombs.getBombs());
    }

    @Override
    public void draw(Graphics g) {
      
        g.drawImage(Images.bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

       map.draw(g);
       bombs.draw(g);
       player.draw(g);
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

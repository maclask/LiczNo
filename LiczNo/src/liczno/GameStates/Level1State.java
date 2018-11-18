/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Graphics;
import liczno.enterties.Block;
import liczno.enterties.Player;


/**
 *
 * @author Maciek
 */
public class Level1State extends GameState{
    
    public Level1State(GameStateManager gsm){
        super(gsm);
        init(); 

    }

    Player player;
    Block[] b;
    
    @Override
    public void init() {
       player = new Player(200, 250, 170, 170);
       b = new Block[5];
       b[0] = new Block(0, 618);
       b[1] = new Block(270, 618);
       b[2] = new Block(540, 618);
       b[3] = new Block(810, 618);
       b[4] = new Block(500, 558);
    }

    @Override
    public void tick() {
        for(int i=0;i<b.length;i++){
            //b[i].draw();
        }
        player.tick(b);
    }

    @Override
    public void draw(Graphics g) {
        player.draw(g);
        for(int i=0;i<b.length;i++){
            b[i].draw(g);
        }
        //b.draw(g);
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

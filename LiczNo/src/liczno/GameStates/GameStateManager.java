/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Graphics;
import java.util.Stack;

/**
 *
 * @author Maciek
 */
public class GameStateManager {
    
    private Stack<GameState> states;
    
    public GameStateManager(){
        states = new Stack<>();
        states.push(new MenuState(this));
    }
    
    public void tick(){
        states.peek().tick();
    }
    
    public void draw(Graphics g){
        states.peek().draw(g);
    }
    
    public void keyPressed(int e){
        states.peek().keyPressed(e);
    }
    
    public void keyReleased(int e){
        states.peek().keyReleased(e);
    }
}

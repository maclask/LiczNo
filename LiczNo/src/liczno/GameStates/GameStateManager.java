/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Stack;
import liczno.enterties.Player;

/**
 * Manager of game states. Running methods from GameState interface for the top
 * state in stack.
 *
 * @see GameState
 * @author Maciek
 */
public class GameStateManager {

    /**
     * Stack of game states
     */
    public Stack<GameState> states;

    public GameStateManager() {
        states = new Stack<>();

        states.push(new MenuState(this));
    }

    public void tick() {
        states.peek().tick();
    }

    public void draw(Graphics g) {
        //to draw level and math task simultaneously
        if (Player.bombTouched) {
            states.get(states.size() - 2).draw(g);
        }
        states.peek().draw(g);

    }

    public void keyPressed(int e) {
        states.peek().keyPressed(e);
    }

    public void keyReleased(int e) {
        states.peek().keyReleased(e);
    }

    public void mouseClicked(MouseEvent e) {
        states.peek().mouseClicked(e);
    }

    public void mouseMoved(MouseEvent e) {
        states.peek().mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        states.peek().mouseDragged(e);
    }
}

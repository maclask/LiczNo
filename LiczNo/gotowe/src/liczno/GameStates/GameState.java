/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Interface of GameState
 *
 * @author Maciek
 */
public abstract class GameState {

    /**
     * Game state manager
     *
     * @see GameStateManager
     */
    public GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public abstract void init();

    public abstract void tick();

    public abstract void draw(Graphics g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public abstract void mouseClicked(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public abstract void mouseDragged(MouseEvent e);

}

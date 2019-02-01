/*
 * The MIT License
 *
 * Copyright 2018 Maciek.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package liczno.GameStates;

import liczno.Score;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 * State of game save score input
 *
 * @see GameState
 * @author Maciek
 */
public class SaveScoreState extends GameState {

    private List<Character> input;
    private Button topButton, inputButton, backButton, nextButton;
    private Button[] clickButtons; 
    private String top, next, save, menu;
    private Point click, hover;

    private boolean enter = false;
    private static String name = "";
    private ArrayList<Score> scores;
    /**
     * Creates MenuState
     *
     * @see GameStateManager
     */
    SaveScoreState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        click = new Point(0, 0);
        hover = new Point(0, 0);

        input = new ArrayList();
        
        top = GamePanel.messages.getString("givename");
        menu = GamePanel.messages.getString("menu");
        save = GamePanel.messages.getString("save");

        topButton = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 5, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 4, 100, top);    
        inputButton = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 7 * 4, GamePanel.WIDTH /2, GamePanel.HEIGHT / 8,GamePanel.gray, Color.BLACK ,60, name);
        backButton = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 6, GamePanel.red, Color.WHITE,  50, menu);
        nextButton = new Button(GamePanel.WIDTH / 4 * 2, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 6, GamePanel.green, Color.WHITE,  50, save);
        clickButtons = new Button[]{backButton, nextButton};
    }

    @Override
    public void tick() {
        name = "";
        for (int i = 0; i < input.size(); i++) {
            name += Character.toString(input.get(i));
        }
        inputButton.text=name;
    }

    @Override
    public void draw(Graphics g) {


        g.drawImage(Images.bg, 0, 0, null);
        
        topButton.drawButton(g);
        inputButton.drawButton(g);
        backButton.drawButton(g);
        nextButton.drawButton(g);


       

    }

    @Override
    public void keyPressed(int k) {

        if ((k >= 65 && k <= 90) || (k >= 97 && k <= 122) || k == 32) {
            if (input.size() < 16) {
                input.add((char) k);
            }
        } else if (k == KeyEvent.VK_BACK_SPACE) {
            if (input.size() > 0) {
                input.remove(input.size() - 1);
            }
        } else if (k == KeyEvent.VK_ENTER) {
            enter = true;
            checkClick();
        } else if (k == KeyEvent.VK_ESCAPE) {
            gsm.states.add(new MenuState(gsm));
        }

    }

    @Override
    public void keyReleased(int k) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = new Point(e.getX(), e.getY());
        checkClick();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hover = new Point(e.getX(), e.getY());
        for (Button button : clickButtons) {
            if (button.contains(hover)) {
                Main.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
                break;
            } else {
                Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private void checkClick() {
        if (nextButton.contains(click) || enter) {
            Main.audio.stop();

            try {
                GamePanel.score.writeScore(name, Player.score);
            } catch (IOException ex) {
                Logger.getLogger(SaveScoreState.class.getName()).log(Level.SEVERE, null, ex);
            }
            gsm.states.add(new MenuState(gsm));
        } else if (backButton.contains(click)) {
            gsm.states.add(new MenuState(gsm));
        }
    }

@Override
    public void mouseDragged(MouseEvent e) {
    }
}

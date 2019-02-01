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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 * State of game displaying end game informations
 *
 * @see GameState
 * @author Maciek
 */
public class EndGameState extends GameState {

    private Button topButton, scoreButton, backButton, nextButton;
    private Button[] clickButtons;
    private String score, next, save, menu;
    private Point click, hover;

    private String[] level;
    private String[] textes;
    private int text;
    private boolean dead, enter = false;

    /**
     * Creates EndGameState
     *
     * @param gsm GameStateManager
     * @param text text to be display depending of current level
     * @param dead boolean indicating if player is dead
     * @see GameStateManager
     */
    public EndGameState(GameStateManager gsm, int text, boolean dead) {
        super(gsm);
        this.text = text;
        this.dead = dead;

        level = new String[]{GamePanel.messages.getString("level") + "1",
            GamePanel.messages.getString("level") + "2",
            GamePanel.messages.getString("level") + "3",
            GamePanel.messages.getString("level") + "4",
            GamePanel.messages.getString("level") + "5",
            GamePanel.messages.getString("win")};
        textes = new String[]{GamePanel.messages.getString("dead")};
        score = GamePanel.messages.getString("yourscore");
        next = GamePanel.messages.getString("next");
        save = GamePanel.messages.getString("save");
        menu = GamePanel.messages.getString("menu");
        score += Player.score;

        topButton = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 5, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 4, 100, level[text]);

        scoreButton = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 5 * 2, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 4, 60, score);
        backButton = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 6, GamePanel.red, Color.WHITE, 50, menu);
        nextButton = new Button(GamePanel.WIDTH / 4 * 2, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 6, GamePanel.green, Color.WHITE, 50, next);
        clickButtons = new Button[]{backButton, nextButton};
        if (dead) {
            topButton.text = textes[0];
            nextButton.text = GamePanel.messages.getString("save");
        }
        if (text == 5) {
            nextButton.text = GamePanel.messages.getString("save");
        }
    }

    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        click = new Point(0, 0);
        hover = new Point(0, 0);

        try {
            Main.audio.play(3, false);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelState.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void tick() {
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(Images.bg, 0, 0, null);
        topButton.drawButton(g);
        if (text != 0 || dead) {
            scoreButton.drawButton(g);
        }
        backButton.drawButton(g);
        nextButton.drawButton(g);

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
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
        boolean hand = false;
        for (int i = 0; i < clickButtons.length; i++) {
            if (clickButtons[i].contains(hover)) {
                Main.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
                hand = true;
                break;
            }
            if (!hand) {
                Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private void checkClick() {
        if (nextButton.contains(click) || enter) {
            //Main.audio.stop();
            if (dead || text == 5) {
                //Player.score = 0;
                Player.isDead = false;
                //gsm.states.add(new EndGameState(gsm,0,Player.isDead));
                gsm.states.add(new SaveScoreState(gsm));
            } else {

                switch (text) {
                    case 0:
                        Player.score = 0;
                        gsm.states.add(new LevelState(gsm, 1, 6));
                        break;
                    case 1:
                        gsm.states.add(new LevelState(gsm, 2, 6));
                        break;
                    case 2:
                        gsm.states.add(new LevelState(gsm, 3, 6));
                        break;
                    case 3:
                        gsm.states.add(new LevelState(gsm, 4, 6));
                        break;
                    case 4:
                        gsm.states.add(new LevelState(gsm, 5, 6));
                        break;
                    default:
                        gsm.states.add(new MenuState(gsm));
                }
            }

        } else if (backButton.contains(click)) {
            gsm.states.add(new MenuState(gsm));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

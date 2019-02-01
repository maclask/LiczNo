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
import java.util.ArrayList;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;

/**
 * State of game displaying highscores
 *
 * @see GameState
 * @author Maciek
 */
public class ShowScoreState extends GameState {

    Point click, hover;
    private ArrayList<Score> scores;
    private Button topButton, doneButton;
    private Button[] scoreButton;
    private String toptext, ok;
    private int records;
    private boolean enter = false;

    /**
     * Creates ShowScoreState
     *
     * @see GameStateManager
     */
    ShowScoreState(GameStateManager gsm) {
        super(gsm);
        toptext = GamePanel.messages.getString("scores");
        ok = GamePanel.messages.getString("ok");
        topButton = new Button(GamePanel.WIDTH / 3, GamePanel.HEIGHT / 7, GamePanel.WIDTH / 3, GamePanel.HEIGHT / 7, 100, toptext);
        doneButton = new Button(GamePanel.WIDTH / 8 * 3, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 8, GamePanel.green, Color.WHITE, 60, ok);

    }

    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        click = new Point(0, 0);
        hover = new Point(0, 0);

        scores = GamePanel.score.readScore();
        if (scores != null) {
            scores = GamePanel.score.sort(scores);
            System.out.println(scores.size());
            if (scores.size() < 5) {
                records = scores.size();
            } else {
                records = 5;
            }
        } else {
            records = 0;
        }
        scoreButton = new Button[records];
        for (int i = 0; i < records; i++) {
            scoreButton[i] = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 8 * 2 + 60 * i, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 4, 60, scores.get(i).toString());
        }
    }

    @Override
    public void tick() {
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(Images.bg, 0, 0, null);

        topButton.drawButton(g);
        doneButton.drawButton(g);

        for (int i = 0; i < records; i++) {
            scoreButton[i].drawButton(g);
        }

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
        if (doneButton.contains(hover)) {
            Main.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hand = true;
        }
        if (!hand) {
            Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void checkClick() {
        if (doneButton.contains(click) || enter) {
            gsm.states.pop();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

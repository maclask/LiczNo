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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;

/**
 * State of game displaying setting menu
 *
 * @see GameState
 * @author Maciek
 */
public class SettingsState extends GameState {

    private Button topButton, doneButton;
    private Button[][] settingsList;

    private String[] settingsName;
    private String[] switchState;
    private String[] languages;
    private final static int settingsAmount = 2;

    private ArrayList<Rectangle> boxes;
    private String score;
    private Point click, hover;
    private ArrayList<Score> scores;

    private int currentSel;
    private boolean enter = false;
    private String topText, ok;
    /**
     * Creates SettingsState
     *
     * @see GameStateManager
     */
    SettingsState(GameStateManager gsm) {
        super(gsm);
        settingsName = new String[]{GamePanel.messages.getString("sfx"),
            GamePanel.messages.getString("music"),
            GamePanel.messages.getString("clearscore"),
            GamePanel.messages.getString("antialiasing"),
            GamePanel.messages.getString("lang")};
        switchState = new String[]{GamePanel.messages.getString("on"),
            GamePanel.messages.getString("off"),
            GamePanel.messages.getString("cleared"),
            GamePanel.messages.getString("clear")};
        languages = new String[]{GamePanel.messages.getString("pol"),
            GamePanel.messages.getString("ang")};
        topText = GamePanel.messages.getString("settings");
        ok = GamePanel.messages.getString("ok");
        topButton = new Button(GamePanel.WIDTH / 3, GamePanel.HEIGHT / 11, GamePanel.WIDTH / 3, GamePanel.HEIGHT / 7, 100, topText);
        doneButton = new Button(GamePanel.WIDTH / 8 * 3, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 8, GamePanel.green, Color.WHITE, 60, ok);

        settingsList = new Button[settingsName.length][settingsAmount];
        for (int i = 0; i < settingsList.length; i++) {
            settingsList[i][0] = new Button(GamePanel.WIDTH / 5, GamePanel.HEIGHT / 8 * 2 + 80 * i, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, 40, settingsName[i], false);
            settingsList[i][1] = new Button(GamePanel.WIDTH / 5 * 3, GamePanel.HEIGHT / 8 * 2 + 80 * i, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, GamePanel.green, Color.WHITE, 40, switchState[0]);
        }
        if (!Main.audio.sfxOn) {
            settingsList[0][1].bgColor = GamePanel.red;
            settingsList[0][1].text = switchState[1];
        }
        if (!Main.audio.musicOn) {
            settingsList[1][1].bgColor = GamePanel.red;
            settingsList[1][1].text = switchState[1];
        }
        if (!GamePanel.antialiasing) {
            settingsList[3][1].bgColor = GamePanel.red;
            settingsList[3][1].text = switchState[1];
        }
        settingsList[2][1].text = switchState[3];
        settingsList[2][1].bgColor = GamePanel.red;
        if (GamePanel.currentLocale == GamePanel.plLocale) {
            settingsList[4][1].text = languages[0];
        } else if (GamePanel.currentLocale == GamePanel.enLocale) {
            settingsList[4][1].text = languages[1];
        }
        currentSel = 0;
    }

    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        click = new Point(0, 0);
        hover = new Point(0, 0);

    }

    @Override
    public void tick() {
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(Images.bg, 0, 0, null);
        Font f2 = Images.f.deriveFont(100F);
        g.setFont(f2);
        g.setColor(Color.WHITE);

        topButton.drawButton(g);
        for (int i = 0; i < settingsList.length; i++) {
            settingsList[i][0].drawButton(g);
            if (currentSel == i) {
                settingsList[i][1].txtColor = GamePanel.lightgray;
            } else {
                settingsList[i][1].txtColor = Color.WHITE;
            }
            settingsList[i][1].drawButton(g);
        }
        doneButton.drawButton(g);

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            enter = true;
            checkClick(currentSel);
        } else if (k == KeyEvent.VK_ESCAPE) {
            gsm.states.add(new MenuState(gsm));
        } else if (k == KeyEvent.VK_DOWN) {
            currentSel++;
            if (currentSel >= settingsList.length) {
                currentSel = 0;
            }
        } else if (k == KeyEvent.VK_UP) {
            currentSel--;
            if (currentSel < 0) {
                currentSel = settingsList.length - 1;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = new Point(e.getX(), e.getY());
        checkClick(currentSel);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hover = new Point(e.getX(), e.getY());
        boolean hand = false;
        for (int i = 0; i < settingsList.length; i++) {
            if (settingsList[i][1].contains(hover)) {
                currentSel = i;
                Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                hand = true;
                break;
            }
        }
        if (doneButton.contains(hover)) {
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            hand = true;
        }

        if (!hand) {
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    private void checkClick(int currentSel) {
        if (doneButton.contains(click)) {
            gsm.states.add(new MenuState(gsm));
        } else if (settingsList[0][1].contains(click) || currentSel == 0) {
            if (Main.audio.sfxOn) {
                Main.audio.sfxOn = !Main.audio.sfxOn;
                settingsList[0][1].bgColor = GamePanel.red;
                settingsList[0][1].text = switchState[1];
            } else {
                Main.audio.sfxOn = !Main.audio.sfxOn;
                settingsList[0][1].bgColor = GamePanel.green;
                settingsList[0][1].text = switchState[0];
            }

        } else if (settingsList[1][1].contains(click) || currentSel == 1) {

            if (Main.audio.musicOn) {
                Main.audio.stopLevelMp3();
                Main.audio.musicOn = !Main.audio.musicOn;
                settingsList[1][1].bgColor = GamePanel.red;
                settingsList[1][1].text = switchState[1];
            } else {
                Main.audio.musicOn = !Main.audio.musicOn;
                Main.audio.playLevelMp3(5);
                settingsList[1][1].bgColor = GamePanel.green;
                settingsList[1][1].text = switchState[0];
            }
        } else if (settingsList[3][1].contains(click) || currentSel == 3) {

            if (GamePanel.antialiasing) {
                GamePanel.antialiasing = !GamePanel.antialiasing;
                settingsList[3][1].bgColor = GamePanel.red;
                settingsList[3][1].text = switchState[1];
            } else {
                GamePanel.antialiasing = !GamePanel.antialiasing;
                settingsList[3][1].bgColor = GamePanel.green;
                settingsList[3][1].text = switchState[0];
            }
        } else if (settingsList[2][1].contains(click) || currentSel == 2) {
            GamePanel.score.deleteScores();
            settingsList[2][1].bgColor = GamePanel.green;
            settingsList[2][1].text = switchState[2];
        } else if (settingsList[4][1].contains(click) || currentSel == 4) {

            GamePanel.changeLang();

            gsm.states.add(new SettingsState(gsm));
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

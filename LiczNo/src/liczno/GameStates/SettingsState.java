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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import liczno.Audio;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 *
 * @author Maciek
 */
public class SettingsState extends GameState {

    private Button topButton, doneButton;
    private Button[][] settingsList;

    private final String[] settingsName = {"Efekty dźwiękowe", "Muzyka", "Wyczyść listę wyników", "Wygładzanie czcionek"};
    private final String[] switchState = {"Włączone", "Wyłączone","Wyczyszczono","Wyczyść"};
    private final static int settingsAmount = 2;

    private ArrayList<Rectangle> boxes;
    private String score;
    Point click, hover;
    private ArrayList<Score> scores;

    private int records;
    private boolean enter = false;
    private String print;

    SettingsState(GameStateManager gsm) {
        super(gsm);

        topButton = new Button(GamePanel.WIDTH / 3 , GamePanel.HEIGHT / 7, GamePanel.WIDTH / 3, GamePanel.HEIGHT / 7, 100, "Ustawienia");
        doneButton = new Button(GamePanel.WIDTH / 8 * 3, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 8, GamePanel.green, Color.WHITE, 60, "OK");

        settingsList = new Button[settingsName.length][settingsAmount];
        for (int i = 0; i < settingsList.length; i++) {
            settingsList[i][0] = new Button(GamePanel.WIDTH / 5  , GamePanel.HEIGHT / 9 * 3+ 80 * i, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, 40, settingsName[i], false);
            settingsList[i][1] = new Button(GamePanel.WIDTH / 5 * 3, GamePanel.HEIGHT / 9 *3 + 80 * i, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, GamePanel.green, Color.WHITE, 40, switchState[0]);
        }
        if(!Main.audio.sfxOn){
            settingsList[0][1].bgColor = GamePanel.red;
            settingsList[0][1].text = switchState[1];
        }
        if(!Main.audio.musicOn){
            settingsList[1][1].bgColor = GamePanel.red;
            settingsList[1][1].text = switchState[1];
        }
        if(!GamePanel.antialiasing){
            settingsList[3][1].bgColor = GamePanel.red;
            settingsList[3][1].text = switchState[1];
        }
        settingsList[2][1].text = switchState[3];
        settingsList[2][1].bgColor = GamePanel.red;
                
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
            settingsList[i][1].drawButton(g);
        }
        doneButton.drawButton(g);

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            enter = true;
            checkClick();
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
        for (int i = 0; i < settingsList.length ; i++) {
            if (settingsList[i][1].contains(hover)) {
                Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                hand = true;
                break;
            }             
        }
        if(doneButton.contains(hover)){
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            hand = true;
        }
            
        if(!hand)
        Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
    }

    private void checkClick() {
        if (settingsList[0][1].contains(click) || enter) {
            if(Main.audio.sfxOn){
                Main.audio.sfxOn = !Main.audio.sfxOn;
                settingsList[0][1].bgColor = GamePanel.red;
                settingsList[0][1].text = switchState[1];
            }
            else{
                Main.audio.sfxOn = !Main.audio.sfxOn;
                settingsList[0][1].bgColor = GamePanel.green;
                settingsList[0][1].text = switchState[0];
            }
            
        } 
        if (settingsList[1][1].contains(click)) {
            
            if(Main.audio.musicOn){
                Main.audio.playLevelMp3a();
                Main.audio.musicOn = !Main.audio.musicOn;
                settingsList[1][1].bgColor = GamePanel.red;
                settingsList[1][1].text = switchState[1];
            }
            else{
                Main.audio.musicOn = !Main.audio.musicOn;
                Main.audio.playLevelMp3(5);
                settingsList[1][1].bgColor = GamePanel.green;
                settingsList[1][1].text = switchState[0];
            }
        } 
        if (settingsList[3][1].contains(click)) {
            
            if(GamePanel.antialiasing){
                GamePanel.antialiasing = !GamePanel.antialiasing;
                settingsList[3][1].bgColor = GamePanel.red;
                settingsList[3][1].text = switchState[1];
            }
            else{
                GamePanel.antialiasing = !GamePanel.antialiasing;
                settingsList[3][1].bgColor = GamePanel.green;
                settingsList[3][1].text = switchState[0];
            }
        } 
        if (settingsList[2][1].contains(click)) {
                GamePanel.score.deleteScores();
                settingsList[2][1].bgColor = GamePanel.green;
                settingsList[2][1].text = switchState[2];
        }
        if (doneButton.contains(click)){
            gsm.states.pop();
        }
    }


}

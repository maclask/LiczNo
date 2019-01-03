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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import liczno.Audio;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 *
 * @author Maciek
 */
public class SaveScoreState extends GameState{

    private List<Character> input;
    private Rectangle topbox, inputbox, scorebox, backbox, nextbox;
    private ArrayList<Rectangle> boxes;
    private String score;
    Point click, hover;
    

    private boolean enter=false;
    private String print = "Podaj imię";
    private static  String name = "";
        private ArrayList<Score> scores;
            
    SaveScoreState(GameStateManager gsm){
        super(gsm);
    }
    
    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        click = new Point(0,0);
        hover = new Point(0,0);
        score = "Twój wynik: " + Player.score;
        boxes = new ArrayList();
        input = new ArrayList();
              scores = GamePanel.score.readScore();
                GamePanel.score.sort(scores);
    }

    @Override
    public void tick() {
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g.drawImage(Images.bg, 0, 0, null);
        Font f2 = Images.f.deriveFont(100F);
        g.setFont(f2);
        g.setColor(Color.WHITE);

        

        //g.drawString(print, GamePanel.WIDTH/4, GamePanel.HEIGHT/5*3);
        topbox = new Rectangle(GamePanel.WIDTH/4, GamePanel.HEIGHT/5, GamePanel.WIDTH/2,GamePanel.HEIGHT/4);
       drawCenteredString(g,  print, topbox, f2);
        
        f2 = f2.deriveFont(60F);
        g.setFont(f2);
        
        inputbox = new Rectangle(370,414,384,86);
        g.setColor(new Color(191,191,191));
        g.fillRect(370,414,384,86);

        name="";
        for(int i =0; i<input.size(); i++){
            name += Character.toString (input.get(i));
        }
        g.setColor(Color.BLACK);
        drawCenteredString(g, name, inputbox, f2);
        

            nextbox = new Rectangle(GamePanel.WIDTH/8*3, GamePanel.HEIGHT/5*4, GamePanel.WIDTH/4,GamePanel.HEIGHT/6);
            boxes.add(nextbox);
            g.setColor(new Color(50,120,54));
            g.fillRect(GamePanel.WIDTH/8*3, GamePanel.HEIGHT/5*4, GamePanel.WIDTH/4,GamePanel.HEIGHT/6);
            g.setColor(Color.WHITE);
            drawCenteredString(g, "Zapisz", nextbox, f2);
        
            

    }

    @Override
    public void keyPressed(int k) {
        
        if((k >= 65 && k <= 90) || (k >= 97 && k <= 122)) {
            if(input.size()<16)
                input.add((char)k); 
        } 
        else if(k == KeyEvent.VK_BACK_SPACE) {
            if(input.size()>0)
                input.remove(input.size()-1);
        }
        else if(k == KeyEvent.VK_ENTER) {
            enter=true;
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
        for (Rectangle box : boxes) {
            if(box.contains(hover) ){
                Main.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
                break;
            }
            else
                Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void checkClick(){
        if(nextbox.contains(click) || enter){
            Main.audio.stop();
            
            try {
                GamePanel.score.writeScore(name, Player.score);
            } catch (IOException ex) {
                Logger.getLogger(SaveScoreState.class.getName()).log(Level.SEVERE, null, ex);
            }
            gsm.states.add(new MenuState(gsm));
        }
        else if(backbox.contains(click)){
            gsm.states.add(new MenuState(gsm));
        }
    }
    
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    
}

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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class AboutState extends GameState {

    private Button topButton, doneButton;
    private BufferedReader in;
    Point click, hover;
    private String patch = "src/liczno/GameStates/about", line, full="";
    private int i, height;
    private ArrayList<String> about;
    private boolean enter = false, scroll =true;
double x;

    AboutState(GameStateManager gsm) {
        super(gsm);

        topButton = new Button(GamePanel.WIDTH / 3 , GamePanel.HEIGHT / 7, GamePanel.WIDTH / 3, GamePanel.HEIGHT / 7, 100, "O grze");
        doneButton = new Button(GamePanel.WIDTH / 8 * 3, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 8, GamePanel.green, Color.WHITE, 60, "OK");
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(patch)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AboutState.class.getName()).log(Level.SEVERE, null, ex);
        }
        i =0;
        about = new ArrayList<>();  
        try {
            while((line = in.readLine()) != null)
            {
                full+=(line);
                i++;
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(AboutState.class.getName()).log(Level.SEVERE, null, ex);
        }
        x = 600;
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
        Font f2 = Images.f.deriveFont(20F);
        g.setFont(f2);
        g.setColor(Color.WHITE);
        if(scroll)x -=0.4;
        //for(int y = 0; y < i; y++)
        drawString(g,full, GamePanel.WIDTH/8, (int)(x));
        if(x<-1500)x=600;
        g.drawImage(Images.bg, 0, 0, 1024, 210, 0, 0, 1024, 768, null);    
        g.drawImage(Images.bg, 0, 600, 1024, 768, 0, 600, 1024, 768, null);

        topButton.drawButton(g);

        doneButton.drawButton(g);

    }
    void drawString(Graphics g, String text, int x, int y) {
        for (String linea : text.split("<br/>")){ 
            g.drawString(linea, x, y += g.getFontMetrics().getHeight());
        }
    }
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            enter = true;
            checkClick();
        }
        if (k==KeyEvent.VK_UP){
            scroll=false;
            x-=10;
        }
        if (k==KeyEvent.VK_DOWN){
            x+=10;
            scroll=false;
        }
        if (k==KeyEvent.VK_SPACE){
            scroll=!scroll;
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

        if(doneButton.contains(hover)){
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            hand = true;
        }
            
        if(!hand)
        Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
    }

    private void checkClick() {
        
        if (doneButton.contains(click)){
            gsm.states.pop();
        }
    }


}

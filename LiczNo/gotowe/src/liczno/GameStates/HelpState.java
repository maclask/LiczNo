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
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import liczno.Button;
import liczno.GamePanel;
import liczno.Images;
import liczno.Main;

/**
 * State of game displaying help instrictions
 *
 * @see GameState
 * @author Maciek
 */
public class HelpState extends GameState {

    private Button topButton, doneButton;
    private BufferedReader in;
    private Point click, hover;
    private String path = "about", line, full = "";
    private int i, height;
    private ArrayList<String> about;
    private boolean enter = false, scroll = true;
    private String toptext, ok, helpinstructions;
    private Font f2;
    private double y;

    /**
     * Creates AboutState
     *
     * @param gsm GameStateManager
     * @see GameStateManager
     */
    HelpState(GameStateManager gsm) {
        super(gsm);
        toptext = GamePanel.messages.getString("help");
        ok = GamePanel.messages.getString("ok");
        helpinstructions = GamePanel.messages.getString("helpinstrucions");
        topButton = new Button(GamePanel.WIDTH / 3, GamePanel.HEIGHT / 11, GamePanel.WIDTH / 3, GamePanel.HEIGHT / 7, 100, toptext);
        doneButton = new Button(GamePanel.WIDTH / 8 * 3, GamePanel.HEIGHT / 5 * 4, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 8, GamePanel.green, Color.WHITE, 60, ok);
        InputStream is = this.getClass().getResourceAsStream(path);
        in = new BufferedReader(new InputStreamReader(is));
        i = 0;
        about = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null) {
                full += (line);
                i++;
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(HelpState.class.getName()).log(Level.SEVERE, null, ex);
        }
        height = i * 20;
        y = GamePanel.HEIGHT / 2;
    }

    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        click = new Point(0, 0);
        hover = new Point(0, 0);

    }

    @Override
    public void tick() {
        if (scroll) {
            y -= 0.4;
        }
        if (y < -height) {
            y = GamePanel.HEIGHT;
        }
        if (y > GamePanel.HEIGHT) {
            y = GamePanel.HEIGHT;
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(Images.bg, 0, 0, null);
        f2 = Images.f.deriveFont(30F);
        g.setFont(f2);
        g.setColor(Color.WHITE);

        drawString(g, helpinstructions, GamePanel.WIDTH / 10, GamePanel.HEIGHT / 5 * 3);

        g.drawImage(Images.help, 0, 0, null);

        topButton.drawButton(g);

        doneButton.drawButton(g);

    }

    void drawString(Graphics g, String text, int x, int y) {
        for (String linea : text.split("<br/>")) {
            g.drawString(linea, x, y += g.getFontMetrics().getHeight());
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            enter = true;
            checkClick();
        }
        if (k == KeyEvent.VK_UP) {
            scroll = false;
            y -= 10;
        }
        if (k == KeyEvent.VK_DOWN) {
            y += 10;
            scroll = false;
        }
        if (k == KeyEvent.VK_SPACE) {
            scroll = !scroll;
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
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            hand = true;
        }
        drag = e.getY() - y;
        if (!hand) {
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    private void checkClick() {

        if (doneButton.contains(click)) {
            gsm.states.pop();
        }
    }
    private double drag;

    @Override
    public void mouseDragged(MouseEvent e) {
        y = e.getY() - drag;
    }

}

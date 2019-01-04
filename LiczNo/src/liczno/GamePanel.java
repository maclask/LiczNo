/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno;

import java.awt.Color;
import liczno.GameStates.GameStateManager;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.OverlayLayout;

/**
 *
 * @author Maciek
 */
public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    private GameStateManager gsm;

    private Thread thread;
    private boolean isRunning = false;

    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;

    public Images images;
    public static ScoreManager score;
    public static Color red, green;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        images = new Images();
        score = new ScoreManager();
        green = new Color(50, 120, 54);
        red = new Color(255, 51, 51);
        start();
        //Map a = score.readScore();

    }

    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();

        LayoutManager overlay = new OverlayLayout(this);
        this.setLayout(overlay);
    }

    @Override
    public void run() {
        long start, elapsed, wait;
        gsm = new GameStateManager();

        while (isRunning) {
            start = System.nanoTime();

            tick();
            repaint();

            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000;

            if (wait <= 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        gsm.tick();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        gsm.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        gsm.mouseClicked(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        gsm.mouseMoved(me);
    }

}

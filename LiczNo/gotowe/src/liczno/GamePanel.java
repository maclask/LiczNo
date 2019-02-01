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
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.OverlayLayout;

/**
 * Initializes GamePanel and game loop extends JPanel implements Runnable,
 * MouseListener, MouseMotionListener, KeyListener
 *
 * @author Maciek
 */
public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {

    /**
     * GamePanel width
     */
    public static final int WIDTH = 1024;
    /**
     * GamePanel height
     */
    public static final int HEIGHT = 768;

    /**
     * Menager of Game Staes
     *
     * @see GameStateManager
     */
    public static GameStateManager gsm;

    private Thread thread;
    private boolean isRunning = false;

    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;

    /**
     * Images loader
     *
     * @see Images
     */
    public Images images;
    /**
     * Score manager
     *
     * @see ScoreManager
     */
    public static ScoreManager score;

    /**
     * Defines colors most used in game
     */
    public static Color red, green, gray, yellow, lightgray;
    /**
     * Specifies if font antialiasing is on
     */
    public static boolean antialiasing = true;
    /**
     * Resource of strings in game
     */
    public static ResourceBundle messages;
    /**
     * Array of game languages
     */
    public static Locale[] local;
    /**
     * Game languages
     */
    public static Locale plLocale, enLocale, currentLocale;

    private static int i = 0;

    /**
     * Creates game panel with
     */
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
        gray = new Color(191, 191, 191);
        yellow = new Color(243, 192, 38);
        lightgray = new Color(216,216,216);

        enLocale = new Locale("en", "US");
        plLocale = new Locale("pl", "PL");
        currentLocale = plLocale;
        local = new Locale[]{plLocale, enLocale};
        messages = ResourceBundle.getBundle("lang.MessagesBundle", currentLocale);
        start();
    }

    /**
     * Starts new Thread
     */
    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();

        LayoutManager overlay = new OverlayLayout(this);
        this.setLayout(overlay);

    }

    /**
     * Gameloop
     */
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

    /**
     * Tick of gameloop
     */
    public void tick() {
        gsm.tick();
    }

    /**
     * Repaint in gameloop
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        if (antialiasing) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        gsm.draw(g);
    }

    /**
     * Switches language
     */
    public static void changeLang() {
        i++;
        if (i > local.length - 1) {
            i = 0;
        }
        currentLocale = local[i];
        messages = ResourceBundle.getBundle("lang.MessagesBundle", currentLocale);
    }

    /**
     * Handles keyPressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    /**
     * Handles keyRelesed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());

    }

    /**
     * Handles keyTyped (not in use)
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handles mouseClicked
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        gsm.mouseClicked(me);
    }

    /**
     * Handles mousePressed (not in use)
     */
    @Override
    public void mousePressed(MouseEvent me) {
    }

    /**
     * Handles mouseReleased (not in use)
     */
    @Override
    public void mouseReleased(MouseEvent me) {
    }

    /**
     * Handles mouseEntered (not in use)
     */
    @Override
    public void mouseEntered(MouseEvent me) {
    }

    /**
     * Handles mouseExited (not in use)
     */
    @Override
    public void mouseExited(MouseEvent me) {
    }

    /**
     * Handles mouseDragged
     */
    @Override
    public void mouseDragged(MouseEvent me) {
        gsm.mouseDragged(me);
    }

    /**
     * Handles mouseMoved
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        gsm.mouseMoved(me);
    }

}

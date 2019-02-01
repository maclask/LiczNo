/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.GameStates;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import liczno.Button;
import liczno.GamePanel;
import static liczno.GamePanel.messages;
import liczno.Images;
import liczno.Main;
import liczno.enterties.Player;

/**
 * State of game displaying menu
 *
 * @see GameState
 * @author Maciek
 */
public class MenuState extends GameState {

    private String[] options;
    private int currentSel = 0;
    private Button topbox;
    private Point click, hover;
    private Button[] buttons;
    /**
     * Inicating is menu music is playing
     */
    public static boolean menuAudioPlay = false;

    /**
     * Creates MenuState
     *
     * @param gsm Game state manager
     * @see GameStateManager
     */
    public MenuState(GameStateManager gsm) {
        super(gsm);
        options = new String[]{messages.getString("start"),
            messages.getString("scores"),
            messages.getString("settings"),
            messages.getString("help"),
            messages.getString("about"),
            messages.getString("exit")};
        buttons = new Button[options.length];
        for (int i = 0; i < options.length; i++) {
            buttons[i] = new Button(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 9 * 3 + 80 * i,
                    GamePanel.WIDTH / 3, GamePanel.HEIGHT / 12, new Color(0, 0, 0, 0), Color.WHITE,
                    50, options[i], true);
        }

        topbox = new Button(GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10,
                GamePanel.WIDTH / 2, GamePanel.HEIGHT / 6, 150, "MENU");

        Player.reset();
        if (!menuAudioPlay) {
            Main.audio.playLevelMp3(5);
            menuAudioPlay = true;
        }
    }

    @Override
    public void init() {
        Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    @Override
    public void tick() {
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(Images.bg, 0, 0, Images.bgWidth, Images.bgHeight, null);
        g.drawImage(Images.logo, GamePanel.WIDTH / 9, GamePanel.HEIGHT / 5 * 2, 300, 300, null);

        topbox.drawButton(g);

        for (int i = 0; i < options.length; i++) {
            if (i == currentSel) {
                buttons[i].txtColor = Color.WHITE;
                buttons[i].bgColor = new Color(243, 192, 38);
            } else {
                buttons[i].txtColor = Color.WHITE;
                buttons[i].bgColor = new Color(0, 0, 0, 0);
            }
            buttons[i].drawButton(g);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_DOWN) {
            currentSel++;
            if (currentSel >= options.length) {
                currentSel = 0;
            }
        } else if (k == KeyEvent.VK_UP) {
            currentSel--;
            if (currentSel < 0) {
                currentSel = options.length - 1;
            }
        }

        if (k == KeyEvent.VK_ENTER) {
            checkClick(currentSel);

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
        for (int i = 0; i < options.length; i++) {
            if (buttons[i].contains(hover)) {
                currentSel = i;
                Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                hand = true;
                break;
            }
        }
        if (!hand) {
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    private void checkClick(int currentSel) {

        switch (currentSel) {
            //start
            case 0:
                Main.audio.stopLevelMp3();
                menuAudioPlay = false;
                gsm.states.push(new EndGameState(gsm, 0, Player.isDead));
                break;
            //help
            case 1:
                // Main.audio.playLevelMp3a();
                gsm.states.push(new ShowScoreState(gsm));
                break;
            //help
            case 2:
                // Main.audio.playLevelMp3a();
                gsm.states.push(new SettingsState(gsm));
                break;

            case 3:
                // Main.audio.playLevelMp3a();
                gsm.states.push(new HelpState(gsm));
                break;
            //exit
            case 4:
                gsm.states.push(new AboutState(gsm));
                break;
            case 5:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

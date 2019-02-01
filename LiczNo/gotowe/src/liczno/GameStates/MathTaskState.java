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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
 * State of game displaying math task
 *
 * @see GameState
 * @author Maciek
 */
public class MathTaskState extends GameState {

    private List<Integer> input;
    private Button topButton;
    private Rectangle topbox, inputbox, closebox, okbox, taskbox;
    private boolean solved = false;
    private int width = 400, height = 600;
    private int solution, ans = 0;
    private int firstNumber, secondNumber, thirdNumber;
    private String sfirstOperator, ssecondOperator, task, inputAns = "", topText, timeText, okText;

    private Point click = new Point(0, 0), hover = new Point(0, 0);

    private int time = 12;
    private long lasttime = System.nanoTime();

    Random generator;

    /**
     * Creates MathTaskState
     *
     * @param gsm GameStateManager
     * @see GameStateManager
     */
    public MathTaskState(GameStateManager gsm) {
        super(gsm);

        topText = GamePanel.messages.getString("giveans");
        okText = GamePanel.messages.getString("ok");
        topButton = new Button(376, 203, 284, 46, Color.WHITE, Color.BLACK, 50, topText);

    }

    public void init() {

        input = new ArrayList<>();
        generator = new Random();
        do {
            task();
        } while (solution < 0 || solution > 150);

        try {

            Main.audio.play(1, true);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(MathTaskState.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void tick() {

        if (System.nanoTime() - lasttime >= 1000000000) {
            time--;
            lasttime = System.nanoTime();
        }
        if (time < 0) {
            time = 0;
            close();
        }

        inputAns = "";
        for (int k : input) {
            inputAns += Character.toString((char) k);
        }
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(GamePanel.WIDTH / 2 - width / 2, GamePanel.HEIGHT / 2 - height / 2, width, height);

        Font f2 = Images.f.deriveFont(100F);
        g.setFont(f2);
        g.setColor(Color.BLACK);

        topButton.drawButton(g);
        inputbox = new Rectangle(370, 414, 284, 86);
        g.setColor(new Color(191, 191, 191));
        g.fillRect(370, 414, 284, 86);

        taskbox = new Rectangle(376, 300, 284, 46);
        g.setColor(Color.BLACK);
        drawCenteredString(g, task, taskbox, f2);

        closebox = new Rectangle(650, 120, 30, 30);
        g.drawImage(Images.close, 650, 120, null);

        g.setColor(Color.BLACK);
        drawCenteredString(g, inputAns, inputbox, f2);

        f2 = f2.deriveFont(40F);
        g.setFont(f2);

        g.drawString(String.valueOf(time), 350, 150);

        okbox = new Rectangle(440, 526, 144, 75);
        g.setColor(new Color(50, 120, 54));
        g.fillRect(440, 526, 144, 75);
        g.setColor(Color.WHITE);
        f2 = Images.f.deriveFont(50F);
        drawCenteredString(g, okText, okbox, f2);

    }

    public void keyPressed(int k) {
        if ((k >= 48 && k <= 57) || (k >= 96 && k <= 105)) {
            if (k >= 96) {
                k -= 48;
            }
            if (input.size() < 5) {
                input.add(k);
            }
        }
        if (k == KeyEvent.VK_BACK_SPACE) {
            if (input.size() > 0) {
                input.remove(input.size() - 1);
            }
        };
        if (k == KeyEvent.VK_ENTER) {
            solved = enter();
        } else if (k == KeyEvent.VK_ESCAPE) {
            enter();
        }
    }

    public void keyReleased(int k) {

    }

    /**
     * If clicked close button go to enter method
     *
     * @return solved boolean
     */
    public boolean close() {
        enter();
        return solved;
    }

    /**
     * Check input
     *
     * @return true if correct
     */
    public boolean enter() {
        ans = 0;
        for (int k : input) {
            ans = 10 * ans + k - 48;
        }
        if (ans == solution) {

            solved = true;
            Player.bombTouched = false;
            Player.solved = true;
            Player.score += time;
            Main.audio.stop();
            Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            try {
                Main.audio.play(2, false);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(MathTaskState.class.getName()).log(Level.SEVERE, null, ex);
            }
            gsm.states.pop();

        } else {
            Player.bombTouched = false;
            Player.solved = true;
            Main.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            Main.audio.stop();
            Main.audio.stopLevelMp3();
            try {
                Main.audio.play(4, false);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(MathTaskState.class.getName()).log(Level.SEVERE, null, ex);
            }

            Player.isDead = true;
            gsm.states.pop();

        }

        return solved;
    }

    private String sign(int a) {
        switch (a) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            default:
                System.out.println("Error generating math task");
                return "";
        }
    }

    private void task() {
        firstNumber = generator.nextInt(12) + 1;
        secondNumber = generator.nextInt(12) + 1;
        thirdNumber = generator.nextInt(12) + 1;

        sfirstOperator = sign(generator.nextInt(3));
        ssecondOperator = sign(generator.nextInt(3));

        task = String.valueOf(firstNumber)
                + sfirstOperator
                + String.valueOf(secondNumber)
                + ssecondOperator
                + String.valueOf(thirdNumber);

        if ("+".equals(sfirstOperator) && "+".equals(ssecondOperator)) {
            solution = firstNumber + secondNumber + thirdNumber;
        } else if ("+".equals(sfirstOperator) && "-".equals(ssecondOperator)) {
            solution = firstNumber + secondNumber - thirdNumber;
        } else if ("+".equals(sfirstOperator) && "*".equals(ssecondOperator)) {
            solution = firstNumber + secondNumber * thirdNumber;
        } else if ("-".equals(sfirstOperator) && "+".equals(ssecondOperator)) {
            solution = firstNumber - secondNumber + thirdNumber;
        } else if ("-".equals(sfirstOperator) && "-".equals(ssecondOperator)) {
            solution = firstNumber - secondNumber - thirdNumber;
        } else if ("-".equals(sfirstOperator) && "*".equals(ssecondOperator)) {
            solution = firstNumber - secondNumber * thirdNumber;
        } else if ("*".equals(sfirstOperator) && "+".equals(ssecondOperator)) {
            solution = firstNumber * secondNumber + thirdNumber;
        } else if ("*".equals(sfirstOperator) && "-".equals(ssecondOperator)) {
            solution = firstNumber * secondNumber - thirdNumber;
        } else if ("*".equals(sfirstOperator) && "*".equals(ssecondOperator)) {
            solution = firstNumber * secondNumber * thirdNumber;
        }
        System.out.println(solution);

    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = new Point(e.getX(), e.getY());
        if (okbox.contains(click)) {
            enter();
        } else if (closebox.contains(click)) {
            close();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hover = new Point(e.getX(), e.getY());
        boolean hand = false;
        if (okbox.contains(hover) || closebox.contains(hover)) {
            Main.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hand = true;
        }
        if (!hand) {
            Main.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

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
package liczno;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Creates button that handle text
 *
 * @author Maciek
 */
public class Button extends Rectangle {

    private Rectangle button;
    private int x, y, width, height;
    /**
     * Color of button bg/text
     */
    public Color bgColor, txtColor;
    /**
     * Font of button
     */
    public Font font;
    /**
     * Text inside button
     */
    public String text;
    /**
     * Specifies if text must be centerd
     */
    public boolean center = true;

    /**
     * Takes position of button (x,y), its width and height, color of text and
     * background, fontsize of text and text in button.
     *
     * @param x x position
     * @param y y position
     * @param width width
     * @param height height
     * @param bgColor bg color
     * @param txtColor text color
     * @param fontsize font size
     * @param text text inside
     */
    public Button(int x, int y, int width, int height, Color bgColor, Color txtColor, int fontsize, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.txtColor = txtColor;
        this.font = Images.f.deriveFont((float) fontsize);
        this.text = text;
        this.setBounds(x, y, width, height);
        init();
    }

    /**
     * Takes position of button (x,y), its width and height, color of text and
     * background, fontsize of text and text in button. Takes also boolean if
     * text must be centered.
     *
     * @param x x position
     * @param y y position
     * @param width width
     * @param height height
     * @param bgColor bg color
     * @param txtColor text color
     * @param fontsize font size
     * @param text text inside
     * @param center if text must be center
     */
    public Button(int x, int y, int width, int height, Color bgColor, Color txtColor, int fontsize, String text, boolean center) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.txtColor = txtColor;
        this.font = Images.f.deriveFont((float) fontsize);
        this.text = text;
        this.center = center;
        this.setBounds(x, y, width, height);
        init();
    }

    /**
     * Takes position of button (x,y), its width and height, fontsize of text
     * and text in button. Takes also boolean if text must be centered. Color of
     * text and background are set to transparent and white.
     *
     * @param x x position
     * @param y y position
     * @param width width
     * @param height height
     * @param fontsize font size
     * @param text text inside
     * @param center if text must be center
     */

    public Button(int x, int y, int width, int height, int fontsize, String text, boolean center) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = new Color(0, 0, 0, 0);
        this.txtColor = Color.WHITE;
        this.font = Images.f.deriveFont((float) fontsize);
        this.text = text;
        this.setBounds(x, y, width, height);
        this.center = center;
        init();
    }

    /**
     * Takes position of button (x,y), its width and height, fontsize of text
     * and text in button. Color of text and background are set to transparent
     * and white.
     *
     * @param x x position
     * @param y y position
     * @param width width
     * @param height height
     * @param fontsize font size
     * @param text text inside
     */
    public Button(int x, int y, int width, int height, int fontsize, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = new Color(0, 0, 0, 0);
        this.txtColor = Color.WHITE;
        this.font = Images.f.deriveFont((float) fontsize);
        this.text = text;
        this.setBounds(x, y, width, height);
        init();
    }

    private void init() {
        button = new Rectangle(x, y, width, height);
    }

    /**
     * Draws button
     *
     * @param g is object of Graphics class.
     */
    public void drawButton(Graphics g) {
        g.setFont(font);
        g.setColor(bgColor);
        g.fillRect(x, y, width, height);
        g.setColor(txtColor);
        if (center) {
            drawCenteredString(g, text, button, font);
        } else {
            drawString(g, text, button, font);
        }
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private void drawString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

}

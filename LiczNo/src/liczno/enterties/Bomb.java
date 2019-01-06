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
package liczno.enterties;

import java.awt.Graphics;
import java.awt.Rectangle;
import liczno.Images;

/**
 * Create single bomb
 *
 * @author Maciek
 */
public class Bomb extends Rectangle {

    /**
     * Bomb width
     */
    public static final int bombWidth = 50;
    /**
     * Bomb height
     */
    public static final int bombHeight = 50;

    /**
     * Create bomb and set boudns depending of x, y, blockWidth, blockHeight
     *
     * @param x x-positon of block
     * @param y y-positon of block
     */
    public Bomb(int x, int y) {
        setBounds(x, y, bombWidth, bombHeight);

    }

    /**
     * Draw bomb
     *
     * @param g object of Graphics class
     */
    public void draw(Graphics g) {
        g.drawImage(Images.bomb, x, y, null);
    }

}

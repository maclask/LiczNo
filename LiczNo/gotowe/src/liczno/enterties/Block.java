/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.enterties;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import liczno.Images;

/**
 * Create single block
 * @author Maciek
 */
public class Block extends Rectangle{
    
    /**
     * Block width
     */
    public static final int blockWidth=205;
    /**
     * Block height
     */
    public static final int blockHeight=85;

    /**
    * Create block and set boudns depending of x, y, blockWidth, blockHeight
    * @param x x-positon of block
    * @param y y-positon of block
    */
    public Block(int x, int y){
        setBounds(x,y,blockWidth,blockHeight);
    
    }
    
    /**
     * Draw block
     *
     * @param g object of Graphics class
     */
    public void draw(Graphics g){
        g.drawImage(Images.block, x, y, null);
    }
}

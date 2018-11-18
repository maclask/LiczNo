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
 *
 * @author Maciek
 */
public class Block extends Rectangle{
    
    public static final int blockWidth=297;
    public static final int blockHeight=150;


    public Block(int x, int y){
        setBounds(x,y,blockWidth,blockHeight);
    }
    
    public void draw(Graphics g){
        g.drawImage(Images.blocks[0], x, y, null);
//        g.drawImage(Images.blocks[0], 0, 618, null);
//        g.drawImage(Images.blocks[0], 270, 618, null);
//        g.drawImage(Images.blocks[0], 540, 618, null);
//        g.drawImage(Images.blocks[0], 810, 618, null);
//         g.drawImage(Images.blocks[0], 500, 518, null);
        
    }
}

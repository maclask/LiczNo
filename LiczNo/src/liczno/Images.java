/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Maciek
 */
public class Images {
      
    public static BufferedImage bg, logo, player, block, icon, bomb;
    public static int bgWidth, bgHeight, logoWidth, logoHeight;
    
    public Images(){
       //blocks = new BufferedImage[1];
        
        try {
            block= ImageIO.read(getClass().getResourceAsStream("images/block2.png"));
            bg = ImageIO.read(getClass().getResource("images/bg.png"));
            logo = ImageIO.read(getClass().getResource("images/logo.png"));
            player = ImageIO.read(getClass().getResource("images/player.png"));
            icon = ImageIO.read(getClass().getResource("images/logo.small.png"));
            bomb = ImageIO.read(getClass().getResource("images/bomb.png"));
        } 
        catch (IOException e) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, e);
        }
       bgWidth = bg.getWidth();
       bgHeight = bg.getHeight();  
       logoWidth = logo.getWidth();
       logoHeight = logo.getHeight(); 
    }
}

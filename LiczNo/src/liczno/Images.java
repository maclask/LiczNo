/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static liczno.Main.f;

/**
 * Loads images used in game and font
 *
 * @author Maciek
 */
public class Images {

    /**
     * Array of players animations
     */
    public static BufferedImage[] playerwalking, playerjumping, playerdie, playeridle;
    /**
     * Frames of players animations
     */
    public static final int PW = 20, PJ = 30, PI = 16, PD = 30;
    /**
     * BufferedImage used in game
     */
    public static BufferedImage bg, logo, block, icon, bomb, close, help;
    /**
     * Dimmension of image
     */
    public static int bgWidth, bgHeight, logoWidth, logoHeight;
    /**
     * Font used in game
     */
    public static Font f;

    /**
     * Loads images and font
     */
    public Images() {
        //blocks = new BufferedImage[1];

        try {
            block = ImageIO.read(getClass().getResourceAsStream("images/block.png"));
            bg = ImageIO.read(getClass().getResource("images/bg.png"));
            logo = ImageIO.read(getClass().getResource("images/logo.png"));
            bomb = ImageIO.read(getClass().getResource("images/bomb.png"));
            close = ImageIO.read(getClass().getResource("images/close.png"));
            help = ImageIO.read(getClass().getResource("images/help.png"));
        } catch (IOException e) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, e);
        }
        bgWidth = bg.getWidth();
        bgHeight = bg.getHeight();
        logoWidth = logo.getWidth();
        logoHeight = logo.getHeight();

        playerwalking = new BufferedImage[PW];
        playerjumping = new BufferedImage[PJ];
        playerdie = new BufferedImage[PD];
        playeridle = new BufferedImage[PI];

        for (int i = 0; i < PW; i++) {
            try {
                playerwalking[i] = ImageIO.read(getClass().getResource("images/player/Walk (" + (i + 1) + ").png"));
            } catch (IOException ex) {
                Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < PJ; i++) {
            try {
                playerjumping[i] = ImageIO.read(getClass().getResource("images/player/Jump (" + (i + 1) + ").png"));
            } catch (IOException ex) {
                Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < PD; i++) {
            try {
                playerdie[i] = ImageIO.read(getClass().getResource("images/player/Dead (" + (i + 1) + ").png"));
            } catch (IOException ex) {
                Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < PI; i++) {
            try {
                playeridle[i] = ImageIO.read(getClass().getResource("images/player/Idle (" + (i + 1) + ").png"));
            } catch (IOException ex) {
                Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        InputStream is = getClass().getResourceAsStream("font/Staatliches-Regular.ttf");
        //File file = new java.io.File("src/liczno/images/Staatliches-Regular.ttf");
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(f);
        } catch (FontFormatException | IOException e) {
            System.out.println(e);

        }
    }
}

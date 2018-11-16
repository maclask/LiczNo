/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 *
 * @author Maciek
 */
public class Main {

    public static int WIDTH=400;
    public static int HEIGHT=400;
    public static Font f;
    
    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
            JFrame frame = new JFrame("LiczNo!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLayout(new BorderLayout());
            frame.add(new GamePanel(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setPreferredSize(new Dimension(600,600));
            frame.setVisible(true);
            Main a = new Main();
            frame.setIconImage(ImageIO.read(new File("src\\liczno\\GameStates\\Images/logo.small.png")));
            
               try
        {
            f = Font.createFont(Font.TRUETYPE_FONT,new java.io.File("src\\liczno\\GameStates\\Images/SF-Pro-Display-Heavy.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(f);
        }
        catch(FontFormatException | IOException e)
        {
            System.out.println(e);
        }
           
    }
    
}

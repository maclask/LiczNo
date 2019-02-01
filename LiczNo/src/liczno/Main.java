/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 * Main class. Creates frame.
 *
 * @author Maciek
 */
public class Main {

    /**
     * Font used in game
     */
    public static Font f;

    /**
     * Jframe of game
     */
    public static JFrame frame;

    /**
     * Loads audio.
     *
     * @see Audio
     */
    public static Audio audio;

    /**
     * Creates new Jframe. Loads Audio.
     *
     * @param args (not used)
     * @throws LineUnavailableException line is unabailble
     * @throws IOException ioexception
     * @throws UnsupportedAudioFileException unsuported audio file
     */
    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        audio = new Audio();

        frame = new JFrame("LiczNo!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(new GamePanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setVisible(true);
        frame.setIconImage(Images.bomb);

    }

}

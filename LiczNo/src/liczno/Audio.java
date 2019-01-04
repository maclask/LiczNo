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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.player.Player;

/**
 *
 * @author Maciek
 */
public class Audio {

    private InputStream[] bufferedIn;
    private Clip clip;
    private AudioInputStream[] ais;
    public Thread mp3;
    public boolean sfxOn, musicOn;

    String[] url = {"sfx/solved.wav", "sfx/time.wav", "sfx/correct.wav", "sfx/nextlevel.wav", "sfx/wrong.wav", "sfx/click.wav"};

    public Audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        clip = AudioSystem.getClip();
        ais = new AudioInputStream[url.length];
        bufferedIn = new InputStream[url.length];
        for (int i = 0; i < url.length; i++) {
            bufferedIn[i] = new BufferedInputStream(getClass().getResourceAsStream(url[i]));
            ais[i] = AudioSystem.getAudioInputStream(bufferedIn[i]);
        }
        sfxOn = true;
        musicOn = true;
    }

    public void play(int track, boolean loop) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        System.out.println("if sfx");
        if (sfxOn) {
            System.out.println("if open");
            if (clip.isOpen()) {
                System.out.println("syop");
                clip.stop();
                System.out.println("close");
                clip.close();
            }System.out.println("if aval");
            if (ais[track].available() == 0) {
                System.out.println("reset");
                ais[track].reset();
            }
            System.out.println("reset");
            clip.open(ais[track]);
            System.out.println("open track");
            clip.start();
            System.out.println("start");
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            System.out.println("loop");
        }
    }

    public void stop() {
        if (sfxOn) {
            clip.stop();
            clip.close();
        }
    }

    public void playLevelMp3(int level) {
        if (musicOn) {
            final int parameter = level;

            mp3 = new Thread(new Runnable() {
                private Player player;
                InputStream is;
                int p = parameter;
                final static String FOLDER = "music/";
                String[] track = {"Dog_and_Pony_Show.mp3", "Hidden_Agenda.mp3", "The_Curious_Kitten.mp3", "Dog_Park.mp3", "Brain_Trust.mp3"};
                String path = FOLDER;

                boolean running = true;

                public void stopa() {
                    this.player.close();
                }

                void terminate() {
                    running = false;
                }

                public void run() {
//                    while (running) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
                    if (is != null) {
                        this.player.close();
                    }
                    try {
                        switch (p) {
                            case 1:
                                path += track[0];
                                break;
                            case 2:
                                path += track[1];
                                break;
                            case 3:
                                path += track[2];
                                break;
                            case 4:
                                path += track[3];
                                break;
                            case 5:
                                path += track[4];
                                break;
                            case -1:
                                this.player.close();
                                break;
                            default:
                                path += track[0];
                        }
                        is = getClass().getResourceAsStream(path);
                        BufferedInputStream bis = new BufferedInputStream(is);

                        this.player = new Player(bis);

                        this.player.play();
                        if (this.player.isComplete()) {
                            this.player.play();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            mp3.start();
        }
    }

    public void playLevelMp3a() {
        if (musicOn) {
            mp3.stop();
        }
    }
}

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
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.player.Player;

/**
 * Class loads and plays audio files
 *
 * @author Maciek
 */
public class Audio {

    private InputStream[] bufferedIn;
    private Clip[] clip;
    private AudioInputStream[] ais;
    /**
     * Thread to play mp3 files
     */
    public Thread mp3;
    /**
     * Boolean specifies if sfx or music is on
     */
    public boolean sfxOn, musicOn;

    private int track = 0;
    String[] url = {"sfx/solved.wav", "sfx/time.wav", "sfx/correct.wav", "sfx/nextlevel.wav", "sfx/wrong.wav"};

    /**
     * Load audio files
     *
     * @throws LineUnavailableException line is unabailble
     * @throws IOException ioexception
     * @throws UnsupportedAudioFileException unsuported audio file
     */
    public Audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        ais = new AudioInputStream[url.length];
        bufferedIn = new InputStream[url.length];
        clip = new Clip[url.length];
        for (int i = 0; i < url.length; i++) {
            bufferedIn[i] = new BufferedInputStream(getClass().getResourceAsStream(url[i]));
            ais[i] = AudioSystem.getAudioInputStream(bufferedIn[i]);
            clip[i] = AudioSystem.getClip();
            clip[i].open(ais[i]);
        }
        sfxOn = true;
        musicOn = true;
    }

    /**
     * Plays audio track
     *
     * @param track specifies track number from Sritng url array
     * @param loop specifies if track must loop itself
     * @throws LineUnavailableException line is unabailble
     * @throws IOException ioexception
     * @throws UnsupportedAudioFileException unsuported audio file
     */
    public void play(int track, boolean loop) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.track = track;
        if (sfxOn) {
            clip[track].setMicrosecondPosition(0);
            clip[track].start();
            if (loop) {
                clip[track].loop(Clip.LOOP_CONTINUOUSLY);
            }
        }

    }

    /**
     * Stops playing audio track
     */
    public void stop() {
        if (sfxOn) {
            clip[track].stop();
        }
    }

    /**
     * Plays mp3 file. Creates new Thead to handle playing.
     *
     * @param level specifies track from String track array which is associated
     * with level number.
     */
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

    /**
     * Stops playing mp3 by stoping Thread created in playLevelMp3
     */
    public void stopLevelMp3() {
        if (musicOn) {
            mp3.stop();
        }
    }
}

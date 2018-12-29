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
    
    
    private InputStream build, solved; 
    private Clip clip;
    private AudioInputStream ais, sol,lv1;
    Thread mp3;

    
    public Audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
//       build = getClass().getResourceAsStream("images/DM-CGS-04_1.wav");
//       solved = getClass().getResourceAsStream("images/correct.wav");
//
//
//     clip = AudioSystem.getClip();
//        // getAudioInputStream() also accepts a File or InputStream
//     ais = AudioSystem.getAudioInputStream(build);
//      sol = AudioSystem.getAudioInputStream(solved);
        //lv1 = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("images/Dog_and_Pony_Show.wav"));
        
        
        
    }
    
    public void playMain() throws LineUnavailableException, IOException{
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
        public void playLv1() throws LineUnavailableException, IOException{
          clip.open(lv1);  
          FloatControl audioControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
             audioControl.setValue(-15.0f); //decrease volume 5 decibels
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
     public void playSol() throws LineUnavailableException, IOException{
        clip.open(sol);
        clip.start();
    }
    public void stopMain(){
        clip.close();
    }
    
    public void playLevelMp3(int level){
        final int parameter = level;
        
        mp3 = new Thread(new Runnable(){
            private Player player;
            InputStream fis;
            int p = parameter;
            
            public void run(){
                
                if(fis!=null)this.player.close();
                try {
                        switch(p){
                            case 1:
                               // InputStream input = getClass().getResourceAsStream("ListStopWords.txt");
                                fis = getClass().getResourceAsStream("images/Dog_and_Pony_Show.mp3");
                                break;
                            case 2:
                                fis = getClass().getResourceAsStream("images/Hidden_Agenda.mp3");
                                break;
                            case 3:
                                fis = getClass().getResourceAsStream("images/The_Curious_Kitten.mp3");
                                break;
                            case 4:
                                fis = getClass().getResourceAsStream("images/Dog_Park.mp3");
                                break;
                            case 5:
                                fis = getClass().getResourceAsStream("images/Dog_and_Pony_Show.mp3");
                                break;
                            case -1:
                                this.player.close();
                                break;
                            default:
                                fis = getClass().getResourceAsStream("images/Dog_and_Pony_Show.mp3");
                        }
                       
                        BufferedInputStream bis = new BufferedInputStream(fis);

                        this.player = new Player(bis);
              
                        this.player.play();
                        
                        if (this.player.isComplete()) {
                                this.player.play();
                        }
                } 
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("dupa");
                }
              }
            
        });
        mp3.start();
    }
    
    public void playLevelMp3a(){
        mp3.stop();
    
    }
}

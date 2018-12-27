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

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Maciek
 */
public class Audio {
    
    private Clip clip;
    private AudioInputStream ais;
    
    public Audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
//     File url = new File("E:\\Users\\Maciej\\Desktop\\AF\\Designer Sound FX\\Abstract (100)\\Beats (20)\\Casino.wav");
//     clip = AudioSystem.getClip();
//        // getAudioInputStream() also accepts a File or InputStream
//     ais = AudioSystem.getAudioInputStream(url);

    }
    
    public void playMain() throws LineUnavailableException, IOException{
       // clip.open(ais);
       // clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stopMain(){
       // clip.close();
    }
    
}

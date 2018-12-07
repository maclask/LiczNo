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
package liczno.enterties;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import liczno.Images;

/**
 *
 * @author Maciek
 */
public class Bombs {
    
    
    private int amount;
    private ArrayList<Block> blocksList;
    private ArrayList<Bombs> bombsList;
    Random generator;
    private int[] selectedBlock, selectedX;
    
    public Bombs(int amount, ArrayList<Block> blocksList){
        
        this.amount = amount;
        this.blocksList = blocksList;
        bombsList = new ArrayList<>();
        
        selectedBlock = new int[amount];
        selectedX = new int[amount];
        init();
        
    }
    
    private void init(){
           for(int i=0; i<amount; i++){
               generator = new Random();
            selectedBlock[i] = generator.nextInt(blocksList.size());
            selectedX[i] = generator.nextInt((int)blocksList.get(selectedBlock[i]).getWidth());
            System.out.println(selectedBlock[i]);
            System.out.println(selectedX[i]);
                       
        }
    }
    
    public void draw(Graphics g){
        for(int i=0; i<amount; i++){
            g.drawImage(Images.bomb, (int)blocksList.get(selectedBlock[i]).getX()+selectedX[i], (int)blocksList.get(selectedBlock[i]).getY()-Images.bomb.getHeight(), null);
        }
    }
}

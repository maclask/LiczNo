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
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import liczno.GamePanel;
import liczno.Images;

/**
 *
 * @author Maciek
 */
public class Bombs {
    
    
    private int amount;
    private ArrayList<Block> blocksList;
    private ArrayList<Bomb> bombsList;
    Bomb bomb;
    Random generator;
    private List<Integer> selectedBlock;
    private List<Integer> selectedX;
    private int newValue;
    private Player player;
    
    public Bombs(int amount, ArrayList<Block> blocksList, Player player){
        
        this.amount = amount;
        this.blocksList = blocksList;
        bombsList = new ArrayList<>();
        selectedBlock = new ArrayList<>();
        selectedX = new ArrayList<>();
        this.player = player;
        init();
        
        
    }
    
    private void init(){
        int tempx;
        if(amount>blocksList.size()) amount = blocksList.size()-3;
        generator = new Random();
           for(int i=0; i<amount; i++){
            
            newValue = generator.nextInt(blocksList.size());
            if(selectedBlock.contains(newValue) || blocksList.get(newValue).getX() < player.x+player.width)
                i-- ;
            else {
                selectedBlock.add(newValue);

                    do{
                        tempx = generator.nextInt((int)blocksList.get(selectedBlock.get(i)).getWidth()-Bomb.bombWidth)
                            +(int)blocksList.get(selectedBlock.get(i)).getX();
                    }while(tempx>GamePanel.WIDTH-Images.bomb.getWidth());

                selectedX.add(tempx);
                
                bomb = new Bomb(selectedX.get(i),
                        (int)blocksList.get(selectedBlock.get(i)).getY()-Images.bomb.getHeight());
                bombsList.add(bomb);
            }                      
        }
    }
    
    public void draw(Graphics g){
        for(int i=0; i<bombsList.size(); i++){
            bombsList.get(i).draw(g);
        }
    }
    
     public ArrayList<Bomb> getBombs(){
        return bombsList;
    }
}

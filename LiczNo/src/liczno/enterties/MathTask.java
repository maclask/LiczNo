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
import java.util.List;
import javax.swing.JTextField;
import liczno.Main;

/**
 *
 * @author Maciek
 */
public class MathTask {
   
    
    private List<Integer> input;
    private boolean solved = false;
     
    public MathTask(){
        init();
    }
    
    private void init(){
        input = new ArrayList<>();
              
    }      
       
    
    public void draw(Graphics g){
        g.drawRect(100, 100, 200, 500);
        int i=0;
        for(int k : input){
            g.drawString(Character.toString ((char) k), 100+i, 100);
            i+=20;
            if(k==57) solved=true;
        }
    }
    
    public void input(int k){
        input.add(k);
    }
    
    public void deleteLastOne(){
        input.remove(input.size()-1);
    }
    
    public boolean enter(){
        return solved;
    }
}

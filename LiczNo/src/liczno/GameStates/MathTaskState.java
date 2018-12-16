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
package liczno.GameStates;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import liczno.GamePanel;
import liczno.Main;
import liczno.enterties.Player;

/**
 *
 * @author Maciek
 */
public class MathTaskState extends GameState{
   
    
    private List<Integer> input;
    private boolean solved = false;
    private int width=400, height = 600;
    private int difficulity, solution, ans =0; 
    private int firstNumber, secondNumber, thirdNumber;
    private String sfirstOperator, ssecondOperator, task;
            
    Random generator;
    
    public MathTaskState(GameStateManager gsm) {
        super(gsm);
    }
    
    public void init(){
        input = new ArrayList<>();    
        generator = new Random();
        task();
        while(solution<0)task();
        
        
     }      
       
 

    public void tick() {
    
    }
    
    public void draw(Graphics g){
        

        g.setColor(Color.WHITE);
        g.fillRect(GamePanel.WIDTH/2-width/2,GamePanel.HEIGHT/2-height/2,width,height);
        
        g.setColor(new Color(191,191,191));
        g.fillRect(370,414,284,86);
        
        g.setColor(new Color(50,120,54));
        g.fillRect(440,526,144,75);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font f2 = liczno.Main.f.deriveFont(100F);
        g2d.setFont(f2);
        g2d.setColor(Color.BLACK);
        g.drawString(task, 376, 263);
        int i=0;
        for(int k : input){
            g.drawString(Character.toString ((char) k), 395+i, 520);
            i+=50;
        }
    }

    public void keyPressed(int k) {
        if(     k == KeyEvent.VK_0 ||
                k == KeyEvent.VK_1 ||
                k == KeyEvent.VK_2 ||
                k == KeyEvent.VK_3 ||
                k == KeyEvent.VK_4 || 
                k == KeyEvent.VK_5 ||
                k == KeyEvent.VK_6 ||
                k == KeyEvent.VK_7 || 
                k == KeyEvent.VK_8 || 
                k == KeyEvent.VK_9  ) {
             input.add(k);
        }
        if(k == KeyEvent.VK_BACK_SPACE) if(input.size()>0)input.remove(input.size()-1);;
        if(k == KeyEvent.VK_ENTER) solved = enter();
    }


    public void keyReleased(int k) {
       
    } 
    
    
    public boolean enter(){
        ans=0;
        for(int k : input){

            ans = 10*ans + k-48;
          
        }
            if(ans==solution) 
            {solved=true;
            Player.bombTouched=false;
            Player.solved=true;
            gsm.states.pop();
            
            }
        return solved;
    }

    private String sign(int a){
        switch(a){
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            default:
                System.out.println("Error generating math task");
                return "";
        }
    }
     private void task(){
         firstNumber = generator.nextInt(12)+1;
        secondNumber = generator.nextInt(12)+1;
        thirdNumber = generator.nextInt(12)+1;
        
        sfirstOperator = sign(generator.nextInt(3));
        ssecondOperator = sign(generator.nextInt(3));
        
        task = String.valueOf(firstNumber)+
                sfirstOperator+
                String.valueOf(secondNumber)+
                ssecondOperator+
                String.valueOf(thirdNumber);
        
        if("+".equals(sfirstOperator)&&"+".equals(ssecondOperator))
        solution = firstNumber + secondNumber + thirdNumber;
        
        else if("+".equals(sfirstOperator)&&"-".equals(ssecondOperator))
            solution = firstNumber + secondNumber - thirdNumber;
        
        else if("+".equals(sfirstOperator)&&"*".equals(ssecondOperator))
            solution = firstNumber + secondNumber * thirdNumber;
        
        else if("-".equals(sfirstOperator)&&"+".equals(ssecondOperator))
            solution = firstNumber - secondNumber + thirdNumber;
        
        else if("-".equals(sfirstOperator)&&"-".equals(ssecondOperator))
            solution = firstNumber - secondNumber - thirdNumber;
        
        else if("-".equals(sfirstOperator)&&"*".equals(ssecondOperator))
            solution = firstNumber - secondNumber * thirdNumber;
        
        else if("*".equals(sfirstOperator)&&"+".equals(ssecondOperator))
            solution = firstNumber * secondNumber + thirdNumber;
        
        else if("*".equals(sfirstOperator)&&"-".equals(ssecondOperator))
            solution = firstNumber * secondNumber - thirdNumber;
        
        else if("*".equals(sfirstOperator)&&"*".equals(ssecondOperator))
            solution = firstNumber * secondNumber * thirdNumber;
        System.out.println(solution);
        
        
     }
}

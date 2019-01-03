/*
 * The MIT License
 *
 * Copyright 2019 Maciek.
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

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Maciek
 */
class Score implements Comparable {
    private int score;
    private String name;
    public Score(String name, int score){
        this.score = score;
        this.name = name;
    }
    
    public int compareTo(Object s) {
        int compareScore=((Score)s).getScore();
        /* For Ascending order*/
        return compareScore - this.score;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
    
    private int getScore(){
        return score;
    }
    
     @Override
    public String toString() {
        return name + " " + score;
    }
    
}

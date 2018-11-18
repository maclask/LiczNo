/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.enterties;

import java.awt.Point;

/**
 *
 * @author Maciek
 */
public class Collision {
    
    public static boolean playerBlock(Point p, Block b){
        return b.contains(p);
    }
}

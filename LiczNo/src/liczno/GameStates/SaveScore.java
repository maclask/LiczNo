package liczno.GameStates;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import liczno.enterties.Block;


/**
 *
 * @author Maciek
 */
public class SaveScore {
    
    private String path;
    private boolean sorted;

    
    ArrayList<Score> scores;
    
    public SaveScore() {
        
        path = System.getProperty("user.dir")+"\\licznoscores";
        System.out.println(path);
        scores = new ArrayList<>();
        sorted = false;
    }
    
    public ArrayList readScore() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String line = null;
            
                
            try {
                while( (line = br.readLine())!=null){
                    String[] score = line.split("\\s+");
                    scores.add(new Score(score[0], Integer.parseInt(score[1])));
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(SaveScore.class.getName()).log(Level.SEVERE, null, ex);
            }
            return scores;

        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(SaveScore.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    public void writeScore(String name, int score) throws IOException{
        if("".equals(name))name="anonim";
        File out = new File(path);
        String write = null;
        if(out.exists() && !out.isDirectory()) { 
            
         write= "\n" + name + " " + score;
        }           
        else{

            out.createNewFile(); // if file already exists will do nothing 
            write=  name + " " + score;
        }
        Files.write(Paths.get(path), write.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    
    public ArrayList sort(ArrayList list){
        if(!sorted)
        Collections.sort(list);
        sorted = true;
        return list;
    }
    
}

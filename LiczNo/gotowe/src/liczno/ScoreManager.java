package liczno;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import liczno.Score;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Menages the score reading form file, writing and sorting
 *
 * @author Maciek
 */
public class ScoreManager {

    private String path;
    private boolean sorted;

    /**
     * Array of scores
     */
    ArrayList<Score> scores;

    /**
     * Initialise ScoreManager, generate path to score file (user directory + /licznoscores)
     */
    public ScoreManager() {

        path = System.getProperty("user.dir") + "\\licznoscores";
        scores = new ArrayList<>();
        sorted = false;
    }

    /**
     * Reads score from file
     *
     * @return ArrayList of scores in file
     */
    public ArrayList readScore() {

        FileInputStream in = null;
        File out = new File(path);
        if (out.exists() && !out.isDirectory()) {
            scores.clear();
            try {
                in = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line = null;

                try {
                    while ((line = br.readLine()) != null) {
                        String[] score = line.split("@");
                        scores.add(new Score(score[0], Integer.parseInt(score[1])));

                    }
                } catch (IOException ex) {
                    Logger.getLogger(ScoreManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                return scores;

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ScoreManager.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ScoreManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    /**
     * Write score to file. If file doesn't exist - creates it.
     *
     * @param name name of player
     * @param score score of player
     * @throws IOException ioexceptions
     */
    public void writeScore(String name, int score) throws IOException {
        if ("".equals(name)) {
            name = "anonim";
        }
        File out = new File(path);
        String write = null;
        if (out.exists() && !out.isDirectory()) {

            write = "\n" + name + "@" + score;
        } else {

            out.createNewFile(); // if file already exists will do nothing 
            write = name + "@" + score;
        }
        Files.write(Paths.get(path), write.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        sorted = false;
    }

    /**
     * Deletes scores file
     */
    public void deleteScores() {
        File out = new File(path);
        out.delete();
        sorted = false;
    }

    /**
     * Sort list of scores
     *
     * @param list ArratList of scores
     * @return ArrayList of sorted scored
     */
    public ArrayList sort(ArrayList list) {
        if (!sorted) {
            Collections.sort(list);
            sorted = true;
        }
        return list;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liczno.mapping;

import java.awt.Graphics;
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import liczno.enterties.Block;

/**
 *
 * @author Maciek
 */
public class Map {

    private int width, height;
    private String path;
    private Block[][] blocks;

    ArrayList<Block> blocksList = new ArrayList<>();

    public Map(String path) {
        this.path = path;
        loadMap();
    }

    public void loadMap() {
        InputStream is = this.getClass().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            width = Integer.parseInt(br.readLine());
            height = Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

        blocks = new Block[height][width];

        String line = null;

        for (int y = 0; y < height; y++) {
            try {
                line = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
            String[] tokens = line.split("\\s+");
            for (int x = 0; x < width; x++) {
                switch (Integer.parseInt(tokens[x])) {
                    case 1:
                        blocks[y][x] = new Block(x * Block.blockWidth / 2, y * Block.blockHeight);
                        blocksList.add(blocks[y][x]);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Reading map error");
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < blocksList.size(); i++) {
            blocksList.get(i).draw(g);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocksList;
    }
}

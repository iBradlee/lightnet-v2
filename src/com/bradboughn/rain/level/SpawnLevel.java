/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.level;

import com.bradboughn.rain.entity.mob.Dummy;
import com.bradboughn.rain.entity.tile.TileMap;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Brad
 */
public class SpawnLevel extends Level 
{
    
    public SpawnLevel(String path) 
    {
        super(path);
    }
    
    protected void loadLevel(String path)
    {
        try 
        {
            BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
            int h = height = image.getHeight();
            int w = width = image.getWidth();
            tiles = new int[w*h];
            image.getRGB(0, 0, w, h, tiles, 0, w);
            tileMap = new TileMap(w, h, tiles);
        }   
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        //this one, at 40<<4 Y coord, is the one messing up
        add(new Dummy(20<<4,40<<4));
//        add(new Dummy(20<<4, 20<<4));
//        add(new Dummy(20<<4, 20<<4));
//        add(new Dummy(20<<4, 20<<4));
    }
    
    //Grass = 0x00FF00    
    //Flower = 0xFFFF00
    //Rock = 0x7F7F00
    protected void generateLevel()  
    {
        
        
        
    }
}
    


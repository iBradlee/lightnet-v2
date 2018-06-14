/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.level;

import com.bradboughn.rain.entity.mob.Dummy;
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
        }   
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        add(new Dummy(17, 24));
        add(new Dummy(18, 25));
        add(new Dummy(19, 25));
        add(new Dummy(20, 25));
    }
    
    //Grass = 0x00FF00    
    //Flower = 0xFFFF00
    //Rock = 0x7F7F00
    protected void generateLevel()  
    {
        
        
        
    }
}
    


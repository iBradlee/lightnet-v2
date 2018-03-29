/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Brad
 */
public class SpriteSheet 
{
    
    private String path;
//    public int SIZE;
    public final int WIDTH, HEIGHT;
    public int[] pixels;
    
    public static final SpriteSheet tiles = new SpriteSheet("/spritesheets/spritesheet.png", 256, 256);
    public static final SpriteSheet spawn_level = new SpriteSheet("/spritesheets/spawn_level.png", 96, 96);
    
    public static final SpriteSheet characters = new SpriteSheet("/spritesheets/beltshake2.png", 192, 96);
    
    
    public SpriteSheet(String path, int width, int height)
    {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        pixels = new int[width * height];
        load();
        
    }
    
    private void load() 
    {
        try 
        {
        BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
        image.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
}


package com.bradboughn.rain.graphics;

import com.bradboughn.rain.entity.mob.Player;
import com.bradboughn.rain.level.tile.Tile;
import java.util.Random;

public class Screen     
{
    public int width, height;
    public int[] pixels;
    public final int MAP_SIZE = 64;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    public int xOffset, yOffset;
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    
    private static Random random = new Random();
    
    public Screen(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        //for (int i = 0; i < (MAP_SIZE * MAP_SIZE); i++) {
            //tiles[i] = random.nextInt(0xffffff);
            //tiles[0] = 0;  
        //}
    }
    
    public void clear()
    {
        for (int i = 0; i < pixels.length; i++) 
        {
            pixels[i] = 0;
        }
    }
    
    
    public void renderTile (int xp, int yp, Sprite sprite) // xp/yp = map location of tile to be rendered
    {
        xp -= xOffset; // subtracting xOffset to XP, to move "map" in correct location
        yp -= yOffset;
        for (int y = 0; y < sprite.SIZE; y++) 
        {
            int ya = y + yp; // Setting Y absolute
            for (int x = 0; x < sprite.SIZE; x++) 
            {
                int col = sprite.pixels[x + y * sprite.SIZE];
                if (col == 0x00ffffff) continue;
                int xa = x + xp; // Setting X absolute
                if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; // stops rendering when outside screen/window size
                if (xa < 0) xa = 0;
                
                pixels[xa + ya * width] = col;
            }
        }
    }
    
    //renderTile w/ flip functionality
    public void renderTile (int xp, int yp, Sprite sprite, int flip)
    {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < sprite.SIZE; y++) 
        {
            int ya = y + yp;
            int ys = y;
            if (flip == 2 || flip == 3) ys = sprite.SIZE - 1 - y; //counts backwards now
            for (int x = 0; x < sprite.SIZE; x++) 
            {
                int xa = x + xp; 
                int xs = x;
                int col = sprite.pixels[x + y * sprite.SIZE];
                if (col == 0x00ffffff)continue;
                if (flip == 1 || flip == 3) xs = sprite.SIZE -1 - x;
                if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                
                pixels[xa + ya * width] = col;
            }
        }
    }
    
    public void renderEntity (int xp, int yp, Sprite sprite)
    {
        xp -= xOffset;
        yp -= yOffset;
        
        for (int y = 0; y < sprite.SIZE; y++) 
        {
            int ya = y + yp; 
            for (int x = 0; x < sprite.SIZE; x++) 
            {
                int col = sprite.pixels[x + y * sprite.SIZE];
                if( col == 0x00FFFFFF) continue;
                int xa = x + xp; // Setting X absolute
                if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; // stops rendering when outside player size
                if (xa < 0) xa = 0;
                pixels[xa + ya * width] = col; // if pixel isn't colored transparent, it renders the player image.
                                                                      // HEX Code is set to transparent.
            }
        }
    }
    //renderEntity w/ flip functionality
    public void renderEntity (int xp, int yp, Sprite sprite, int flip)
    {
        xp -= xOffset;
        yp -= yOffset;
        
        for (int y = 0; y < sprite.SIZE; y++) 
        {
            int ya = y + yp; 
            int ys = y;
            if (flip == 2 || flip == 3) ys = sprite.SIZE -1 - y;
            for (int x = 0; x < sprite.SIZE; x++) 
            {
                int xa = x + xp; 
                int xs = x;
                if (flip == 1 || flip == 3) xs = sprite.SIZE - 1 - x;
                int col = sprite.pixels[xs + ys * sprite.SIZE];
                if( col == 0x00FFFFFF) continue;
                if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; 
                if (xa < 0) xa = 0;
                pixels[xa + ya * width] = col; 
                                                       
            }
        }
    }
    
    public void renderPlayerOnScreen (int xp, int yp, Sprite sprite)
    {
        for (int y = 0; y < sprite.SIZE; y++) 
        {
            int ya = y + yp; 
            for (int x = 0; x < sprite.SIZE; x++) 
            {
                int xa = x + xp; // Setting X absolute
                if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; // stops rendering when outside player size
                if (xa < 0) xa = 0;
                int col = sprite.pixels[x + y * sprite.SIZE];
                if( col != 0x00FFFFFF) pixels[xa + ya * width] = col; // if pixel isn't colored transparent, it renders the player image.
                                                                      // HEX Code is set to transparent.
            }
        }
    }
    
    public void setOffset(int xOffset, int yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
}

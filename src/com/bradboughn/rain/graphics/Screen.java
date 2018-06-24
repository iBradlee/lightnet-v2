
package com.bradboughn.rain.graphics;

import com.bradboughn.rain.entity.mob.Player;
import com.bradboughn.rain.entity.tile.Tile;
import java.util.Random;

public class Screen     
{
    private static int width, height;
    private static int[] pixels;
    private static final int MAP_SIZE = 64;
    private static final int MAP_SIZE_MASK = MAP_SIZE - 1;
    private static int xOffset, yOffset;
    private static int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    
    public static void init(int width, int height)
    {
        Screen.width = width;
        Screen.height = height;
        pixels = new int[width * height];
    }
    
    public static void clear()
    {
        for (int i = 0; i < pixels.length; i++) 
        {
            pixels[i] = 0;
        }
    }
    
    
    public static void renderTile (int xp, int yp, Sprite sprite) // xp/yp = map location of tile to be rendered
    {
        xp -= xOffset; // subtracting xOffset to XP, to move "map" in correct location
        yp -= yOffset;
        for (int y = 0; y < sprite.HEIGHT; y++) 
        {
            int ya = y + yp; // Setting Y absolute
            for (int x = 0; x < sprite.WIDTH; x++) 
            {
                int col = sprite.getPixels()[x + y * sprite.WIDTH];
                if (col == 0x00ffffff) continue;
                int xa = x + xp; // Setting X absolute
                if (xa < -sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break; // stops rendering when outside screen/window size
                if (xa < 0) xa = 0;
                
                pixels[xa + ya * width] = col;
            }
        }
    }
    
    //renderTile w/ flip functionality
    public static void renderTile (int xp, int yp, Sprite sprite, int flip)
    {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < sprite.HEIGHT; y++) 
        {
            int ya = y + yp;
            int ys = y;
            if (flip == 2 || flip == 3) ys = sprite.HEIGHT - 1 - y; //counts backwards now
            for (int x = 0; x < sprite.WIDTH; x++) 
            {
                int xa = x + xp; 
                int xs = x;
                int col = sprite.getPixels()[x + y * sprite.WIDTH];
                if (col == 0x00ffffff) continue;
                if (flip == 1 || flip == 3) xs = sprite.WIDTH -1 - x;
                if (xa < -sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                
                pixels[xa + ya * width] = col;
            }
        }
    }
    
    /**
  * Renders a sprite in given pixel world coordinates with boundaries checking.
  * 
  * @param xp
  *             pixel x coordinate of the left-top corner of the sprite
  * @param yp
  *             pixel y coordinate of the left-top corner of the sprite
  * @param sprite
  *             sprite to render
  * @param pinToMap
  *             true means using viewport offset (pin sprite to the map, false - to the camera).
  */
    public static void renderSprite(int xp, int yp, Sprite sprite, boolean pinToMap)
    {
        if (pinToMap)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        
        //check if it's completely off screen
        if (xp <= -sprite.WIDTH || xp >= width || yp <= -sprite.HEIGHT || yp >= height) return;
        
        int sw = sprite.WIDTH;
        
        //calculate visable rectangle
        int x0 = xp < 0 ? -xp : 0;
        int x1 = xp + sw > width ? width - xp : sw;
        int y0 = yp < 0 ? -yp : 0;
        int y1 = yp + sprite.HEIGHT > height ? height - yp : sprite.HEIGHT;
        //y offset in screen and sprite
        int scanY = (y0 + yp) * width;
        int scanYspr = y0 * sw;
        
//        if (sprite.isTransparent())
//        {
            for (int y = y0; y < y1; y++)
            {
                for (int x = x0; x < x1; x++)
                {
                    int color = sprite.getPixels()[x + scanYspr];
                    //skip needed alpha values (<0 means uint alpha > 0x7f)
                    if ((color & 0xff000000) < 0) pixels[x+xp+scanY] = color;
                }
                scanY += width;
                scanYspr += sw;
            }
//        }
//        else
//        {
//            for (int y = y0; y < y1; y++)
//            {
//                for (int x = x0; x < x1; x++)
//                {
//                    pixels[x + xp + scanY] = sprite.getPixels()[x + scanYspr];
//                }
//                scanY += width;
//                scanYspr += sw;
//            }
//        }
    }
    
    public static void renderEntity (int xp, int yp, Sprite sprite)
    {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < sprite.HEIGHT; y++) 
        {
            int ya = y + yp; 
            for (int x = 0; x < sprite.WIDTH; x++) 
            {
                int col = sprite.getPixels()[x + y * sprite.WIDTH];
                if( col == 0x00FFFFFF) continue;
                int xa = x + xp; // Setting X absolute
                if (xa < -sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break; // stops rendering when outside player size
                if (xa < 0) xa = 0;
                pixels[xa + ya * width] = col; // if pixel isn't colored transparent, it renders the player image.
                                                                      // HEX Code is set to transparent.
            }
        }
    }
    
    //renderEntity w/ flip functionality
    public static void renderEntity (int xp, int yp, Sprite sprite, int flip)
    {
        xp -= xOffset;
        yp -= yOffset;
        
        for (int y = 0; y < sprite.HEIGHT; y++) 
        {
            int ya = y + yp; 
            int ys = y;
            if (flip == 2 || flip == 3) ys = sprite.HEIGHT -1 - y;
            for (int x = 0; x < sprite.WIDTH; x++) 
            {
                int xa = x + xp; 
                int xs = x;
                if (flip == 1 || flip == 3) xs = sprite.WIDTH - 1 - x;
                int col = sprite.getPixels()[xs + ys * sprite.WIDTH];
                if( col == 0x00FFFFFF) continue;
                if (xa < -sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break; 
                if (xa < 0) xa = 0;
                pixels[xa + ya * width] = col; 
            }
        }
    }
    
    public static void renderPlayerOnScreen (int xp, int yp, Sprite sprite)
    {
        for (int y = 0; y < sprite.HEIGHT; y++) 
        {
            int ya = y + yp; 
            for (int x = 0; x < sprite.WIDTH; x++) 
            {
                int xa = x + xp; // Setting X absolute
                if (xa < -sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break; // stops rendering when outside player size
                if (xa < 0) xa = 0;
                int col = sprite.getPixels()[x + y * sprite.WIDTH];
                if( col != 0x00FFFFFF) pixels[xa + ya * width] = col; // if pixel isn't colored transparent, it renders the player image.
                                                                      // HEX Code is set to transparent.
            }
        }
    }
    
    public static void setOffset(int xOffset, int yOffset)
    {
        Screen.xOffset = xOffset;
        Screen.yOffset = yOffset;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int[] getPixels() {
        return pixels;
    }

    public static int getxOffset() {
        return xOffset;
    }

    public static int getyOffset() {
        return yOffset;
    }

    public static int[] getTiles() {
        return tiles;
    }
    
    
}

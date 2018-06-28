
package com.bradboughn.rain.camera;

import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.entity.mob.Player;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.graphics.SpriteSheet;
import com.bradboughn.rain.util.debuggingtools.SpriteWithCoord;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
import java.util.List;

public class Camera 
{
    //NEED TO KEEP IN MIND THAT I SCALE THE IMAGE, NOT SURE IF THAT WILL AFFECT THINGS
    public static int width, height;
    
    private static int[] pixels;
    private static int maxX, maxY;
    private static int startCol, startRow, endCol, endRow;
    private static int xtick = 0, ytick = 0;
    
    private static int offsetX, offsetY; 
    
    private static Level level;
    private static Player player;
    
    public static List<SpriteWithCoord> testRenderQueue = new ArrayList();
    
    public static void init(int width, int height, int startX, int startY, Level level, Player player)
    {
        Camera.level = level;
        Camera.player = player;
        Camera.width = width;
        Camera.height = height;
        pixels = new int[width * height];
        maxX = level.getWidth() - width;
        maxY = level.getHeight() - height;
        offsetX = startX - (width>>1); //need to subtract halfwidth of camera
        offsetY = startY - (height>>1);
    }
    
    //testing functions only. for debugging/etc.
    public static void addSpriteToTestRenderQueue(SpriteWithCoord spr)
    {
        testRenderQueue.add(spr);
    }
    
    public static void clearTestRenderQueue()
    {
        testRenderQueue.clear();
    }
    
    //end of testing functions only. for debugging/etc.

    public static void update()
    {
        clearTestRenderQueue();
        updateOffsets();
        updateStartEndColRow();
    }
   
    public static void render()
    {      
        renderCamera();
    }
    
    public static void clear()
    {
        for (int i = 0; i < pixels.length; i++)
        {
            pixels[i] = 0xffa2c5f1;
        }
    }
    
    public static void updateStartEndColRow()
    {
        //calculating viewport
        startCol = offsetX >> 4;
        endCol = (offsetX + width + 16) >> 4;
        startRow = offsetY >> 4; //>>4 to get in tile precision. same as dividing by 16
        endRow = (offsetY + height + 16) >> 4; //+16 to add an additional tile to cover everything

    }
    
    public static void updateOffsets()
    {
        //work in progress camera edge-pan
//        if (Mouse.getX() < 15 || Mouse.getX() > width -15 || Mouse.getY() < 15 || Mouse.getY() > height - 15)
//        {
//            if (Mouse.getX() < 15) offsetX--; 
//            else if (Mouse.getX() > width -15) offsetX++;
//            if (Mouse.getY() < 15) offsetY--;
//            else if (Mouse.getY() > height - 15) offsetY++;
//            
//            xtick = 0;
//            ytick = 0;
//        }
//        else 
//        {
//            if (offsetX != player.getX() - Camera.width/2)
//            {
//                xtick++;
//                if (offsetX < player.getX() - Camera.width/2 && xtick > 30) offsetX++;
//                if (offsetX > player.getX() - Camera.width/2 && xtick > 30) offsetX--;
//            } else xtick = 0;
//            if (offsetY != player.getY() - Camera.height/2)
//            {
//                ytick++;
//                if (offsetY < player.getY() - Camera.height/2 && ytick > 30) offsetY++;
//                if (offsetY > player.getY() - Camera.height/2 && ytick > 30) offsetY--;
//            } else ytick = 0;
//        }
            offsetX = (int)player.getX() - (Camera.width>>1);
            offsetY = (int)player.getY() - (Camera.height>>1);
    }
    
    public static void renderCamera()
    {
        for (int y = startRow; y < endRow; y++)
        {
            for (int x = startCol; x < endCol; x++)
            {
                Sprite tileSpr = level.getTile(x, y).getSprite();
                renderSprite(x << 4, y << 4, tileSpr, true);
            }
        }
        //can just have camera call level's render method, and have it only render entities
        level.renderEntities();
//        renderSprite()
        
        if (!(testRenderQueue.isEmpty()))
        {
            for (SpriteWithCoord spr : testRenderQueue)
            {
                spr.render();
            }
        }
    }
    
    public static void renderSprite(int xp, int yp, Sprite sprite, boolean pinToMap)
    {
        //sets to render tile in terms of camera, not world. (0,0) being top left camera
        if(pinToMap)
        {
            xp -= offsetX;
            yp -= offsetY;
        }
        
        //check if completely off screen
        if (xp <= -sprite.WIDTH || xp >= width || yp <= -sprite.HEIGHT || yp >= height) return;
       
        int sw = sprite.WIDTH;
        
        //calculate visible portions of rect (if partway off screen)
        int x0 = xp < 0 ? -xp : 0; //if partially offscreen left, say xp = -3; then x0 now is 3; otherwise 0
        int x1 = xp + sw > width ? width - xp : sw; //if partially offscreen right, say 590 (where 600=width), x1 now is 10, otherwise 16(tileSize)
        int y0 = yp < 0 ? -yp : 0;//if offscreen top, say yp = -3, y0 now is 3; otherwise 0;
        int y1 = yp + sprite.HEIGHT > height ? height - yp : sprite.HEIGHT; //if offScrn down, yp=590(width=600), y1 now is 10, otherwise 16(tileSize)
        
        //y offset in screen and sprite
        int scanY = (y0 + yp) * width; //this allows quick traversal thru y coordinates (like "y *width")
        int scanYspr = y0 * sw; //same thing here, except for the sprite, not the camera
        
        for (int y = y0; y < y1; y++)
        {
            for (int x = x0; x < x1; x++)
            {
                int col = sprite.getPixels()[x + scanYspr];
                //skip needed alpha value; <0 means alpha is > 0x7f
                if ((col & 0xff000000) < 0) pixels[x + xp + scanY] = col;
            }
            //update scanY's to next y row
            scanY += width;
            scanYspr += sw;
        }
    }
    
    public static void renderSpriteSheet(int xp, int yp, SpriteSheet sheet, boolean pinToMap)
    {
        //sets to render tile in terms of camera, not world. (0,0) being top left camera
        if(pinToMap)
        {
            xp -= offsetX;
            yp -= offsetY;
        }
        
        //check if completely off screen
        if (xp <= -sheet.WIDTH || xp >= width || yp <= -sheet.HEIGHT || yp >= height) return;
       
        int sw = sheet.WIDTH;
        
        //calculate visible portions of rect (if partway off screen)
        int x0 = xp < 0 ? -xp : 0; //if partially offscreen left, say xp = -3; then x0 now is 3; otherwise 0
        int x1 = xp + sw > width ? width - xp : sw; //if partially offscreen right, say 590 (where 600=width), x1 now is 10, otherwise 16(tileSize)
        int y0 = yp < 0 ? -yp : 0;//if offscreen top, say yp = -3, y0 now is 3; otherwise 0;
        int y1 = yp + sheet.HEIGHT > height ? height - yp : sheet.HEIGHT; //if offScrn down, yp=590(width=600), y1 now is 10, otherwise 16(tileSize)
        
        //y offset in screen and sprite
        int scanY = (y0 + yp) * width; //this allows quick traversal thru y coordinates (like "y *width")
        int scanYspr = y0 * sw; //same thing here, except for the sprite, not the camera
        
        for (int y = y0; y < y1; y++)
        {
            for (int x = x0; x < x1; x++)
            {
                int col = sheet.getPixels()[x + scanYspr];
                //skip needed alpha value; <0 means alpha is > 0x7f
                if ((col & 0xff000000) < 0) pixels[x + xp + scanY] = col;
            }
            //update scanY's to next y row
            scanY += width;
            scanYspr += sw;
        }
    }

    public static int getOffsetY()
    {
        return offsetY;
    }

    public static void setOffsetY(int offsetY)
    {
        Camera.offsetY = offsetY;
    }

    public static int getOffsetX()
    {
        return offsetX;
    }

//    public static void setOffsetX(int offsetX)
//    {
//        Camera.offsetX = offsetX;
//    }

    public static int[] getPixels()
    {
        return pixels;
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }
    
    
    
    
    
    
}

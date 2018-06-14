
package com.bradboughn.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet 
{
    
    private String path;
//    public int SIZE;
    public final int WIDTH, HEIGHT;
    public final int ANIM_LENGTH;
    public int[] pixels;
    
    public static final SpriteSheet tiles = new SpriteSheet("/textures/spritesheets/spritesheet.png", 256, 256);
    public static final SpriteSheet spawn_level = new SpriteSheet("/textures/spritesheets/spawn_level.png", 96, 96);
    
    public static final SpriteSheet characters = new SpriteSheet("/textures/spritesheets/beltshake2.png", 192, 96);
    public static final SpriteSheet projectiles = new SpriteSheet("/textures/spritesheets/projectiles/basic2.png", 48, 48);
    
    //Characters/mobs/etc.
    public static final SpriteSheet player = new SpriteSheet("/textures/spritesheets/player_spritesheet.png", 128, 96);
    public static final SpriteSheet mob_dummy = new SpriteSheet("/textures/spritesheets/mob_ghost.png", 192, 128);
    
    //Sub-SpriteSheets for animation/etc.
    public static final SpriteSheet player_up = new SpriteSheet(player, 0, 0, 1, 3, 32);
    public static final SpriteSheet player_right = new SpriteSheet(player, 1, 0, 1, 3, 32);
    public static final SpriteSheet player_down = new SpriteSheet(player, 2, 0, 1, 3, 32);
    public static final SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 3, 32);
    
    public static final SpriteSheet mob_dummy_walkR = new SpriteSheet(mob_dummy, 0, 0, 6, 1, 32);
    public static final SpriteSheet mob_dummy_walkL = new SpriteSheet(mob_dummy, 0, 1, 6, 1, 32);

    
    private Sprite[] sprites;

    public SpriteSheet(String path, int width, int height)
    {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        pixels = new int[width * height];
        ANIM_LENGTH = -1;
        load();
        
    }
    
    /**
     * SUB-SPRITESHEET Constructor | Used for quickly making Sprite array, holding "frames" of animation
     * @param sheet
     *          Sheet is the sprite sheet which we're using to pull multiple sprites out of
     * @param x
     *          X is for starting position, in a "tile" or "grid" precision (using size of the sprites)
     * @param y
     *          Y is for starting position, in a "tile" or "grid" precision (using size of the sprites)
     * @param width
     *          Width is the width of our sub-spritesheet. It deals with how many sprites wide our 
     *          subsheet, or animation cycle, is
     * @param height
     *          Height is the height of our sub-spritesheet. It deals with how many sprites high our 
     *          subsheet, or animation cycle, is
     * @param spriteSize 
     *          Square sprite size, in pixels
     */
    
    
    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize)
    {
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        WIDTH = w;
        HEIGHT = h;
        //ANIM_LENGHT will find which is bigger: width (if we have spritesheet that had animations going to the right),
        //or height (if we had spritesheet that has animations going down), and sets that as our LENGTH
        ANIM_LENGTH = width > height ? width : height;
        sprites = new Sprite[ANIM_LENGTH];
        pixels = new int[w * h];
        //Nest for loop pulls entire image, from sheet, and stores in SpriteSheet's pixel array
        for (int y0 = 0; y0 < h; y0++)
        {
            int yp = yy + y0;
            for (int x0 = 0; x0 < w; x0++)
            {
                int xp = xx + x0;
                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
                
            }
        }
        //Keeps track of which "frame", for animation, so we can store them properly
        int frame = 0;
        
        //Using the pixel array, which now has our entire sheet, we create new pixel arrays to copy
        //over individual sprites, and then store each one in our Sprite array. The final product gives
        //us a Sprite array, with all the frames of animation we need.
        //y0/x0 iterate over SPRITES; ya/xa iterate over pixels in each SPRITE
        for (int y0 = 0; y0 < height; y0++)
        {
            for (int x0 = 0; x0 < width; x0++)
            {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for (int ya = 0; ya < spriteSize; ya++)
                {
                    for (int xa = 0; xa < spriteSize; xa++)
                    {
                        //This creates a new sprite, for each sprite in our sub-sheet
                        spritePixels[xa + ya * spriteSize] = pixels[(xa + x0 * spriteSize) + (ya + y0 * spriteSize) * WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }
    }
    
    public Sprite[] getSprite()
    {
        return sprites;
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

    public int[] getPixels()
    {
        return pixels;
    }
    
}

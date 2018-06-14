
package com.bradboughn.rain.graphics;

import static com.bradboughn.rain.graphics.SpriteSheet.mob_dummy;
import static com.bradboughn.rain.graphics.SpriteSheet.player;

public class Sprite 
{
    
    /*@todo
    *   May need to SLIGHTLY overhaul sprites/spritesheet a tad. I want to, before creating public
    *   static sprites, isolate only the colored, actual pixels that make up the sprite, and save those
    *   into the image, doing away with all transparent pixels. This means I would have to specify if
    *   a sprite is supposed to be transparent, like a ghosty-boi or somesuch, and also write those
    *   semi-transparent pixels to the sprite (by just doing the 0xff shift to isolate it(can refer
    *   to original LightNet Engine for specific code))
    */
    
    public final int WIDTH, HEIGHT;
    private int x, y;
    private boolean transparent = false;
    private int[] pixels;
    protected SpriteSheet sheet;
    
    public final static Sprite GRASS = new Sprite(16, 16, 0, 0, SpriteSheet.tiles);
    public final static Sprite GRASS_FLOWER = new Sprite(16, 16, 0, 1, SpriteSheet.tiles);
    
    public final static Sprite WATER = new Sprite(16, 16, 1, 0, SpriteSheet.tiles);
    public final static Sprite VOID_SPRITE = new Sprite(16, 16, 0x1B87E0);
    
    //Spawn Level Sprites Here:
    public final static Sprite SPAWN_GRASS_BASIC = new Sprite(16, 16, 0, 0, SpriteSheet.spawn_level);
    public final static Sprite SPAWN_GRASS = new Sprite(16, 16, 0, 1, SpriteSheet.spawn_level);
    public final static Sprite SPAWN_GRASS_ROCK = new Sprite(16, 16, 0, 2, SpriteSheet.tiles);
    public final static Sprite SPAWN_HEDGE = new Sprite(16, 16, 0, 2, SpriteSheet.spawn_level);
    public final static Sprite SPAWN_WALL_STONE = new Sprite(16, 16, 1, 0, SpriteSheet.spawn_level);
    public final static Sprite SPAWN_WALL_STONE2 = new Sprite(16, 16, 1, 1, SpriteSheet.spawn_level);
    public final static Sprite SPAWN_FLOOR_WOOD = new Sprite(16, 16, 1, 2, SpriteSheet.spawn_level);
    public final static Sprite SPAWN_WATER = new Sprite(16, 16, 2, 0, SpriteSheet.spawn_level);
    
    //Default Sprites, for constructors, Here:
    public static final Sprite DEFAULT_PLAYER = new Sprite(32,32,0,0, player);
    public static final Sprite DEFAULT_MOB_DUMMY = new Sprite(32,32,0,0, mob_dummy);

    
    //Projectiles
    public static Sprite projectile_basic = new Sprite(8, 8, 0, 0, SpriteSheet.projectiles);
    
    //Particles
    public static Sprite particle_basic = new Sprite(2, 2, 0xff05a1f5);
    
    public Sprite(int width, int height, int x, int y, SpriteSheet sheet) 
    {
        WIDTH = width;
        HEIGHT = height;
        pixels = new int[WIDTH * HEIGHT];
        this.x = x * WIDTH;
        this.y = y * HEIGHT;
        this.sheet = sheet;
        load();
    }
    
    public Sprite(int width, int height, int color) 
    {
        WIDTH = width;
        HEIGHT = height;
        pixels = new int[WIDTH * HEIGHT];
        setColor(color);
    }
    
    protected Sprite(int width, int height, SpriteSheet sheet)
    {
        WIDTH = width;
        HEIGHT = height;
        this.sheet = sheet;
    }

    Sprite(int[] spritePixels, int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;
        pixels = spritePixels;
    }
    
    public void setColor(int color) 
    {
        for (int i = 0; i < pixels.length; i++) 
        {
            pixels[i] = color;
        }
    }
    
    public void load()
    {
        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                pixels[x + y * WIDTH] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
            }
        }
    }
    
    public int[] getPixels()
    {
        return pixels;
    }
    
    public boolean isTransparent() 
    {
        return transparent;
    }
}

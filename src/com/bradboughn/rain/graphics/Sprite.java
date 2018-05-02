
package com.bradboughn.rain.graphics;

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
    private SpriteSheet sheet;
    
    public static Sprite grass = new Sprite(16, 16, 0, 0, SpriteSheet.tiles);
    public static Sprite grassFlower = new Sprite(16, 16, 0, 1, SpriteSheet.tiles);
    public static Sprite grassRock = new Sprite(16, 16, 0, 2, SpriteSheet.tiles);
    
    public static Sprite water = new Sprite(16, 16, 1, 0, SpriteSheet.tiles);
    public static Sprite voidSprite = new Sprite(16, 16, 0x1B87E0);
    
    //Spawn Level Sprites Here:
    public static Sprite spawn_grassBasic = new Sprite(16, 16, 0, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_grass = new Sprite(16, 16, 0, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_hedge = new Sprite(16, 16, 0, 2, SpriteSheet.spawn_level);
    public static Sprite spawn_stoneWall = new Sprite(16, 16, 1, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_stoneWall2 = new Sprite(16, 16, 1, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_woodFloor = new Sprite(16, 16, 1, 2, SpriteSheet.spawn_level);
    public static Sprite spawn_water = new Sprite(16, 16, 2, 0, SpriteSheet.spawn_level);
    
    //Player Sprites Here:
    public static Sprite player_U = new Sprite(32, 32, 0, 5, SpriteSheet.tiles);
    public static Sprite player_D = new Sprite(32, 32, 2, 5, SpriteSheet.tiles);
    public static Sprite player_L = new Sprite(32, 32, 3, 5, SpriteSheet.tiles);
    public static Sprite player_R = new Sprite(32, 32, 1, 5, SpriteSheet.tiles);
    
    public static Sprite player_U1 = new Sprite(32, 32, 0, 6, SpriteSheet.tiles);
    public static Sprite player_D1 = new Sprite(32, 32, 2, 6, SpriteSheet.tiles);
    public static Sprite player_L1 = new Sprite(32, 32, 3, 6, SpriteSheet.tiles);
    public static Sprite player_R1 = new Sprite(32, 32, 1, 6, SpriteSheet.tiles);
    
    public static Sprite player_U2 = new Sprite(32, 32, 0, 7, SpriteSheet.tiles );
    public static Sprite player_D2 = new Sprite(32, 32, 2, 7, SpriteSheet.tiles );
    public static Sprite player_L2 = new Sprite(32, 32, 3, 7, SpriteSheet.tiles );
    public static Sprite player_R2 = new Sprite(32, 32, 1, 7, SpriteSheet.tiles );
    
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

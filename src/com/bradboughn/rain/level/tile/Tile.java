
package com.bradboughn.rain.level.tile;

import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.tile.spawn_level.SpawnGrassTile;
import com.bradboughn.rain.level.tile.spawn_level.SpawnWaterTile;

public class Tile 
{
 
    //@todo need to implement TILE_SIZE into spritesheet, i think? and/or sprite?
    public static final int TILE_SIZE = 16;
    protected int x, y;
    protected Sprite sprite;
    protected AABB aabb;

    
    //Basic FIRST Tiles
    public static Tile grass = new GrassTile();
    public static Tile grassFlower = new FlowerTile();
    public static Tile grassRock = new RockTile();
    public static Tile water = new WaterTile();
    public static Tile voidTile = new VoidTile();
    
    //Spawn Level Tiles:
    public static Tile spawn_grassBasic = new GrassTile();
    public static Tile spawn_grass = new SpawnGrassTile();
    public static Tile spawn_hedge = new BreakableWall();
    public static Tile spawn_stoneWall = new WallTile();
    public static Tile spawn_stoneWall2 = new WallTile(Sprite.SPAWN_WALL_STONE2);
    public static Tile spawn_woodFloor = new FloorTile();
    public static Tile spawn_water = new SpawnWaterTile();
    
    public static final int COL_SPAWN_GRASS = 0xff00ff00;
    public static final int COL_SPAWN_GRASS_FLOWER = 0xfffe0000;
    public static final int COL_SPAWN_GRASS_STONE = 0xff7f7f00;
    public static final int COL_SPAWN_HEDGE = 0xff2C5E00;
    public static final int COL_SPAWN_WALL_STONE = 0xff2E3D46;
    public static final int COL_SPAWN_WALL_STONE2 = 0xffC58F2D;
    public static final int COL_SPAWN_FLOOR_WOOD = 0xff4D2201;
    public static final int COL_SPAWN_WATER = 0xff0026ff;
    

    
    public Tile(Sprite sprite)
    {
        this.sprite = sprite;
    }
    
    public Tile(Sprite sprite, int x, int y)
    {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }
    
    public Tile()
    {
        
    }
    
    public void render(int x, int y) 
    {
        Screen.renderSprite(x << 4, y << 4, sprite, true);
    }
    
    public boolean isSolid() 
    {
        return false;
    }
    
    public boolean breakable() 
    {
        return false;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public AABB getAabb()
    {
        return aabb;
    }
    
    
    
}

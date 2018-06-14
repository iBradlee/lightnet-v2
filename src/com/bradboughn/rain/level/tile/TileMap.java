
package com.bradboughn.rain.level.tile;

import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.Level;
import com.bradboughn.rain.level.tile.spawn_level.SpawnGrassTile;

public class TileMap
{

    private Tile[][] map;
    private int width, height;
    
    
    public TileMap(int width, int height, int[] mapData)
    {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        loadMapDataImg(mapData);
        
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
//                map[x][y] 
            }
        }
        
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                
            }
        }
    }
    
    private void loadMapDataImg(int[] mapData)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                
            }
        }
    }
    /**
     * Reads RGB pixel data from mapData image, determines what color corresponds to a specific Tile,
     * then returns a new instance of that tile, with proper x and y coordinates.
     * @param x 
     * @param y
     * @param mapData
     * @return 
     */
    private Tile getNewTileFromColor(int x, int y, int[] mapData)
    {
        if (x < 0 || y < 0 || x >= width || y >= height) return new WaterTile(Sprite.SPAWN_WATER, x, y);
        
        switch (mapData[x + y * width])
        {
            case Tile.COL_SPAWN_GRASS:
                return new GrassTile(Sprite.SPAWN_GRASS, x, y);
            case Tile.COL_SPAWN_GRASS_FLOWER:
                return new FlowerTile(Sprite.GRASS_FLOWER, x, y);
            case Tile.COL_SPAWN_GRASS_STONE:
                return new RockTile(Sprite.SPAWN_GRASS_ROCK, x, y);
            case Tile.COL_SPAWN_WALL_STONE:
                return new WallTile(Sprite.SPAWN_WALL_STONE, x, y);
            case Tile.COL_SPAWN_WALL_STONE2:
                return new WallTile(Sprite.SPAWN_WALL_STONE2, x, y);
            case Tile.COL_SPAWN_FLOOR_WOOD:
                return new FloorTile(Sprite.SPAWN_FLOOR_WOOD, x, y);
            case Tile.COL_SPAWN_HEDGE:
                return new BreakableWall(Sprite.SPAWN_HEDGE, x, y);
            case Tile.COL_SPAWN_WATER:
                return new WaterTile(Sprite.SPAWN_WATER, x, y);
            default:
                return new VoidTile(Sprite.VOID_SPRITE, x, y);
        }
    }
}

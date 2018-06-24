
package com.bradboughn.rain.level;

import com.bradboughn.rain.entity.tile.Tile;


public class TileCoordinate 
{
    
    private int x, y;

    
    public TileCoordinate (int x, int y) 
    {
            this.x = x * Tile.TILE_SIZE;
            this.y = y * Tile.TILE_SIZE;       
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY() 
    {
        return y;
    }
    
    
    public void setX(int x)
    {
        this.x = x * Tile.TILE_SIZE;
    }
    
    public void setY(int y)
    {
        this.y = y * Tile.TILE_SIZE;
    }
    
    public int[] xy() 
    {
        int[] r = new int[2];
        r[0] = x;
        r[1] = y;
        return r;
    }
    
    
}

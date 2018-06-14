/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.level.tile;

import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.tile.Tile;

/**
 *
 * @author Brad
 */
public class WallTile extends Tile 
{
    
    public WallTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public WallTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public WallTile()
    {
        sprite = Sprite.SPAWN_WALL_STONE;
    }
    
    public boolean isSolid() 
    {
        return true;
    }
}

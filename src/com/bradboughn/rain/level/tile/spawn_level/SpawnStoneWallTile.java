/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.level.tile.spawn_level;

import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.tile.Tile;

/**
 *
 * @author Brad
 */
public class SpawnStoneWallTile extends Tile 
{
    
    public SpawnStoneWallTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public SpawnStoneWallTile()
    {
        sprite = Sprite.spawn_stoneWall;
    }
    
    public boolean isSolid() 
    {
        return true;
    }
}

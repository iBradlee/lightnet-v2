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
public class SpawnWaterTile extends Tile 
{
    
    public SpawnWaterTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public SpawnWaterTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public SpawnWaterTile()
    {
        sprite = Sprite.SPAWN_WATER;
    }
    
}

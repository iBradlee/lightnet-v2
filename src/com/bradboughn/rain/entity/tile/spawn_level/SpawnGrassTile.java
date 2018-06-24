/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.entity.tile.spawn_level;

import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.entity.tile.Tile;

/**
 *
 * @author Brad
 */
public class SpawnGrassTile extends Tile
{
    
    public SpawnGrassTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public SpawnGrassTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public SpawnGrassTile()
    {
        sprite = Sprite.SPAWN_GRASS;
    }
    

    
}

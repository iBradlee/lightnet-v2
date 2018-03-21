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
public class SpawnGrassTile extends Tile
{
    
    public SpawnGrassTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public SpawnGrassTile()
    {
        sprite = Sprite.spawn_grass;
    }
    

    
}

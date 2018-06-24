/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.entity.tile;

import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.entity.tile.Tile;

/**
 *
 * @author Brad
 */
public class FloorTile extends Tile 
{
    
    public FloorTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public FloorTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public FloorTile()
    {
        sprite = Sprite.SPAWN_FLOOR_WOOD;
    }
    
}

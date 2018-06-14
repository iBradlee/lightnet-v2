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
public class BreakableWall extends Tile
{
    
    public BreakableWall(Sprite sprite) 
    {
        super(sprite);
    }
    
    public BreakableWall(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public BreakableWall()
    {
        sprite = Sprite.SPAWN_HEDGE;
    }
    
    public boolean isSolid()
    {
        return true; 
    }
    
    public boolean breakable()
    {
        return true;
    }
    
}


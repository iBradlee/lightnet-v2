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
public class SpawnHedgeTile extends Tile
{
    
    public SpawnHedgeTile(Sprite sprite) 
    {
        super(sprite);
    }
    
    public SpawnHedgeTile()
    {
        sprite = Sprite.spawn_hedge;
    }
    
    public boolean solid()
    {
        return true; 
    }
    
    public boolean breakable()
    {
        return true;
    }
    
}


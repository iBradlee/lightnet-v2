/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.entity.tile;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;

/**
 *
 * @author Brad
 */
public class WaterTile extends Tile {
    
    public WaterTile(Sprite sprite) {
        super(sprite);
    }
    
    public WaterTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public WaterTile()
    {
        sprite = Sprite.WATER;
    }
    

}

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
public class FlowerTile extends Tile {

    public FlowerTile(Sprite sprite) {
        super(sprite);
    }
    
    public FlowerTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public FlowerTile()
    {
        sprite = Sprite.GRASS_FLOWER;
    }
    

}

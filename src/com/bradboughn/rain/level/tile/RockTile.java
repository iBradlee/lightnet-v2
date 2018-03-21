/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.level.tile;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;

/**
 *
 * @author Brad
 */
public class RockTile extends Tile {

    public RockTile(Sprite sprite) {
        super(sprite);
    }
    
    public RockTile()
    {
        sprite = Sprite.grassRock;
    }
    
    public boolean solid() {
        return true;
    }
    
    
}

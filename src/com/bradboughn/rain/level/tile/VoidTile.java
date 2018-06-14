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
public class VoidTile extends Tile {

    public VoidTile(Sprite sprite) {
        super(sprite);
    }
    
    public VoidTile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public VoidTile()
    {
        sprite = Sprite.VOID_SPRITE;
    }
    

}

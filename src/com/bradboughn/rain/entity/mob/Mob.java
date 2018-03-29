/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Sprite;

/**
 *
 * @author Brad
 */
public abstract class Mob extends Entity 
{
    
    protected Sprite sprite;
    protected int dir = 0;

    protected boolean moving = false;
    
    public void move(int xa, int ya) 
    {
        if(xa != 0 && ya != 0) 
        {
            move(xa,0);
            move(0,ya);
            return;
        }
        if (xa > 0) dir = 1;
        if (xa < 0) dir = 3;
        if (ya > 0) dir = 2;
        if (ya < 0) dir = 0;
        
        if (!collision(xa, ya))
        {
        x += xa;
        y += ya;
        }
    }
    
    public void update() 
    {
        
    }
    
    //Finding four corners of tile for collision
    private boolean collision(int xa, int ya) 
    {
        boolean solid = false;
        for (int c = 0; c < 4; c++)
        {
            //<editor-fold defaultstate="collapsed" desc="Collision "info"">
            /*  Actual "corner code" goes between "(x+xa)" and "/16". (x+xa) is obv. the absolute position of
            *   Mob, and "/16" gets it in Tile precision, as opposed to pixel.
            *   NOTE: To adjust/play with tile collision here, think of this algorithm kinda like this:
            *   (c % 2) and (c / 2) cycles through the 4 corners of a tile for each axis, in a way. The
            *   x portion checks for 4 corners of a tile ON THE X AXIS, and the y portion checks for the
            *   same, but ON THE Y AXIS, to locate the actual tile using those x and y values. Now the
            *   actual collision stuff:
            *   (* 14 -7) and (* 12 + 3) KIND OF work in the following way:
            *   the "* 14" for the x, can be changed to change how far/close on the right side/corner of a tile to
            *   classify it as colliding. The "-7" does the same, but checks for the left side/corner of a tile.
            *   The exact same is true for y. "* 12" handles the bottom side/corner, and "+3" handles top.
            *   you can change these values to achieve different sized collision boxes.
            */
//</editor-fold>
            int xTile = ((x + xa) + c % 2 * 14 - 7) / 16; 
            int yTile = ((y +ya) + c / 2 * 12 + 3) / 16;
//
//            System.out.println("actual xtile : " + x / 16 + "\nactual ytile : " + y /16+ "\n");
//            System.out.println("c = " + c + "\nxTile : " + xTile + "\nyTile : " + yTile + "\n------------------------");
            if (level.getTile(xTile, yTile).solid()) solid = true;
        }
        return solid;
    }
    
        
    public void shoot(int x, int y, double direction)
    {
        direction *= 180/Math.PI;
    }
    
    public void render() 
    {
        
    }
}

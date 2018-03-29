
package com.bradboughn.rain.entity.projectile;

import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Sprite;

public class Projectile extends Entity 
{
    protected final int xOrigin, yOrigin;
    protected double angle;
    protected Sprite sprite;
    protected double newX, newY;
    protected boolean projectileDrop;
    protected double speed, rateOfFire, dropRate, range, damage;


    
    public Projectile(int x, int y, int dir)
    {
        xOrigin = x;
        yOrigin = y;
        angle = dir;
        
    }
    
}

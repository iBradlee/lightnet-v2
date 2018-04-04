
package com.bradboughn.rain.entity.projectile;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;

public class WizardProjectile extends Projectile 
{
    //initialization block
    {
        sprite = Sprite.basicProjectile;
    }

    public static final int FIRE_RATE = 20;
    
    public WizardProjectile(int x, int y, double slope) 
    {
        super(x, y, slope);
        this.x = x - sprite.SIZE/2;
        this.y = y - sprite.SIZE/2;
        speed = 6;
        range = 200;
        damage = 20;
        projX = this.x;
        projY = this.y;
        //<editor-fold defaultstate="collapsed" desc="cos and sin comment">
//sine and cosine will give us the correct ratio to move each line in, each tick, based upon
//our angle. Just like Bresenham's Line Algorithm, using the ratio from sin and cos, it will
//move X a bit more than Y, each tick, if it's a shallow angle. And move Y more than X each
//tick, if it's a steep angle. This is because you cannot accurately use decimals when drawing
//with pixels. The line either moves on the X or Y axis this tick a certain increment of 1 pixel,
//or it doesn't move along that axis this time.
//these are Vectors, I believe
//</editor-fold>
        newX = Math.cos(angle) * speed;
        newY = Math.sin(angle) * speed;   
    }
    



}

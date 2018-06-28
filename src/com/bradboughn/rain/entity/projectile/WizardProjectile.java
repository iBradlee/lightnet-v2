
package com.bradboughn.rain.entity.projectile;

import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.graphics.Sprite;

public class WizardProjectile extends Projectile 
{
    //initialization block
    {
        setSprite(Sprite.projectile_basic);
    }

    public static int FIRE_RATE = 10;
    
    public WizardProjectile(double x, double y, double slope) 
    {
        super(x, y, slope);
        value = "projectile";
        this.x = x - getSprite().WIDTH/2;
        this.y = y - getSprite().HEIGHT/2;
        speed = 8;
        range = 200;
        FIRE_RATE = 10;
        damage = 20;
        xx = this.x;
        yy = this.y;
        
        aabb = new AABB(x, y, getSprite().WIDTH -1, getSprite().HEIGHT -1, this);
        
        //<editor-fold defaultstate="collapsed" desc="cos and sin comment">
//sine and cosine will give us the correct ratio to move each line in, each tick, based upon
//our angle. Just like Bresenham's Line Algorithm, using the ratio from sin and cos, it will
//move X a bit more than Y, each tick, if it's a shallow angle. And move Y more than X each
//tick, if it's a steep angle. This is because you cannot accurately use decimals when drawing
//with pixels. The line either moves on the X or Y axis this tick a certain increment of 1 pixel,
//or it doesn't move along that axis this time.
//these are Vectors, I believe
//</editor-fold>
        xa = (Math.cos(angle) * speed)/2;
        ya = (Math.sin(angle) * speed)/2;  
    }
    
    



}

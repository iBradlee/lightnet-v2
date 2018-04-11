
package com.bradboughn.rain.gameobject.projectile;

import com.bradboughn.rain.gameobject.GameObject;
import com.bradboughn.rain.gameobject.spawner.Spawner;
import com.bradboughn.rain.gameobject.spawner.ParticleSpawner;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.Level;

public class Projectile extends GameObject 
{
    /*@todo
    *   need to check all corners of projectile, for collision. As of now, even if a projectile "hits"
    *   an object and collides, it won't count as a collision, because it uses the CENTER of the 
    *   projectile as the point of collision.
    */
    protected Sprite sprite;
    
    protected final double xOrigin, yOrigin;
    protected double xx, yy;
    protected double xa, ya;

    protected double distance;
    protected double angle;
    
    protected boolean projectileDrop;
    protected double speed, dropRate, range, damage;


    
    public Projectile(double x, double y, double slope)
    {
        
        xOrigin = x;
        yOrigin = y;
        angle = slope;
    }
    
    public void update()
    {
        if (distance < range)
        {
            if (!level.tileCollision(xx, yy, xa, ya, sprite.WIDTH, sprite.HEIGHT))
            {
            move();
            }
            else 
            {
                remove();
                level.add(new ParticleSpawner((int)xx, (int)yy, Spawner.Type.PARTICLE_WIZ, 25, 20, level));
            }

        } 
        else remove();
        
        calculateDistance();
    }
    
    public void render()
    {
        Screen.renderSprite((int)xx, (int)yy, sprite, true);
    }
    
    protected void move()
    {
        xx += xa;
        yy += ya;  
        
    }
    
    protected double calculateDistance() 
    {
        distance = Math.sqrt(((xOrigin - xx) * (xOrigin - xx) + (yOrigin - yy) * (yOrigin - yy)));
        return distance;
    }
    
    public void init(Level level)
    {
        this.level = level;
    }
    
}

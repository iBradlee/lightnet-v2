
package com.bradboughn.rain.gameobject.projectile;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.gameobject.GameObject;
import com.bradboughn.rain.gameobject.spawner.Spawner;
import com.bradboughn.rain.gameobject.spawner.ParticleSpawner;
import com.bradboughn.rain.graphics.Sprite;

public abstract class Projectile extends GameObject 
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
        this.x = (int)x;
        this.y = (int)y;
        xOrigin = x;
        yOrigin = y;
        angle = slope;
        
    }
    
    public void update()
    {
        if (distance < range)
        {
            if (!level.tileCollision(xx, yy, xa, ya, this))
            {
            move();
            }
            else 
            {
                remove();
                level.add(new ParticleSpawner((int)xx, (int)yy, Spawner.Type.PARTICLE_WIZ, 35, 25, level));
            }

        } 
        else remove();
        
        calculateDistance();
    }
    
    public void render()
    {
        Camera.renderSprite((int)xx, (int)yy, sprite, true);
    }
    
    protected void move()
    {
        xx += xa;
        yy += ya; 
        //updating original x,y, as well as doubles xx,yy

//        System.out.println("xx : " + xx + ",        yy : " + yy + "\nx : " + x + ",         y : " + y + "\n");
    }
    
    protected double calculateDistance() 
    {
        distance = Math.sqrt(((xOrigin - xx) * (xOrigin - xx) + (yOrigin - yy) * (yOrigin - yy)));
        return distance;
    }
    

    
}

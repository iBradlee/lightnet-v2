
package com.bradboughn.rain.entity.projectile;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.entity.spawner.Spawner;
import com.bradboughn.rain.entity.spawner.ParticleSpawner;
import com.bradboughn.rain.graphics.Sprite;

public abstract class Projectile extends Entity 
{
    /*@todo
    *   need to check all corners of projectile, for collision. As of now, even if a projectile "hits"
    *   an object and collides, it won't count as a collision, because it uses the CENTER of the 
    *   projectile as the point of collision.
    */
    
    protected final double xOrigin, yOrigin;
    protected double xx, yy;
    protected double xa, ya;

    protected double distance;
    protected double angle;
    
    protected boolean projectileDrop;
    protected double speed, dropRate, range, damage;


    
    public Projectile(double x, double y, double slope)
    {
        setSprite(Sprite.VOID_SPRITE);
        sprHalfWidth = getSprite().WIDTH>>1;
        sprHalfHeight = getSprite().HEIGHT>>1;
        type = Type.PROJECTILE;
        this.x = (int)x;
        this.y = (int)y;
        setCenter();
        xOrigin = x;
        yOrigin = y;
        angle = slope;    }
    
    public void update()
    {
        setSpeed(87);
        if (distance < range)
        {
            move(xa,ya);
//            if (!level.tileCollision(xx, yy, xa, ya, aabb))
//            {
//            move(xa, ya);
//            }
//            else 
//            {
//                remove();
//                level.add(new ParticleSpawner((int)xx, (int)yy, Spawner.Type.PARTICLE_WIZ, 35, 25, level));
//            }
        } 
        else setRemovedTrue();
        
        calculateDistance();
        
        updateGridCells();
    }
    
    public void render()
    {
        Camera.renderSprite((int)xx, (int)yy, getSprite(), true);
    }
    
    int tick = 0;
    public void move(double xa, double ya) 
    {
        if (tick < 3 && this.isRemoved() == false)
        {
            if(xa != 0 && ya != 0) 
            {
                move(xa,0);
                move(0,ya);
                return;
            }

            double[] collisionCheck = level.tileCollision(xx, yy, xa, ya, aabb);

            updateXs(collisionCheck[1]);
            updateYs(collisionCheck[2]);

            if (collisionCheck[0] == 1)
            {
                setRemovedTrue();
                level.add(new ParticleSpawner(centerX, centerY, Spawner.Type.PARTICLE_WIZ, 35, 25, level));
            }
        }
        tick = 0;
    }
    
    public void setSpeed(int newSpeed)
    {
        speed = newSpeed;
    }
    
//    protected void move()
//    {
//        updateXs(xa);
//        updateYs(ya);
//    }
    
    protected double calculateDistance() 
    {
        distance = Math.sqrt(((xOrigin - xx) * (xOrigin - xx) + (yOrigin - yy) * (yOrigin - yy)));
        return distance;
    }
    
    protected void updateXs(double xa)
    {
        xx += xa;
        centerX = (int)xx + sprHalfWidth;
    }
    
    protected void updateYs(double ya)
    {
        yy += ya;
        centerY = (int)yy + sprHalfHeight;
    }
}

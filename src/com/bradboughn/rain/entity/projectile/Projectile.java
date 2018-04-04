
package com.bradboughn.rain.entity.projectile;

import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.Level;

public class Projectile extends Entity 
{
    /*@todo
    *   UPDATE ON THIS NOTE: I don't know if this (comment below this) is nessicary. Because projectiles
    *   are removed so quickly (they only travel the distance of "range" b4 being removed form arrList)
    *   I could test this if I really wanted to, but i'm unsure it's needed now...
    *
    *
    *   I'm thinking adding a Mob (entity? probs not tho) Object to projectile might be neat. That way 
    *   I can pass in the owner of the projectile when it is created (in projectile constructor), and 
    *   use that object to call the function "clearProjectiles" ONLY WHEN I SET A PROJECTILE TO "remove"
    *   So, like this:
    *   if (distance > range){
    *       remove(); 
    *       clearProjectile(ownerMob);
    *       return;
    *   }
    *   because as of now, clearProjectile is constantly checked, which means the arraylist is constantly
    *   being iterated thru, and checked for an if statement. bleh.
    */
    
    protected final double xOrigin, yOrigin;
    protected double projX, projY;
    protected double angle;
    protected double distance;
    protected Sprite sprite;
    protected double newX, newY;
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
            if (!level.tileCollision(projX, projY, newX, newY, sprite.SIZE))
            {
            move();
            }
            else
            {
                remove();
            }
        } else
        {
            remove();
        }
        
        calculateDistance();
    }
    
    public void render(Screen screen)
    {
        screen.renderEntity((int)projX, (int)projY, sprite);
    }
    
    protected void move()
    {
            projX += newX;
            projY += newY;       
    }
    
    protected double calculateDistance() 
    {
        distance = Math.sqrt(((xOrigin - projX) * (xOrigin - projX) + (yOrigin - projY) * (yOrigin - projY)));
        return distance;
    }
    
    public void init(Level level)
    {
        this.level = level;
    }
    
}

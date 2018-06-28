
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.entity.DynamicEntity;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.entity.projectile.Projectile;
import com.bradboughn.rain.entity.projectile.WizardProjectile;
import com.bradboughn.rain.graphics.AnimatedSprite;
import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends DynamicEntity 
{
    {
        type = Entity.Type.MOB;
        value = "mob";
    }
    
    protected boolean walking = false;
    protected double speed = 1;
    
    protected List<Projectile> projectiles = new ArrayList();
    
    protected AnimatedSprite animSprite;
    
    protected AnimatedSprite anim_up;
    protected AnimatedSprite anim_right;
    protected AnimatedSprite anim_down;
    protected AnimatedSprite anim_left;

    public Mob(double x, double y)
    {
        super(x, y);
    }

    protected enum Direction 
    {
        UP, DOWN, LEFT, RIGHT
    }
    
    protected Direction dir = Direction.DOWN;
    
    protected boolean checkMovement(double xa, double ya)
    {
        if (xa != 0 || ya != 0)
        {
            walking = true;
        }
        else walking = false;
        
        return walking;
    }
    
    public void move(double xa, double ya, AABB aabb) 
    {
        if(xa != 0 && ya != 0) 
        {
            move(xa,0,aabb);
            move(0,ya,aabb);
            return;
        }
        if (ya > 0) dir = Direction.DOWN;
        if (ya < 0) dir = Direction.UP;
        if (xa > 0) dir = Direction.RIGHT;
        if (xa < 0) dir = Direction.LEFT;
        
//        double[] collisionCheck = level.tileCollision(x, y, xa, ya, aabb);
//        //First index is 0=no collision; 1=collision detected
//        updateXs((int)collisionCheck[1]);
//        updateYs((int)collisionCheck[2]);
        updateXs((int)xa);
        updateYs((int)ya);
        
        
    }
    
    public abstract void update(); 
    
    public abstract void render();
    
    public void updateAnimation()
    {
        switch (dir)
        {
            case UP:
                animSprite = anim_up;
                break;
            case RIGHT:
                animSprite = anim_right;
                break;
            case DOWN:
                animSprite = anim_down;
                break;
            case LEFT:
                animSprite = anim_left;
                break;
            default:
                animSprite = anim_down;
        }
        if (walking)
        {
            animSprite.update();
            setSprite(animSprite.getSprite());
        }
        else setSprite(animSprite.getSpriteAt(0));        
    }
    
    //Finding four corners of tile for collision OLD METHOD NOT USED ATM
//    private boolean collision(int xa, int ya) //xa and ya are either 1, 0, or -1, depending on movement
//    {
//        boolean solid = false;
//        for (int c = 0; c < 4; c++)
//        {
//            //<editor-fold defaultstate="collapsed" desc="Collision "info"">
//            /*  Actual "corner code" goes between "(x+xa)" and "/16". (x+xa) is obv. the absolute position of
//            *   Mob, and "/16" gets it in Tile precision, as opposed to pixel.
//            *   NOTE: To adjust/play with tile collision here, think of this algorithm kinda like this:
//            *   (c % 2) and (c / 2) cycles through the 4 corners of a tile for each axis, in a way. The
//            *   x portion checks for 4 corners of a tile ON THE X AXIS, and the y portion checks for the
//            *   same, but ON THE Y AXIS, to locate the actual tile using those x and y values. Now the
//            *   actual collision stuff:
//            *   (* 14 -7) and (* 12 + 3) KIND OF work in the following way:
//            *   the "* 14" for the x, can be changed to change how far/close on the right side/corner of a tile to
//            *   classify it as colliding. The "-7" does the same, but checks for the left side/corner of a tile.
//            *   The exact same is true for y. "* 12" handles the bottom side/corner, and "+3" handles top.
//            *   you can change these values to achieve different sized collision boxes.
//            */
////</editor-fold>
//            int xTile = ((x + xa) + c % 2 * 14 - 7) / 16; 
//            int yTile = ((y +ya) + c / 2 * 12 + 3) / 16;
//            if (level.getTile(xTile, yTile).isSolid()) solid = true;
//        }
//        return solid;
//    }
    
        //should be "addProjectiles()" I thinks
    public void shoot(double x, double y, double slope)
    {
        Projectile p = new WizardProjectile(x, y, slope);
        
        level.add(p);
    }
    
    public void updateProjectiles()
    {
        for (Projectile p : projectiles)
        {
            p.update();
        }
    }
    
    public void renderProjectiles()
    {
        for (Projectile p : projectiles)
        {
            p.render();
        }
    }
    
    public void clearProjectiles()
    {
        
    }
    
}

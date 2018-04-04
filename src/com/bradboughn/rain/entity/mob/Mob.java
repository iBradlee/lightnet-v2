
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.Game;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.entity.projectile.Projectile;
import com.bradboughn.rain.entity.projectile.WizardProjectile;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends Entity 
{
    
    protected Sprite sprite;
    protected int dir = 0;
    protected double speed = 50;

    protected boolean moving = false;
    
    protected List<Projectile> projectiles = new ArrayList();
    
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
            if (ya == 0)
            {
                
            }
        x += xa;
        y += ya;
        }
    }
    
    public void update() 
    {
        updateProjectiles();
    }
    
    public void render(Screen screen) 
    {
        renderProjectiles(screen);
    }
    
    //Finding four corners of tile for collision
    private boolean collision(int xa, int ya) //xa and ya are either 1, 0, or -1, depending on movement
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
            if (level.getTile(xTile, yTile).solid()) solid = true;
        }
        return solid;
    }
    
        //should be "addProjectiles()" I thinks
    public void shoot(int x, int y, double slope)
    {
        Projectile p = new WizardProjectile(x, y, slope);
        
//        projectiles.add(p);
        level.addProjectile(p);
    }
    
    public void updateProjectiles()
    {
        for (Projectile p : projectiles)
        {
            p.update();
        }
    }
    
    public void renderProjectiles(Screen screen)
    {
        for (Projectile p : projectiles)
        {
            p.render(screen);
        }
    }
    
    public void clearProjectiles()
    {
//        for (int i = 0; i < projectiles.size(); i++)
//        {
//            if(projectiles.get(i).isRemoved())
//            {
//                projectiles.remove(i);
//            }
//        }
        level.removeLvlProjectiles();
    }
    
}

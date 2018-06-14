
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.broadphase.explicitgrid.Grid;
import com.bradboughn.rain.broadphase.explicitgrid.GridCell;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.entity.projectile.WizardProjectile;
import com.bradboughn.rain.graphics.AnimatedSprite;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.graphics.SpriteSheet;
import com.bradboughn.rain.input.Keyboard;
import com.bradboughn.rain.input.Mouse;
import com.bradboughn.rain.level.Level;
import com.bradboughn.rain.level.TileCoordinate;
import java.awt.event.KeyEvent;

public class Player extends Mob
{
    
    private int anim = 0;

    private int currentItemID;
    private int fireRate = 0;
    
    //GOES INTO WALLS WHEN SPEED GOES PAST 16!
    private int speed = 2;
    private int topSpeed = 1;
    private double acceleration = topSpeed/8.0;
    private double accumAcceleration = 0.0;
    private int ticks = 0;
    
    /*@todo 
    *   Need to create a static variable that handles the player's current coordinates/offset/whatever
    *   IN RELATION TO THE SCREEN/WINDOW, and not absolute map/level location. I want ONE variable that
    *   can be dynamic, in case we need to, during gameplay, NOT have player in center of screen. This
    *   would allow me to dynamically change the Projectile/Shooting angle/slope, depending on where
    *   the center of the player is. Also, would use this same variable to do many other things, such
    *   as handling where we're rendering the player sprite. I want, in the most basic form, the variable
    *   handling the Player's rendering coordinates to be the exact same variable that deals with
    *   handling projectile angles when using the mouse as a destination, and player location (in relation
    *   to the screen/window) as the "anchor" point, or starting point. That way, when changing the rendering
    *   location, it also dynamically changes the math behind figuring out the angle.
    */
    
    /*@todo
    *   Need to actually render player in center of screen. Player is not actually centered properly now
    */
//    public Player (Keyboard input) 
//    {
//        this.key = input;
//    }
    
    public Player (int x, int y) 
    {
        type = Type.PLAYER;
        setSprite(Sprite.DEFAULT_PLAYER);
        animSprite = anim_down;
        anim_up = new AnimatedSprite(32,32, SpriteSheet.player_up);
        anim_right = new AnimatedSprite(32,32, SpriteSheet.player_right);
        anim_down = new AnimatedSprite(32,32, SpriteSheet.player_down);
        anim_left = new AnimatedSprite(32,32, SpriteSheet.player_left);
        this.x = x;
        this.y = y;
        setCenter();

        fireRate = WizardProjectile.FIRE_RATE;
        
        aabb = new AABB(x , y , 16 , 16, this);
    }
    
    public void update() 
    {
        if (Keyboard.isKey(KeyEvent.VK_F1))
        {
            TileCoordinate tc = new TileCoordinate(14,34);
            x = tc.getX();
            y = tc.getY();
        }
        //I'M NOT actually running multiple times per frame ATM
        recursiveUpdate();
        if (fireRate > 0) fireRate--;
        updateAnimation();
//        clearProjectiles();
        updateShooting();
//        updateProjectiles();
    }
    
    public void recursiveUpdate()
    {
//        if (ticks < 3) 
//        {
        int xa = 0, ya = 0;
        if (Keyboard.up) ya-= speed;
        if (Keyboard.down) ya+= speed;
        if (Keyboard.left) xa-= speed;
        if (Keyboard.right) xa+= speed;

        if (xa != 0 || ya != 0) {
            move(xa,ya, aabb);
            walking = true;
        } else {
            walking = false;
        }  
        updateGridCells();
    }
    
    public void render()
    {
//        int xx = x - 16;
//        int yy = y - 16;
        Camera.renderSprite(x, y, getSprite(), true);
        aabb.setAABBpos();
        aabb.renderAABB();

    }
    /**
     * Checks for correct input to shoot, at the start. Finds delta x, and delta y, between current
     * player location, and mouse coordinates. Using both delta variables, it then uses the Math.atan2()
     * function to calculate the slope of the angle between the player and mouse. After all this, it 
     * will call Mob.shoot(int x, int y, double slope), and let this method handle the actual creating
     * of the new Projectile object, as well as adding it to the Level and/or Mob ArrayLists for Projectiles.
     */
    public void updateShooting()
    {
        if (Mouse.isMouseL() && fireRate <=0)
        {
            double dx = Mouse.getX() - (x + getSprite().WIDTH/2 - Camera.getOffsetX());
            double dy = Mouse.getY() - (y + getSprite().HEIGHT/2 - Camera.getOffsetY());
            double slope = Math.atan2(dy, dx);
            shoot(x + sprHalfWidth , y + sprHalfHeight, slope);
            fireRate = WizardProjectile.FIRE_RATE;
        }
        

    }
    
    public void init(Level level) 
    {
        this.level = level;
//        level.addPlayer(this);
    }

    public void setTileCoord(TileCoordinate tc)
    {
        x = tc.getX();
        y = tc.getY();
    }
    
    public void setTileCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    
    
}

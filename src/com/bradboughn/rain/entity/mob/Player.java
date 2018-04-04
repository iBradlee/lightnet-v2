
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.Game;
import com.bradboughn.rain.entity.projectile.Projectile;
import com.bradboughn.rain.entity.projectile.WizardProjectile;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.input.Keyboard;
import com.bradboughn.rain.input.Mouse;

public class Player extends Mob{
    
    private Keyboard key;
    
    private Sprite sprite;
    private int anim = 0;
    private boolean walking = false;

    private int currentItemID;
    private int fireRate = 0;

    
    /*@todo 
    *   Need to create a static variable that handles the player's current coordinates/offset/whatever
    *   IN RELATION TO THE SCREEN/WINDOW, and not absolute map/level location. I want ONE variable that
    *   can be dynamic, in case we need to, during gameplay, NOT have player in center of screen. This
    *   would allow me to dynamically change the Projectile/Shooting angle/slope, depending on where
    *   the center of the player is. Also, would use this same variable to do many other things, such
    *   as handling where we're rendering the player sprite. I want, in the most basic form, the variable
    *   handling the Player's rendering coordinates to be the exact same variable that deals with
    *   handling projectile angles when using the mouse as a destination, and player location (in relation
    *   to the screen/window) as the "anchor" point, or starting point. That way, when chaning the rendering
    *   location, it also dynamically changes the math behind figuring out the angle.
    */
    
    /*@todo
    *   Need to actually render player in center of screen. Player is not actually centered properly now
    */
    public Player (Keyboard input) 
    {
        this.key = input;
    }
    
    public Player (int x, int y, Keyboard key) 
    {
        this.x = x;
        this.y = y;
        this.key = key;
        fireRate = WizardProjectile.FIRE_RATE;
    }
    
    public void update() 
    {
        if (fireRate > 0) fireRate--;
        int xa = 0, ya = 0;
        if (anim < 7500) anim++; 
        else anim = 0;
        if (key.up) ya--;
        if (key.down) ya++;
        if (key.left) xa--;
        if (key.right) xa++;
        
        if (xa != 0 || ya != 0) {
            move(xa,ya);
            walking = true;
        } else {
            walking = false;
        }     
        
        clearProjectiles();
        updateShooting();
        updateProjectiles();
        
    }
    
    public void render(Screen screen)
    {
        int xx = x - 16;
        int yy = y - 16;
        renderProjectiles(screen);
        updateAnimation();
        screen.renderEntity(xx, yy, sprite);
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
            double dx = Mouse.getX() - Game.getScreenWcenter();
            double dy = Mouse.getY() - Game.getScreenHcenter();
            double slope = Math.atan2(dy, dx);
            shoot(x, y, slope);
            fireRate = WizardProjectile.FIRE_RATE;
        }
    }
    
    public void updateAnimation()
    {
        if (dir == 0) 
        {
            sprite = Sprite.playerU;
            if (walking) 
            {
                if (anim % 30 < 10)
                    sprite = Sprite.playerU1;
                else if (anim % 30 >= 10 && anim % 30 <20)
                    sprite = Sprite.playerU;
                else sprite = Sprite.playerU2;
            }
        }
        if (dir == 1) 
        {
            sprite = Sprite.playerR;
            if (walking) 
            {
                if (anim % 30 < 10)
                    sprite = Sprite.playerR1;
                else if (anim % 30 >= 10 && anim % 30 <20)
                    sprite = Sprite.playerR;
                else sprite = Sprite.playerR2;
            }
        }
        if (dir == 2) 
        {
            sprite = Sprite.playerD;
            if (walking) 
            {
                if (anim % 30 < 10)
                    sprite = Sprite.playerD1;
                else if (anim % 30 >= 10 && anim % 30 < 20)
                    sprite = Sprite.playerD;
                else sprite = Sprite.playerD2;
            }
        }
        if (dir == 3) 
        {
            sprite = Sprite.playerL;
            if (walking) 
            {
                if (anim % 30 < 10)
                    sprite = Sprite.playerL1;
                else if (anim % 30 >= 10 && anim % 30 < 20)
                    sprite = Sprite.playerL;
                else sprite = Sprite.playerL2;
            }
            
        }
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.input.Keyboard;

/**
 *
 * @author Brad
 */
public class Player extends Mob{
    
    private Keyboard input;
    private Sprite sprite;
    private int anim = 0;
    private boolean walking = false;
    
    public Player (Keyboard input) 
    {
        this.input = input;
    }
    
    public Player (int x, int y, Keyboard input) 
    {
        this.x = x;
        this.y = y;
        this.input = input;
    }
    
    public void update() 
    {
       int xa = 0, ya = 0;
       if (anim < 7500) anim++; 
       else anim = 0;
       if (input.up) ya--;
       if (input.down) ya++;
       if (input.left) xa--;
       if (input.right) xa++;
       if (xa != 0 || ya != 0) {
           move(xa,ya);
           walking = true;
       } else {
           walking = false;
       }
    }
    
    public void render(Screen screen)
    {
        updateAnimation();
        screen.renderPlayer(x - 32, y - 32, sprite);
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

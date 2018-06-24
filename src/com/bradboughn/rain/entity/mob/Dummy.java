
package com.bradboughn.rain.entity.mob;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.graphics.AnimatedSprite;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.graphics.SpriteSheet;
import com.bradboughn.rain.input.Keyboard;
import com.bradboughn.rain.level.TileCoordinate;

public class Dummy extends Mob {
    
    private int time = rand.nextInt(40);
    private double xa = rand.nextGaussian(); 
    private double ya = rand.nextGaussian();
    
    public Dummy(double x, int y)
    {
        super(x,y);
        setSprite(Sprite.DEFAULT_MOB_DUMMY);
        animSprite = anim_up;
        //this is only for my janky animated sprite without a decent way to change animation 
        int[] animationCycle = {0,1,2,3,4,5};
        anim_up = new AnimatedSprite(32,32, animationCycle, SpriteSheet.mob_dummy_walkR);
        anim_right = new AnimatedSprite(32,32, animationCycle, SpriteSheet.mob_dummy_walkR);
        anim_down = new AnimatedSprite(32,32, animationCycle, SpriteSheet.mob_dummy_walkL);
        anim_left = new AnimatedSprite(32,32, animationCycle, SpriteSheet.mob_dummy_walkL);
        anim_up.setFrameRate(10);
        anim_right.setFrameRate(10);
        anim_down.setFrameRate(10);
        anim_left.setFrameRate(10);
        aabb = new AABB(this.x, this.y, 15, 16, this);
        
//        speed = rand.nextInt(2) + 1;
        speed = 0;
        value = "this is a fking dummy yo!";
    }
    
    int ticks = 0;
    public void update()
    {
        if (Keyboard.F1)
        {
            TileCoordinate tc = new TileCoordinate(14,34);
            x = tc.getX();
            y = tc.getY();
        }
        time++;
        if (time % 60 == 0)
        {
            xa = (rand.nextInt(3) -1) * speed;
            ya = (rand.nextInt(3) -1) * speed;
            if (rand.nextInt(5) == 0)
            {
                xa = 0;
                ya = 0;
            }
        }
        
        if (checkMovement(xa, ya))
        {
            move(xa,ya,aabb);
        }
        //want dummy ghosts to always play animation, since it looks like an idle animation as well
        walking = true;
        System.out.println("offscreen: " +  this.isOffScreen());
        updateAnimation();
        updateGridCells();
    }

    public void render()
    {
//        Camera.renderSprite(aabb.getLeftX(), aabb.getTopY(), new Sprite(aabb.getHalfwidth()<<1, aabb.getHalfheight()<<1, 0xff0f0f0f), true);
        Camera.renderSprite((int)x, (int)y, getSprite(), true);
    }

}


package com.bradboughn.rain.entity.particle;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Sprite;

public class Particle extends Entity{
    //@todo are we also creating x "amount" of arrays, as well as particles? even tho we only use 1 per
    //call of the original particle? should i just create a "sub-particle" class, to spawn all others?

    
    private int life;
    //zz can be thought of as gravity. za is change in zz per update.
    protected double xx, yy, zz;
    protected double xa, ya, za;
    
    public Particle(int x, int y, int life)
    {
        type = Type.PARTICLE;
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = rand.nextInt(life) + life;
        setSprite(new Sprite(rand.nextInt(4),rand.nextInt(4),0xffffff00)); 
        
        aabb = new AABB(x,y, getSprite().WIDTH, getSprite().HEIGHT, this);
        this.xa = rand.nextGaussian();
        this.ya = rand.nextGaussian();
        this.zz = 1.0;
    }
    
 
    public void update()
    {

        
        //WAS FKING AROUND WITH MAKING THE PARTICLE SPAWNER EMIT LITTLE BITS OF FLAME ON IMPACT.
        //I think there's several cool ways to go about hackily simulating flames. The simplest that
        //I can immediately come up with is more or less this:
        //Take the center positions of the particle spawner, when it's created on impact. Use that 
        //value to simply ask, when creating particles, "is this particle less than *center position,
        //on x axis?"; if it is, set an accumulating negative x value, to animate it left. If it's 
        //more than center position, just push it right, obviously. There will be additional binary
        //checks, to check if particle has travelled some arbitrary distance, again for example, 
        //negatively on the x axis. If so, start decrementing y to simulate upwards movement. Some of
        //these arbitrary numbers, used for the checks, can be semi-randomized allowing only a slight 
        //variation, to maintain some form of consistancy. 
        
        
        
        
        
        
        life--;
        if(life<=0) setRemovedTrue();
        

            
        if (!level.partTileCollision(xx, yy, xa, ya, aabb))
        {
            //za is simulating increase in gravity here
            za += 0.3;
            za/= 3;
            xa /= 3;
            ya /= 3;
          
            updateXs(xa);
            updateYs(ya - za);
        }
        else
        {
            setRemovedTrue();
        }
    }
    
    public void render()
    {
            Camera.renderSprite((int)xx, (int)yy , getSprite(), true);
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

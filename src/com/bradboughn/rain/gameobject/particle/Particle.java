
package com.bradboughn.rain.gameobject.particle;

import com.bradboughn.rain.gameobject.GameObject;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;

public class Particle extends GameObject{
    //@todo are we also creating x "amount" of arrays, as well as particles? even tho we only use 1 per
    //call of the original particle? should i just create a "sub-particle" class, to spawn all others?

    
    private Sprite sprite;
    private int life;
    //zz can be thought of as gravity. za is change in zz per update.
    protected double xx, yy, zz;
    protected double xa, ya, za;
    
    public Particle(int x, int y, int life)
    {
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = rand.nextInt(life) + life;
        sprite = new Sprite(rand.nextInt(4),rand.nextInt(4),0xffffff00); 
        
        this.xa = rand.nextGaussian();
        this.ya = rand.nextGaussian();
        this.zz = 0.0;
    }
    
 
    
    public void update()
    {
        life--;
        if(life<=0) remove();
        if (!level.tileCollision(xx, yy, xa, ya, sprite.WIDTH, sprite.HEIGHT))
        {
            //za is simulating increase in gravity here
            za -= 0.1;
            xx += xa;
            yy += ya - za;
        }
        else
        {
            remove();
        }
    }
    
    public void render()
    {
            Screen.renderSprite((int)xx, (int)yy , sprite, true);
    }

}

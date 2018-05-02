
package com.bradboughn.rain.gameobject;

import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.level.Level;
import java.util.Random;

public abstract class GameObject 
{
    
    protected int x, y;
    protected int centerX, centerY;
    private boolean removed = false;
    protected Level level;
    protected final Random rand = new Random();
    protected AABB aabb;
    
    public void update() 
    {
        
    }
    
    public void render () 
    {
        
    }
    
    public void remove () 
    {
        //Remove from level
        removed = true;
    }
    
    public boolean isRemoved() 
    {
        return removed;
    }
    
    public void init(Level level) 
    {
        this.level = level;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Level getLevel()
    {
        return level;
    }

    public AABB getAabb()
    {
        return aabb;
    }
    
    
}

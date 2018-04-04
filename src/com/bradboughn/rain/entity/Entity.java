
package com.bradboughn.rain.entity;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.level.Level;
import java.util.Random;

public abstract class Entity 
{
    
    public int x, y;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();
    
    
    public void update() 
    {
        
    }
    
    public void render (Screen screen) 
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
        level.addEntity(this);
    }
}

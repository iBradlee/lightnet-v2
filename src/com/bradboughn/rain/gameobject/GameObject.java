
package com.bradboughn.rain.gameobject;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.level.Level;
import java.util.Random;

public abstract class GameObject 
{
    
    public int x, y;
    private boolean removed = false;
    protected Level level;
    protected final Random rand = new Random();
    
    
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
}

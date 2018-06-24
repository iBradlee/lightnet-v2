
package com.bradboughn.rain.entity.spawner;

import com.bradboughn.rain.entity.DynamicEntity;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
import java.util.List;

public class Spawner extends DynamicEntity{
    
    private List<Entity> entities = new ArrayList();
    
    public enum Type
    {
        MOB, PARTICLE_WIZ, PARTICLE_
    }
    
    private Type type;
    
    public Spawner(double x, double y, Type type, int amount, Level level)
    {
        super(x,y);
        this.type = type;
        this.level = level;
    }
    
    public void update()
    {
        
    }
}

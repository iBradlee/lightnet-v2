
package com.bradboughn.rain.gameobject.spawner;

import com.bradboughn.rain.gameobject.GameObject;
import com.bradboughn.rain.gameobject.particle.Particle;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
import java.util.List;

public class Spawner extends GameObject{
    
    private List<GameObject> entities = new ArrayList();
    
    public enum Type
    {
        MOB, PARTICLE_WIZ, PARTICLE_
    }
    
    private Type type;
    
    public Spawner(int x, int y, Type type, int amount, Level level)
    {
        this.x = x; 
        this.y = y;
        this.type = type;
        this.level = level;
    }
}

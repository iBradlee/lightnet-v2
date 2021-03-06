
package com.bradboughn.rain.gameobject.spawner;

import com.bradboughn.rain.gameobject.particle.Particle;
import com.bradboughn.rain.level.Level;

public class ParticleSpawner extends Spawner 
{
    
    private int life;
    
    public ParticleSpawner(int x, int y, Type type, int life, int amount, Level level) 
    {
        super(x, y, Type.PARTICLE_WIZ, amount, level);
        this.life = life;
        for (int i = 0; i < amount; i++)
        {
            if (type == Type.PARTICLE_WIZ)
                level.add(new Particle(x, y, life));
        }

    }

}

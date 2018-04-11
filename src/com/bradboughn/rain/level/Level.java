
package com.bradboughn.rain.level;

import com.bradboughn.rain.gameobject.GameObject;
import com.bradboughn.rain.gameobject.spawner.Spawner;
import com.bradboughn.rain.gameobject.particle.Particle;
import com.bradboughn.rain.gameobject.projectile.Projectile;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.level.tile.Tile;
import java.util.ArrayList;
import java.util.List;

public class Level 
{
    
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;
    //@todo Would like to have an ArrayList that holds lists. This would handle entities that have
    //their own arraylist, in order to keep track of the parent of some of these added entities/lists.
    //for example, each mob will have their own arraylists for projectile/etc., so that we know what
    //projectile kills something
    private List<GameObject> entities = new ArrayList();
    private List<Projectile> projectiles = new ArrayList();
    private List<Particle> particles = new ArrayList();
    
    
    public static Level spawn = new SpawnLevel ("/textures/levels/spawn_level.png");
    
    public Level(int width, int height)
    {
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
        
    }
    
    public Level(String path)
    {
        loadLevel(path);
        generateLevel();
        
//        add(new Spawner(23 * 16, 37 * 16, Spawner.Type.PARTICLE, 10000, this));
    }
    
    protected void generateLevel()
    {
        
    }
    
    protected void loadLevel(String path)
    {
        
    }
    
    public void update()
    {
        updateEntities();
//        System.out.println(lvlProjectiles.size());
    }
    
    private void time() 
    {
        
    }
    
    public boolean tileCollision(double x, double y, double newX, double newY, int width, int height)
    {
        boolean solid = false;
        for (int c = 0; c < 4; c++)
        {
            //<editor-fold defaultstate="collapsed" desc="Collision "info"">
            /*  Actual "corner code" goes between "(x+xa)" and "/16". (x+xa) is obv. the absolute position of
            *   Mob, and "/16" gets it in Tile precision, as opposed to pixel.
            *   NOTE: To adjust/play with tile collision here, think of this algorithm kinda like this:
            *   (c % 2) and (c / 2) cycles through the 4 corners of a tile for each axis, in a way. The
            *   x portion checks for 4 corners of a tile ON THE X AXIS, and the y portion checks for the
            *   same, but ON THE Y AXIS, to locate the actual tile using those x and y values. Now the
            *   actual collision stuff:
            *   (* 14 -7) and (* 12 + 3) KIND OF work in the following way:
            *   the "* 14" for the x, can be changed to change how far/close on the right side/corner of a tile to
            *   classify it as colliding. The "-7" does the same, but checks for the left side/corner of a tile.
            *   The exact same is true for y. "* 12" handles the bottom side/corner, and "+3" handles top.
            *   you can change these values to achieve different sized collision boxes.
            */
//</editor-fold>
            double xTile = ((x + newX) + c % 2 * width / 10 + 2) / 16; 
            double yTile = ((y + newY) + c / 2 * height / 6 + 4) / 16;

            if (getTile((int)xTile, (int)yTile).solid()) solid = true;
        }
        return solid;
    }
    
    //Render method is finding all 4 sides, and the current position on the map, of the screen, then renders each tile individually
    public void render(int xScroll,int yScroll)
    {      
        Screen.setOffset(xScroll, yScroll);                          //setting/updating offset in our screen, based on player movement
        int x0 = xScroll >> 4; // left side                          >>4, is same as divided by 16. This has it check/render every tile, instead of pixels.
        int x1 = (xScroll + Screen.getWidth() + 16) >> 4; // right side        >>4 puts numbers in tile precision
        int y0 = yScroll >> 4;//top side            
        int y1 = (yScroll + Screen.getHeight() + 16) >> 4; // bottom side || +16 adds another tile to fully cover screen.
        
        for (int y = y0; y < y1; y++) 
        {
            for (int x = x0; x < x1; x++) 
            {
                getTile(x,y).render(x, y);
                // x and y grab every tile on screen currently, by taking the x0, y0 variable
            }    
        }   
            renderEntities();          
    }
    //Entity functions
    public void add(GameObject e)
    {
        e.init(this);
        if (e instanceof Particle)
        {
            particles.add((Particle)e);
        }
        else if (e instanceof Projectile)
        {
            projectiles.add((Projectile)e);
        }
        else
        {
            entities.add(e);
        }
    }
    
    public void removeEntities()
    {
        for (int i = 0; i < entities.size(); i++)
        {
            if(entities.get(i).isRemoved()) entities.remove(i);
        }
        for (int i = 0; i < projectiles.size(); i++)
        {
            if(projectiles.get(i).isRemoved()) projectiles.remove(i);
        }
        for (int i = 0; i < particles.size(); i++)
        {
            if(particles.get(i).isRemoved()) particles.remove(i);
        }
    }
    
    public void updateEntities()
    {
        //starting at i = ent.size, so when one is removed, it doesn't skip any. this way, entities that
        //are removed are replaced by entities which you have already checked.
        for (int i = entities.size()-1 ; i >= 0; i--)
        {
            if (entities.get(i).isRemoved()) entities.remove(i);
            else entities.get(i).update();
        }
        for (int i = projectiles.size()-1 ; i >= 0; i--)
        {
            if (projectiles.get(i).isRemoved()) projectiles.remove(i);
            else projectiles.get(i).update();
        }
        for (int i = particles.size()-1 ; i >= 0; i--)
        {
            if (particles.get(i).isRemoved()) particles.remove(i);
            else particles.get(i).update();
        }
    }
    
    public void renderEntities()
    {
        for (GameObject e : entities)
        {
            e.render();
        }
        for (Projectile p : projectiles)
        {
            p.render();
        }
        for (Particle p : particles)
        {
            p.render();
        }
    }
    
    //ALL BELOW I BELIEVE IS OLD CODE

    
    //Grass = 0x00FF00    
    //Flower = 0xFFFF00
    //Rock = 0x7F7F00
    
    //SPAWNS
    //Stone wall = 2E3D46
    //Bronze/Stone = C58F2D
    //Wood floor = 4D2201
    //Dark grass = 2C5E00
    //Spawn point = 00FFFF
    
    public Tile getTile (int x, int y) 
    {
        if (x < 0 || y < 0 || x >= width || y >= height) return Tile.spawn_water;
        if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass;
        if (tiles[x + y * width] == Tile.col_grass_flower) return Tile.grassFlower;
        if (tiles[x + y * width] == 0xff7f7f00) return Tile.grassRock;
        if (tiles[x + y * width] == Tile.col_spawn_stoneWall) return Tile.spawn_stoneWall; 
        if (tiles[x + y * width] == Tile.col_spawn_StoneWall2) return Tile.spawn_stoneWall2; 
        if (tiles[x + y * width] == Tile.col_spawn_woodFloor) return Tile.spawn_woodFloor;
        if (tiles[x + y * width] == Tile.col_spawn_hedge) return Tile.spawn_hedge;
        if (tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water;
        return Tile.voidTile;
    }
}

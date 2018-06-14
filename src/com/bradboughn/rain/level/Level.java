
package com.bradboughn.rain.level;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.entity.mob.Dummy;
import com.bradboughn.rain.entity.particle.Particle;
import com.bradboughn.rain.entity.projectile.Projectile;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.input.Keyboard;
import com.bradboughn.rain.level.tile.Tile;
import java.awt.event.KeyEvent;
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
    private List<Entity> entities = new ArrayList();
    private List<Entity> offScreenEntities = new ArrayList();
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
        if (Keyboard.isKey(KeyEvent.VK_0))
        {
            add(new Dummy(20,27));
        }
        updateEntities();
        updateOffScreenEntities();
//        System.out.println(lvlProjectiles.size());
    }
    
    private void time() 
    {
        
    }
    
    //don't believe there's any reason to pass in as double; think everything casts to int before computing
    //THE PROBLEM THAT I CAN SEE SO FAR, WITH THE OBJECT "JUMPING" WHEN COLLIDING, AND NOT ENDING IN THE
    //RIGHT SPOT, IS THE FACT THAT IT'S DETECTING A COLLISION AND RESOLVING IT **ON THE WRONG AXIS!**
    //SO WHEN SHOOTING UP, AND RIGHT, AND THE OBJECT JUMPS TO THE LEFT WHEN COLLIDING, WHAT'S HAPPENING
    //IS INSTEAD OF DETECTING AND RESOLVING THE COLLISON ON THE Y AXIS (AS IT SHOULD), IT'S NOT DETECTING
    //THE Y COLLISION, AND INSTEAD DETECTING A COLLISION ON THE X AXIS (FOR SOME REASON), AND FORCING IT TO JUMP
    //TO THE OUTSIDE EDGE OF THE TILE IT'S "COLLIDING" WITH. It shouldn't ever, in this scenario, detect
    //an x axis collision, UNLESS (i think) it has already entered a tile by moving upwards on the y axis,
    //thus placing the object inside a tile, and then when it tries to move right on the x axis, it now
    //detects a collision with the tile it's inside of, and resolves it to move to the opposite (left in this case)
    //side of the tile it's inside. That's my best guess atm
    public double[] tileCollision(double x, double y, double dx, double dy, AABB aabb)
    {
        aabb.setAABBpos();
        
        //First index detects collision: 0 = false, 1 = true
        //Second and third are resolution for collision, if any. They move max distance between solid
        //object and base object, without entering. If no collision, dx/dy stay the same, and travel as usual.
        double[] collisionDetectionResolution = {0,dx,dy};
        
        
        if (dx != 0.0)
        {
            //get tiles in which top/bottom side of AABB fall in, to see how many y axis rows need to be checked
            int top = aabb.getTopY()/Tile.TILE_SIZE;
            int bottom = aabb.getBottomY()/Tile.TILE_SIZE;
            
            if (dx < 0)
            {
                //get starting scan location/tile for x axis (starting is forward-facing edge)
                int forwardEdge = aabb.getLeftX()/Tile.TILE_SIZE;
                //get ending scan tile for x axis (check how many col dx will reach, if any more than starting)
                int endCol = (aabb.getLeftX() + (int)dx)/Tile.TILE_SIZE;
//                System.out.println("forward : " + forwardEdge + ",    endCol : " + endCol);

                //loop thru each x axis column, then for each, loop thru all y axis rows the AABB could collide with
                for (int xCol = forwardEdge ; xCol >= endCol; xCol--)
                {
                    for (int yRow = top; yRow <= bottom; yRow++ )
                    {
                        if (getTile(xCol, yRow).isSolid()) 
                        {
                            //create temp aabb for tile which is solid, and in our path
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
//                            Camera.setVisAABB(tileAabb);
                            double deltaX = tileAabb.getRightX() - aabb.getLeftX();
                            //if difference between tile aabb's right side, and main aabb's left side
                            //is shorter than dx (our target movement), then store shortest distance
                            //in "newMovement", and return.
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[1] = deltaX > dx ? deltaX : dx;  
                        }
                    }
                }
                
            }
            else
            {
                
                //THIS SECTION IS CAUSING PARTICLE/PARTICLE SPAWNER TO NO LONGER FUCNTION, AND INSTEAD,
                //JUST DISSAPPEAR AFTER A FRACTION OF A SECOND. When projectile collides, the console
                //still spits out forward : 0, endCol : 0. I don't know why it's still running ~15 times
                //after the object should be removed from our arraylist, in which it's update is called,
                //in which it's movement is called. It must still think it's moving? Could be that new
                //updateX,updateY methods...
                
                int forwardEdge = aabb.getRightX()/Tile.TILE_SIZE;
                int endCol = (aabb.getRightX() + (int)dx)/Tile.TILE_SIZE;

                for (int xCol = forwardEdge ; xCol <= endCol; xCol++)
                {
                    for (int yRow = top; yRow <= bottom; yRow++ )
                    {
                        if (getTile(xCol, yRow).isSolid()) 
                        {
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
//                            Camera.setVisAABB(tileAabb);
                            //FOR SOME REASON, without taking away 1 pix from the right side (or tile left).
                            //it thinks i'm 1 pixel too far, and i get stuck in walls going right, and then
                            //trying to move up/down. PRETTY SURE it's the sprite not having an actual
                            //center point, since it's 36x36, and thusly, the aabb's center (based on sprite center)
                            //is also skewed a bit to one side (the left i believe)
                            double deltaX = tileAabb.getLeftX() - aabb.getRightX() ;

                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[1] = deltaX < dx ? deltaX : dx;  
                        }
                    }
                }
            }
        }
        
        if (dy != 0.0)
        {
            int left = aabb.getLeftX()/Tile.TILE_SIZE;
            int right = aabb.getRightX()/Tile.TILE_SIZE;
            
            if (dy < 0)
            {
                int forwardEdge = aabb.getTopY()/Tile.TILE_SIZE;
                int endRow = (aabb.getTopY() + (int)dy)/Tile.TILE_SIZE;
                for (int yRow = forwardEdge; yRow >= endRow; yRow--)
                {
                    for (int xCol = left; xCol <= right; xCol++)
                    {
                        if (getTile(xCol, yRow).isSolid())
                        {
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
//                            Camera.setVisAABB(tileAabb);
                            double deltaY = tileAabb.getBottomY() - aabb.getTopY();
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[2] = deltaY > dy ? deltaY : dy;
                            ///////////////////////////////////////////////////////////////
                            //SWITCHING "<" FOR ">" FIXES PLAYER/MOB COLLISION, BUT MAKES PROJECTILE MOVEMENT JUMP WHEN HITTING CERTAIN TOP/BOTTOMS OF TILES
                            ////////////////////////////////////////////////////////////////
                        }
                    }
                }
            }
            else
            {
                int forwardEdge = aabb.getBottomY()/Tile.TILE_SIZE;
                int endRow = (aabb.getBottomY() + (int)dy)/Tile.TILE_SIZE;
                
                for (int yRow = forwardEdge; yRow <= endRow; yRow++)
                {
                    for (int xCol = left; xCol <= right; xCol++)
                    {
                        if (getTile(xCol, yRow).isSolid())
                        {
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                            Camera.setVisAABB(tileAabb);
                            //SAME ISSUE AS Moving right on x axis. Without subtracting 1, it thinks
                            //it's bottom edge is inside the wall, more than likely because of the 
                            //fact that a 36x36 sprite has no true center point, and bc of that, the
                            //aabb has no true center point either
                            double deltaY = tileAabb.getTopY() - aabb.getBottomY();
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[2] = deltaY < dy ? deltaY : dy; 
                            ///////////////////////////////////////////////////////////////
                            //SWITCHING ">" FOR "<" FIXES PLAYER/MOB COLLISION, BUT MAKES PROJECTILE MOVEMENT JUMP WHEN HITTING CERTAIN TOP/BOTTOMS OF TILES
                            ////////////////////////////////////////////////////////////////
                        }
                    }
                }
            }
        }
        return collisionDetectionResolution;
    }
    
    //The ONLY DIFFERENCE between this method, and regular tileCollision, is that projectile doesn't
    //subtract 1 from the delta, when checking right side & bottom side collisions. If I use the same
    //method for projectiles, funnily enough THEY get stuck in that glitch. I'd guess the proj's 
    //sprite probably has an actual center, so measurements aren't thrown off.
    public double[] projTileCollision(double x, double y, double dx, double dy, AABB aabb)
    {
        aabb.setAABBpos();
        double[] collisionDetectionResolution = {0,dx,dy};
        
        
        if (dx != 0)
        {
            //get tiles in which top/bottom side of AABB fall in, to see how many y axis rows need to be checked
            int top = aabb.getTopY()/Tile.TILE_SIZE;
            int bottom = aabb.getBottomY()/Tile.TILE_SIZE;
            
            if (dx < 0)
            {
                //get starting scan location/tile for x axis (starting is forward-facing edge)
                int forwardEdge = aabb.getLeftX()/Tile.TILE_SIZE;
                //get ending scan tile for x axis (check how many col dx will reach, if any more than starting)
                int endCol = (aabb.getLeftX() + (int)dx)/Tile.TILE_SIZE;
//                System.out.println("forward : " + forwardEdge + ",    endCol : " + endCol);

                //loop thru each x axis column, then for each, loop thru all y axis rows the AABB could collide with
                for (int xCol = forwardEdge ; xCol >= endCol; xCol--)
                {
                    for (int yRow = top; yRow <= bottom; yRow++ )
                    {
                        if (getTile(xCol, yRow).isSolid()) 
                        {
                            //create temp aabb for tile which is solid, and in our path
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                            Camera.setVisAABB(tileAabb);
                            int deltaX = tileAabb.getRightX() - aabb.getLeftX();
                            //if difference between tile aabb's right side, and main aabb's left side
                            //is shorter than dx (our target movement), then store shortest distance
                            //in "newMovement", and return.
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[1] = deltaX > dx ? deltaX : dx;  

                        }
                    }
                }
                
            }
            else
            {
                
                //THIS SECTION IS CAUSING PARTICLE/PARTICLE SPAWNER TO NO LONGER FUCNTION, AND INSTEAD,
                //JUST DISSAPPEAR AFTER A FRACTION OF A SECOND. When projectile collides, the console
                //still spits out forward : 0, endCol : 0. I don't know why it's still running ~15 times
                //after the object should be removed from our arraylist, in which it's update is called,
                //in which it's movement is called. It must still think it's moving? Could be that new
                //updateX,updateY methods...
                
                int forwardEdge = aabb.getRightX()/Tile.TILE_SIZE;
                int endCol = (aabb.getRightX() + (int)dx)/Tile.TILE_SIZE;

                for (int xCol = forwardEdge ; xCol <= endCol; xCol++)
                {
                    for (int yRow = top; yRow <= bottom; yRow++ )
                    {
                        if (getTile(xCol, yRow).isSolid()) 
                        {
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                            Camera.setVisAABB(tileAabb);
                            //FOR SOME REASON, without taking away 1 pix from the right side (or tile left).
                            //it thinks i'm 1 pixel too far, and i get stuck in walls going right, and then
                            //trying to move up/down. PRETTY SURE it's the sprite not having an actual
                            //center point, since it's 36x36, and thusly, the aabb's center (based on sprite center)
                            //is also skewed a bit to one side (the left i believe)
                            int deltaX = tileAabb.getLeftX() - aabb.getRightX();
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[1] = deltaX < dx ? deltaX : dx;  
                        }
                    }
                }
            }
        }
        if (dy != 0)
        {

            int left = aabb.getLeftX()/Tile.TILE_SIZE;
            int right = aabb.getRightX()/Tile.TILE_SIZE;
            
            if (dy < 0)
            {
                int forwardEdge = aabb.getTopY()/Tile.TILE_SIZE;
                int endRow = (aabb.getTopY() + (int)dy)/Tile.TILE_SIZE;
                
                for (int yRow = forwardEdge; yRow >= endRow; yRow--)
                {
                    for (int xCol = left; xCol <= right; xCol++)
                    {
                        if (getTile(xCol, yRow).isSolid())
                        {
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                            Camera.setVisAABB(tileAabb);
                            int deltaY = tileAabb.getBottomY() - aabb.getTopY();
                            
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[2] = deltaY > dy ? deltaY : dy; 
                        }
                    }
                }
            }
            else
            {
                int forwardEdge = aabb.getBottomY()/Tile.TILE_SIZE;
                int endRow = (aabb.getBottomY() + (int)dy)/Tile.TILE_SIZE;
                
                for (int yRow = forwardEdge; yRow <= endRow; yRow++)
                {
                    for (int xCol = left; xCol <= right; xCol++)
                    {
                        if (getTile(xCol, yRow).isSolid())
                        {
                            AABB tileAabb = new AABB(xCol*Tile.TILE_SIZE, yRow*Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                            Camera.setVisAABB(tileAabb);
                            //SAME ISSUE AS Moving right on x axis. Without subtracting 1, it thinks
                            //it's bottom edge is inside the wall, more than likely because of the 
                            //fact that a 36x36 sprite has no true center point, and bc of that, the
                            //aabb has no true center point either
                            int deltaY = tileAabb.getTopY() - aabb.getBottomY();
                            
                            collisionDetectionResolution[0] = 1;
                            collisionDetectionResolution[2] = deltaY < dy ? deltaY : dy; 
                        }
                    }
                }
            }
        }
        return collisionDetectionResolution;
    }
    
    public boolean partTileCollision(double x, double y, double dx, double dy, AABB aabb)
    {
        boolean collide = false;
        double xt = (x + dx)/Tile.TILE_SIZE;
        double yt = (y + dy)/Tile.TILE_SIZE;
        if (getTile((int)xt, (int)yt).isSolid()) 
        {
            AABB tileAabb = new AABB((int)xt * Tile.TILE_SIZE, (int)yt * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);

            aabb.setAABBpos();
            if (AABB.checkAABBvAABB(aabb, tileAabb)) return true;
        }
//            System.out.println("TILE IS SOLID BITCH!" + 
//                "\nxt : " + (int)xt  + ",        yt : " + (int)yt);
        return collide;
    }
    
    //Render method is finding all 4 sides, and the current position on the map, of the screen, then renders each tile individually
//    public void render(int xScroll,int yScroll)
//    {      
//        Screen.setOffset(xScroll, yScroll);                          //setting/updating offset in our screen, based on player movement
//        int x0 = xScroll >> 4; // left side                          >>4, is same as divided by 16. This has it check/render every tile, instead of pixels.
//        int x1 = (xScroll + Screen.getWidth() + 16) >> 4; // right side        >>4 puts numbers in tile precision
//        int y0 = yScroll >> 4;//top side            
//        int y1 = (yScroll + Screen.getHeight() + 16) >> 4; // bottom side || +16 adds another tile to fully cover screen.
//        
//        for (int y = y0; y < y1; y++) 
//        {
//            for (int x = x0; x < x1; x++) 
//            {
//                getTile(x,y).render(x, y);
//                // x and y grab every tile on screen currently, by taking the x0, y0 variable
//            }    
//        }   
//            renderEntities();
//    }
    //Entity functions
    //THERE IS A SMALL ISSUE WITH THE WAY I ADD ENTITIES, AND MORE IMPORTANTLY ADD BACK INTO A LEVEL LIST:
    //OBJECTS ARE INITIALIZED EVERY SINGLE TIME THEY'RE ADDED. NOW THAT I REMOVE ENTITIES WHEN THEY'RE
    //OFF SCREEN, WHEN I ADD THEM AGAIN, IT RUNS THE INITIALIZATION EVERY SINGLE TIME! THAT'S BAD PROGRAMMING! ========================================================NOTICE ME SENPAI============
    //IDEAS:
    //I could just simply create another "add()" method, where the only difference is it leaves out the
    //initialization, and be sure to name the new add method accordingly. I DO think when objects are
    //added to the Level's lists, they SHOULD be initialized. It makes sense. I'm feeling quite dumb, 
    //and can't think of the correct name for a... ahem... "re-add" method. But the new add method should be named the
    //not completely retarded version of that.
    public void add(Entity e)
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
    
    public void addToOffScreen(Entity e)
    {
        offScreenEntities.add(e);
    }
    
    //only difference is this doesn't run init(), because Player already ran init() in main game class
    //because of that, it inits Player, which adds level, and calls level.add(this), which then tries
    //to initialize again, and goes 'round and 'round.
    public void addPlayer(Entity go)
    {
        entities.add(go);
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
    
    public void updateOffScreenEntities()
    {
        for (int i = 0; i < offScreenEntities.size(); i++)
        {
            if (offScreenEntities.get(i).isOffScreen() == false)
            {
                offScreenEntities.remove(i);
                continue;
            } 
            else
            {
                offScreenEntities.get(i).updateOffScreen();
            }
        }
    }
    
    public void renderEntities()
    {
        for (Entity e : entities)
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
        if (tiles[x + y * width] == Tile.COL_SPAWN_GRASS) return Tile.spawn_grass;
        if (tiles[x + y * width] == Tile.COL_SPAWN_GRASS_FLOWER) return Tile.grassFlower;
        if (tiles[x + y * width] == 0xff7f7f00) return Tile.grassRock;
        if (tiles[x + y * width] == Tile.COL_SPAWN_WALL_STONE) return Tile.spawn_stoneWall; 
        if (tiles[x + y * width] == Tile.COL_SPAWN_WALL_STONE2) return Tile.spawn_stoneWall2; 
        if (tiles[x + y * width] == Tile.COL_SPAWN_FLOOR_WOOD) return Tile.spawn_woodFloor;
        if (tiles[x + y * width] == Tile.COL_SPAWN_HEDGE) return Tile.spawn_hedge;
        if (tiles[x + y * width] == Tile.COL_SPAWN_WATER) return Tile.spawn_water;
        return Tile.voidTile;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    
}

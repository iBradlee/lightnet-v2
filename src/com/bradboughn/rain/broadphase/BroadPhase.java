
package com.bradboughn.rain.broadphase;

import com.bradboughn.rain.broadphase.explicitgrid.CellCoord;
import com.bradboughn.rain.broadphase.explicitgrid.Grid;
import com.bradboughn.rain.broadphase.explicitgrid.GridCell;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.entity.projectile.Projectile;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BroadPhase 
{
    private static Level level;
    
    private static List<Entity> activeEntities = new ArrayList();
    private static List<Entity> activeProjectiles = new ArrayList();
    private static List<Entity[]> pairs = new ArrayList();
    
    public static void init(Level level)
    {
        BroadPhase.level = level;
        for (Entity e : level.getEntities())
        {
            activeEntities.add(e);
        }
        
        for (Entity e : level.getProjectiles())
        {
            activeProjectiles.add(e);
        }
    }
    
    public static void update()
    {
        clearLists();
        updateLists();
        checkAllEntities();
        checkAllProjectiles();
//        testPrintLists();
    }
    private static int counter = 0;
    
    private static void checkAllEntities()
    {
        for (int i = 0; i < activeEntities.size(); i++)
        {
            Entity e = activeEntities.get(i);
            GridCell gc = e.getOccupiedCell();
            
            //need to check initial cell a bit differently, making sure not to add itself as a pair
            checkOwnCellForEntities(gc, e);
            checkCellForEntities(gc.getnTop(), e);
            checkCellForEntities(gc.getnBot(), e);
            checkCellForEntities(gc.getnLeft(), e);
            checkCellForEntities(gc.getnRight(), e);
            checkCellForEntities(gc.getnTopLeft(), e);
            checkCellForEntities(gc.getnTopRight(), e);
            checkCellForEntities(gc.getnBotLeft(), e);
            checkCellForEntities(gc.getnBotRight(), e);
        }
    }
    
    private static void checkAllProjectiles()
    {
        for (int i = 0; i < activeProjectiles.size(); i++)
        {
            Entity e = activeProjectiles.get(i);
            GridCell gc = e.getOccupiedCell();
            
            //need to check initial cell a bit differently, making sure not to add itself as a pair
            checkOwnCellForEntities(gc, e);
            checkCellForEntities(gc.getnTop(), e);
            checkCellForEntities(gc.getnBot(), e);
            checkCellForEntities(gc.getnLeft(), e);
            checkCellForEntities(gc.getnRight(), e);
            checkCellForEntities(gc.getnTopLeft(), e);
            checkCellForEntities(gc.getnTopRight(), e);
            checkCellForEntities(gc.getnBotLeft(), e);
            checkCellForEntities(gc.getnBotRight(), e);
        }
    }
    
    private static void checkOwnCellForEntities(GridCell gc, Entity e)
    {
        //it's crashing here, when player runs this method. I just divided up Entity into two sub-classes,
        //dynamic and static entities. moved some methods/variables around as I saw fit. something's broked!
        //to test what's causing this, I can go and put back each method/var (from Dynamic) one at a time, back into
        //the main entity class, and see where it starts wroking again, if it does.
        if (gc.inhabitants.size() <= 1) return;
        //check for entities in list, while not checking entity itself
        queuePairsInOwnCell(gc, e);
    }
    
    private static void checkCellForEntities(GridCell gc, Entity e)
    {
        //need to account for border cells without certain neighbors
        if (gc == null) return;
        if (gc.isEmpty() == true) return;
        
        queuePairs(gc, e);
    }
    
    private static void queuePairs(GridCell gc, Entity e)
    {
        //in here, could run a check to stop duplicate pairs from being added by doing so:
        //each Entity has a unique ID number. Each Entity also has a linked list of Entity ID's that
        //it is currently "paired" with, ie, this method has ran and said that they are potential colliders.
        //before adding pair, do quick check through linked list of "calling" entity, to see if it is already
        //paired with the ID from the Entity currently trying to be paired up. Could have ID's work like so:
        //ID is a static int, shared by all "types" of entities, each time an object of that type is created,
        //you increment the static int by 1. These ID's also have a modifier before them, using a 
        //"letter to number" type of key, where A=1,B=2,etc., so an object of type "Ball" could have
        //ID's such as : 0002001, 0002002, 0002003, etc. 
        
        for (int i = 0; i < gc.inhabitants.size(); i++)
        {
//            System.out.println(e.value + " into " + gc.inhabitants.get(i));
            pairs.add(new Entity[]{e, gc.inhabitants.get(i)});
        }
    }
    
    private static void queuePairsInOwnCell(GridCell gc, Entity e)
    {
        for (int i = 0; i < gc.inhabitants.size(); i++)
        {
            if (gc.inhabitants.get(i).equals(e)) continue;
            pairs.add(new Entity[]{e, gc.inhabitants.get(i)});
        }
    }
    
    private static void findPairs()
    {
        //Need to, ya know, first run the broad phase and find possible collisions, before sorting pairs...
        //ahem... yeah, that'd be a good place to start
        for (int i = 0; i < activeEntities.size(); i++)
        {
            Entity e = activeEntities.get(i);
            GridCell gc = e.getOccupiedCell();
            //I BELIVE I NEED TO CHANGE INHABITANTS TO AN ARRAYLIST, SO I CAN USE THE NESTED FOR LOOP,
            //WITH MAIN LOOP ITERATOR AS "I", AND NESTED ITERATOR AS "I+1", SO I CAN SKIP THE PAIRS WHICH
            //HAVE ALREADY BEEN PAIRED UP!
            Iterator<Entity> itr = gc.inhabitants.iterator();
            while(itr.hasNext())
            {
                Entity en = itr.next();
                //make sure entity is not pairing against itself
                if (en.equals(e)) continue;
                else
                {
                    //add pair to pair list
                    pairs.add(new Entity[]{e,en});
                    //quick test check
                    for (int pair = 0; pair < pairs.size(); pair++)
                    {
                        Entity[] entArr = pairs.get(pair);
                        System.out.println(entArr[0].value);
                        System.out.println(entArr[1].value);
                        System.out.println("end of pair!\n");
                    }
                }
                
                
            }

            //check if cell has more than just this object inside
//            if (gc.inhabitants.size() > 1)
//            {
//                Iterator<Entity> itr = gc.inhabitants.iterator();
//                while (itr.hasNext())
//                {
//                    Entity entity = itr.next();
//                    if (entity.equals(e)) continue;
//                    
//                    
//                }
//            }
            
            for (int cells = 0; cells < 9; cells++)
            {
                
            }
        }
        counter = 0;
    }
    
    private static void updateLists()
    {
        for (Entity e : level.getEntities())
        {
            activeEntities.add(e);
        }
        
        for (Entity e : level.getProjectiles())
        {
            activeProjectiles.add(e);
        }
    }
    
    private static void clearLists()
    {
        activeEntities.clear();
        activeProjectiles.clear();
        pairs.clear();
    }
    
    private static void testPrintLists()
    {
        for (int i = 0; i < pairs.size(); i++)
        {
            System.out.println(pairs.get(i)[0].value + ",   " + pairs.get(i)[0].getID());
            System.out.println(pairs.get(i)[1].value + ",   " + pairs.get(i)[1].getID());
            System.out.println("--------------------------");
        }
    }
    
}

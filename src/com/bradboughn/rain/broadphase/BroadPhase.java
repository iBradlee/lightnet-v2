
package com.bradboughn.rain.broadphase;

import com.bradboughn.rain.broadphase.implicitgrid.GridCell;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.util.debuggingtools.SpriteWithCoord;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
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
        if (gc.getInhabitants().size() <= 1) return;
        queuePairsInOwnCell(gc, e);
    }
    
    private static void checkCellForEntities(GridCell gc, Entity e)
    {
//        need to account for border cells without certain neighbors
        if (gc == null) return;
        if (gc.isEmpty() == true) return;
        
        queuePairs(gc, e);
    }
    
    private static void queuePairs(GridCell gc, Entity e)
    {
        for (int i = 0; i < gc.getInhabitants().size(); i++)
        {
            Entity entityToPair = gc.getInhabitants().get(i);
            if (e.checkCurrentIDPairsForID(entityToPair.getID())) continue;
            e.addIDToCurrentIDPairs(entityToPair.getID());
            entityToPair.addIDToCurrentIDPairs(e.getID());
            pairs.add(new Entity[]{e, gc.getInhabitants().get(i)});
//            if (e.value == "player")
            {
                addPairToTestRenderQueue(entityToPair);
            }
        }
    }
    
    private static void queuePairsInOwnCell(GridCell gc, Entity e)
    {
        for (int i = 0; i < gc.getInhabitants().size(); i++)
        {
            Entity entityToPair = gc.getInhabitants().get(i);
            if (entityToPair.equals(e)) continue;
            else if (e.checkCurrentIDPairsForID(entityToPair.getID())) continue;
            e.addIDToCurrentIDPairs(entityToPair.getID());
            entityToPair.addIDToCurrentIDPairs(e.getID());
            pairs.add(new Entity[]{e, entityToPair});
//            if (e.value == "player")
            {
                addPairToTestRenderQueue(entityToPair);
            }
        }
    }
    
    private static void addPairToTestRenderQueue(Entity e)
    {
        Camera.addSpriteToTestRenderQueue(new SpriteWithCoord(3, 3, 0xffff00c5,(int)e.getCenterX()-1, (int)e.getCenterY()-1));
    }
    
    private static void updateLists()
    {
        for (Entity e : level.getEntities())
        {
            //first clear current Entity's "currentIDPairs" list
            e.clearAllCurrentIDPairs();
            activeEntities.add(e);
            
        }
        
        for (Entity p : level.getProjectiles())
        {
            //first clear current Entity's "currentIDPairs" list
            p.clearAllCurrentIDPairs();
            activeProjectiles.add(p);
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

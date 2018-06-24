
package com.bradboughn.rain.entity;

import com.bradboughn.rain.broadphase.explicitgrid.CellCoord;
import com.bradboughn.rain.broadphase.explicitgrid.Grid;
import com.bradboughn.rain.broadphase.explicitgrid.GridCell;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Entity 
{
    protected int ID;
    public String value = "defualt";
    
    protected double x, y;
    protected double centerX, centerY;
    protected Level level;
    protected Random rand = new Random();
    protected AABB aabb;
    protected Sprite sprite;
    //Generic HalfWidth/Height set to 16, for ease of use
    protected int sprHalfWidth = -1, sprHalfHeight = -1;
    
    protected GridCell occupiedCell;
    protected List<Integer> currentPairs = new ArrayList();
         
    protected boolean removed = false;
    private boolean offScreen = false;
    
    public enum Type 
    {
        PLAYER(0), MOB(1), PROJECTILE(2), PARTICLE(3);
        
        private int value;
        
        private Type(int value)
        {
            this.value = value;
        }
    }
    

    protected Type type;
    
    protected void updateGridCells()
    {
        GridCell temp = Grid.getCellAt(centerX, centerY);
        if (temp != null)
        {
            System.out.println("temp = " + temp.getX() + ", " + temp.getY());
            occupiedCell = temp;
            temp.addToInhabitants(this);
            Grid.addToActiveCells(temp);
        }
        else 
        {
            System.out.println("not a cell here");
            //because boradphase runs AFTER level's update of all entities, i need to remove this from level 
            //entity list as well as add it into level offscreen list immediately
            int camx = Camera.getOffsetX();
            int camy = Camera.getOffsetY();
            level.removeFromEntities(this);
            level.addToOffScreen(this);
            setRemovedTrue();
            setOffScreenTrue();
        }
    }
    
    
    
    //pasted code from Dynamic starts here
    
    public void updateOffScreen()
    {
        //Just waiting to see if it's inside the broadphase grid again, to start updating normally
        if (centerX < Camera.getOffsetX() - Grid.bufferSize_Pixel || centerX > Camera.getOffsetX() + Camera.getWidth() + Grid.bufferSize_Pixel) return;
        if (centerY < Camera.getOffsetY() - Grid.bufferSize_Pixel || centerY > Camera.getOffsetY() + Camera.getHeight() + Grid.bufferSize_Pixel) return;
        level.addToEntities(this);
        level.removeFromOffScreen(this);
        setOffScreenFalse();
        setRemovedFalse();
    }
    
    //pasted code from Dynamic ends here
    
    /**
     * Getting Grid cell at position relative to the map coordinates; meaning this method takes the camera offsets 
     * into consideration. For example: using (100,200) in this method, given the camera offsets are
     * currently 100 and 200 respectively for x and y, this method will return the GRID CELL @ position (0,0).
     * The Grid is in a fixed position on top of the camera, where (0,0) is always
     * the top leftmost GRID CELL.
     * @param x absolute x position on the map
     * @param y absolute y position on the map
     * @return 
     */
    
    public void runBroadPhase()
    {
        
    }
    
    public void update() 
    {
        
    }
    
    public void render () 
    {
        
    }
    
    public boolean isRemoved() 
    {
        return removed;
    }
    
    public boolean isOffScreen()
    {
        return offScreen;
    }
    
    public void setRemovedTrue () 
    {
        //Remove from level's Entity list, which updates/renders/etc.
        removed = true;
    }
    
    public void setRemovedFalse()
    {
        removed = false;
    }
    
    public void setOffScreenTrue()
    {
        offScreen = true;
    }
    
    public void setOffScreenFalse()
    {
        offScreen = false;
    }
    
    public boolean compareIDs(Entity e)
    {
        return ID == e.ID;
    }
    
    public boolean checkCurrentPairsForID(int eID)
    {
        for (Integer id : currentPairs)
        {
            if (id == eID) return true;
        }
        return false;
    }
    
    public void init(Level level) 
    {
        this.level = level;
        
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    protected void updateXs(int xa)
    {
        x += xa;
        centerX = x + sprHalfWidth;
    }
    
    protected void updateYs(int ya)
    {
        y += ya;
        centerY = y + sprHalfWidth;
    }
    
    protected void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        sprHalfWidth = sprite.WIDTH>>1;
        sprHalfHeight = sprite.HEIGHT>>1;
    }
    
    protected void setCenter()
    {
        centerX = x + sprHalfWidth;
        centerY = y + sprHalfHeight;
    }

    public double getCenterX()
    {
        return centerX;
    }

    public double getCenterY()
    {
        return centerY;
    }

    public int getSprHalfWidth()
    {
        return sprHalfWidth;
    }

    public int getSprHalfHeight()
    {
        return sprHalfHeight;
    }
    
    public Level getLevel()
    {
        return level;
    }

    public AABB getAabb()
    {
        return aabb;
    }

    public Type getType()
    {
        return type;
    }
    
    public Sprite getSprite()
    {
        return sprite;
    }

    public int getID()
    {
        return ID;
    }

    public GridCell getOccupiedCell()
    {
        return occupiedCell;
    }
    
    public List<Integer> getCurrentPairs()
    {
        return currentPairs;
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Entity)) return false;
        
        Entity e = (Entity) o;
        
        return e.getID() == ID;
    }
}

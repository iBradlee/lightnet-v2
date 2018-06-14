
package com.bradboughn.rain.entity;

import com.bradboughn.rain.broadphase.explicitgrid.CellCoord;
import com.bradboughn.rain.broadphase.explicitgrid.Grid;
import com.bradboughn.rain.broadphase.explicitgrid.GridCell;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.collision.AABB;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.Level;
import com.bradboughn.rain.util.DoublyLinkedList;
import java.util.Random;

public abstract class Entity 
{
    protected int ID;
    protected static int IDcounter = 0;
    
    protected int x, y;
    protected int centerX, centerY;
    private boolean removed = false;
    private boolean offScreen = false;
    protected Level level;
    protected Random rand = new Random();
    protected AABB aabb;
    private Sprite sprite;
    //Generic HalfWidth/Height set to 16, for ease of use
    protected int sprHalfWidth = -1, sprHalfHeight = -1;
    
    protected DoublyLinkedList<GridCell> occupiedCells = new DoublyLinkedList(); 
    
            
    
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
        
        GridCell temp = getCellAtAbsolute(x, y);
        if (temp != null)
        {
            occupiedCells.add(temp);
            temp.addToInhabitants(this);
            Grid.addToActiveCells(temp);

        }
    }
    
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
    public GridCell getCellAtAbsolute(int x, int y)
    {
        int xa = x - Camera.getOffsetX();
        int ya = y - Camera.getOffsetY();
        if (xa < -Grid.getBufferSizePixel() || xa > Grid.gridWidth + Grid.bufferSize_Pixel || 
            ya < -Grid.bufferSize_Pixel || ya > Grid.gridHeight + Grid.bufferSize_Pixel) 
        {
            System.out.println("Outside of explicit grid! Will be added to off-screen entity list!");
            //need to build in better functionality for handling off-screen entities. this crashes atm,
            //but I like the concept of a seperate custom class used as a container for these objects,
            //to be added back into update/rendering loops when/if they come back onscreen again!
            setOffScreenTrue();
            setRemovedTrue();
            level.addToOffScreen(this);
            return null;
        }
        return Grid.cellMap.get(new CellCoord(xa/Grid.cellSize,ya/Grid.cellSize));
    }
    
    public void runBroadPhase()
    {
        
    }
    
    public void update() 
    {
        
    }
    
    public void updateOffScreen()
    {
        System.out.println("off screen update running");
        //Just waiting to see if it's inside the broadphase grid again, to start updating normally
        if (x < Camera.getOffsetX() - Grid.bufferSize_Pixel || x > Camera.getOffsetX() + Camera.getWidth() + Grid.bufferSize_Pixel) return;
        if (y < Camera.getOffsetY() - Grid.bufferSize_Pixel || y > Camera.getOffsetY() + Camera.getHeight() + Grid.bufferSize_Pixel) return;
        System.out.println("ENTITY RETURNING TO REGULAR ENTITY LIST!");
        setOffScreenFalse();
        setRemovedFalse();
        level.add(this);
    }
    
    public void render () 
    {
        
    }
    
    public void setRemovedTrue () 
    {
        //Remove from level
        removed = true;
    }
    
    public void setRemovedFalse()
    {
        removed = false;
    }
    
    public boolean isRemoved() 
    {
        return removed;
    }
    
    public void setOffScreenTrue()
    {
        offScreen = true;
    }
    
    public void setOffScreenFalse()
    {
        offScreen = false;
    }
    
    public boolean isOffScreen()
    {
        return offScreen;
    }
    
    public void init(Level level) 
    {
        this.level = level;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
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

    public int getCenterX()
    {
        return centerX;
    }

    public int getCenterY()
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
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Entity)) return false;
        
        Entity e = (Entity) o;
        
        return e.getID() == ID;
    }
}

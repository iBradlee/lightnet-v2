
package com.bradboughn.rain.broadphase.implicitgrid;

import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.util.DoublyLinkedList;
import java.util.ArrayList;
import java.util.List;

public class GridCell 
{
    private String value = "default"; 
    private CellCoord cellCoord;
    //may want to change back to a DoublyLinkedList, bc it will be added to so often? idk
    private List<Entity> inhabitants = new ArrayList();
    
    //storage for neighboring cells
    private GridCell nTop;
    private GridCell nBot;
    private GridCell nLeft;
    private GridCell nRight;
    //n+ doesn't say to store these, but these are diagonals
    private GridCell nTopLeft;
    private GridCell nTopRight;
    private GridCell nBotLeft;
    private GridCell nBotRight;
    
    
    public GridCell(CellCoord cellCoord)
    {
        this.cellCoord = cellCoord;
    }
    
    public GridCell(int x, int y)
    {
        this.cellCoord = new CellCoord(x,y);
    }
    
    public boolean isEmpty()
    {
        return inhabitants.isEmpty();
    }
    
    public List<Entity> getInhabitants()
    {
        return inhabitants;
    }
    
    public void addToInhabitants(Entity e)
    {
        inhabitants.add(e);
    }
    
    public void clearInhabitants()
    {
        inhabitants.clear();
    }
    
    public CellCoord getCellCoord()
    {
        return cellCoord;
    }
    
    public int getX()
    {
        return cellCoord.getX();
    }
    
    public void setX(int x)
    {
        cellCoord.setX(x);
    }
    
    public int getY()
    {
        return cellCoord.getY();
    }
    
    public void setY(int y)
    {
        cellCoord.setY(y);
    }
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String val)
    {
        value = val;
    }

    public GridCell getnTop()
    {
        return nTop;
    }

    public void setnTop(GridCell nTop)
    {
        this.nTop = nTop;
    }

    public GridCell getnBot()
    {
        return nBot;
    }

    public void setnBot(GridCell nBot)
    {
        this.nBot = nBot;
    }

    public GridCell getnLeft()
    {
        return nLeft;
    }

    public void setnLeft(GridCell nLeft)
    {
        this.nLeft = nLeft;
    }

    public GridCell getnRight()
    {
        return nRight;
    }

    public void setnRight(GridCell nRight)
    {
        this.nRight = nRight;
    }

    public GridCell getnTopLeft()
    {
        return nTopLeft;
    }

    public void setnTopLeft(GridCell nTopLeft)
    {
        this.nTopLeft = nTopLeft;
    }

    public GridCell getnTopRight()
    {
        return nTopRight;
    }

    public void setnTopRight(GridCell nTopRight)
    {
        this.nTopRight = nTopRight;
    }

    public GridCell getnBotLeft()
    {
        return nBotLeft;
    }

    public void setnBotLeft(GridCell nBotLeft)
    {
        this.nBotLeft = nBotLeft;
    }

    public GridCell getnBotRight()
    {
        return nBotRight;
    }

    public void setnBotRight(GridCell nBotRight)
    {
        this.nBotRight = nBotRight;
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof GridCell)) return false;
        GridCell gc = (GridCell) o;
        
        return gc.getX() == getX() && gc.getY() == getY();
    }
    
}

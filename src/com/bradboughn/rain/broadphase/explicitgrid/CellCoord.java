
package com.bradboughn.rain.broadphase.explicitgrid;

import com.bradboughn.rain.entity.Entity;
import java.util.ArrayList;

public class CellCoord
{
    private int x, y;
    private Entity entity;
    
    public CellCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    //need to override, so HashMap knows how to check if two objects are equal, so it can search for
    //the key (cell coord) that I'm looking up, or checking to see if it exists in the map
    public boolean equals(Object o)
    {
        //quick basic checks. If it's the same instance, return true. If it's not the same class, or null, false
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        //Casting argument object to CellCoord to do actual checks
        CellCoord cc = (CellCoord) o;
        
        if (x != cc.x) return false;
        if (y != cc.y) return false;
        return true;
    }
    
    //needed custom hashCode() for this class.
    public int hashCode()
    {
        int result = 17;
        result = 37 * result + x;
        result = 37 * result + y;
        return result;
    }
    
    public void updateX()
    {
        //UPDATE LOGIC HERE;
    }
    
    public void updateY()
    {
        //UPDATE LOGIC HERE;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

}


package com.bradboughn.rain.entity;

import com.bradboughn.rain.broadphase.implicitgrid.CellCoord;
import com.bradboughn.rain.broadphase.implicitgrid.Grid;
import com.bradboughn.rain.broadphase.implicitgrid.GridCell;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.level.Level;

public abstract class DynamicEntity extends Entity
{
    {
        ID = dynamicIDcounter++;
    }
    
    public DynamicEntity(double x, double y)
    {
        this.x = x;
        this.y = y;
        setCenter();
    }
    
    private static int dynamicIDcounter = 0;
    
    public GridCell getCellAtAbsolute(int x, int y)
    {
        int xa = x - Camera.getOffsetX();
        int ya = y - Camera.getOffsetY();
        if (xa < -Grid.getBufferSizePixel() || xa > Grid.getWidth() + Grid.getBufferSize_Pixel() || 
            ya < -Grid.getBufferSize_Pixel() || ya > Grid.getHeight() + Grid.getBufferSize_Pixel()) 
        {
            setOffScreenTrue();
            return null;
        }
        return Grid.cellMap.get(new CellCoord(xa/Grid.getCellSize(),ya/Grid.getCellSize()));
    }
    
    public void updateOffScreen()
    {
        //Just waiting to see if it's inside the broadphase grid again, to start updating normally
        if (centerX <= Camera.getOffsetX() - Grid.getBufferSize_Pixel() +1 || centerX >= Camera.getOffsetX() 
                + Camera.getWidth() + Grid.getBufferSize_Pixel() ) return;
        if (centerY <= Camera.getOffsetY() - Grid.getBufferSize_Pixel() || centerY >= Camera.getOffsetY() 
                + Camera.getHeight() + Grid.getBufferSize_Pixel() ) return;
        level.addToEntities(this);
        level.removeFromOffScreen(this);
        setOffScreenFalse();
        setRemovedFalse();
    }
}

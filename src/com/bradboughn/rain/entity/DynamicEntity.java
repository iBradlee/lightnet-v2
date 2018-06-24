
package com.bradboughn.rain.entity;

import com.bradboughn.rain.broadphase.explicitgrid.CellCoord;
import com.bradboughn.rain.broadphase.explicitgrid.Grid;
import com.bradboughn.rain.broadphase.explicitgrid.GridCell;
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
        if (xa < -Grid.getBufferSizePixel() || xa > Grid.gridWidth + Grid.bufferSize_Pixel || 
            ya < -Grid.bufferSize_Pixel || ya > Grid.gridHeight + Grid.bufferSize_Pixel) 
        {
            setOffScreenTrue();
            return null;
        }
        return Grid.cellMap.get(new CellCoord(xa/Grid.cellSize,ya/Grid.cellSize));
    }
    
    public void updateOffScreen()
    {
        //Just waiting to see if it's inside the broadphase grid again, to start updating normally
        if (centerX <= Camera.getOffsetX() - Grid.bufferSize_Pixel +1 || centerX >= Camera.getOffsetX() + Camera.getWidth() + Grid.bufferSize_Pixel ) return;
        if (centerY <= Camera.getOffsetY() - Grid.bufferSize_Pixel || centerY >= Camera.getOffsetY() + Camera.getHeight() + Grid.bufferSize_Pixel ) return;
        level.addToEntities(this);
        level.removeFromOffScreen(this);
        setOffScreenFalse();
        setRemovedFalse();
    }
}

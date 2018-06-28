
package com.bradboughn.rain.util.debuggingtools;

import com.bradboughn.rain.Game;
import com.bradboughn.rain.broadphase.implicitgrid.Grid;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.graphics.Sprite;

/**
 * This is simply a class that provides the ability to render the implicit grid, used in broad phase,
 * for testing purposes, and such.
 * @author Brad
 */

public class VisualImplicitGrid 
{
    private static int gridWidth = Grid.getWidth();
    private static int gridHeight = Grid.getHeight();
    private static int cellSize = Grid.getCellSize();
    private static int bufferSize_Cell = Grid.getBufferSize_Cell();
    private static int bufferSize_Pixel = Grid.getBufferSizePixel();
    private static int numVisibleRows = Grid.getNumVisibleRows();
    private static int numVisibleColumns = Grid.getNumVisibleColumns();
    private static int gridFullWidth = gridWidth + Grid.getBufferSizePixel() << 1;
    private static int gridFullHeight = gridHeight + Grid.getBufferSizePixel() << 1;
    
    private static void update()
    {
        
    }
    
    public static void render()
    {
        int xa = -Grid.getBufferSize_Cell();
        for (int y = -Grid.getBufferSize_Cell(); y < Grid.getNumVisibleRows() + Grid.getBufferSize_Cell(); y++)
        {
            Camera.renderSprite(xa * cellSize, y * cellSize, new Sprite(gridFullWidth, 1, 0xff00fffc), false);
            for (int x = -Grid.getBufferSize_Cell(); x < Grid.getNumVisibleColumns() + Grid.getBufferSize_Cell(); x++)
            {
                xa = x;
                Camera.renderSprite(xa * cellSize, y * cellSize, new Sprite(1, gridFullHeight, 0xff00fffc), false);
            }
            xa = -Grid.getBufferSize_Cell();
        }
    }
}

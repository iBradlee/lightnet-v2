
package com.bradboughn.rain.broadphase.explicitgrid;

import com.bradboughn.rain.Game;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.entity.DynamicEntity;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.level.Level;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Grid 
{
    
    public static int gridWidth = Game.getWidth();
    public static int gridHeight = Game.getHeight();
    public static int cellSize = 32;
    public static int bufferSize_Cell = 5;
    public static int bufferSize_Pixel = bufferSize_Cell * cellSize;
    public static int numVisibleRows = gridHeight/cellSize;
    public static int numVisibleColumns = gridWidth/cellSize;
    public static Level level;

    //? Grid only needs to create/store in set/list, the cells that are currently being occupied, and disposing
    //of entire set/list after each tick. Entity could move
    
    //Grid can store cells in list/set. Cells can store all entities located within. Entities can store all current cells.
    //Grid should handle the assigning of entities to a specific cell; Needs method to find specific cell, and return that cell
    //Cells need to have their location, and create a list of entities if they exist inside (clearing the list every tick)
    //Cells need method to check if more than one entity resides inside (Grid would call this, i think? to check if narrow phase needs to be ran)
    //Entity needs to use grid to look up what cell they're in, and store that info in a list
    public static Map<CellCoord, GridCell> cellMap = new LinkedHashMap();
    private static List<GridCell> activeCells = new ArrayList();
    
    //THIS CELL IS FOR COORDINATES POINTING TO PLACES OFF-SCREEN FOR NOW. Could keep entities off-screen
    //in a cell such as this, or a new class that holds info of entities off-screen (thus not updated/rendered)
    private static GridCell GRIDCELL_VOID = new GridCell(new CellCoord(-1,-1));

    
    /*
     * An explicit grid, to be used for broadphase collision detection. Grid is built with GridCells 
     * of an arbitrary number, and of an arbitrary size. Keep in mind, the screenWidth and screenHeight
     * parameters only make up the actual ENTIRE size of the grid, if bufferSize is 0. BufferSize refers
     * to the size of the "expanded grid", ie. a portion of the grid which is off screen, and completely
     * surrounding the grid. BufferSize of 10, will add 10 cells to the top/bottom/left/right sides of 
     * the screen. This is done to keep the "main grid", ie. the visible portion, in a more readable
     * format, where GridCell at (0,0) will be the top left GridCell.
     * @param screenWidth
     * @param screenHeight
     * @param bufferSize
     * @param cellSize 
     */
//    public Grid(int screenWidth, int screenHeight, int bufferSize, int cellSize)
//    {
//        GRIDCELL_VOID.setValue("VOID");
//        Grid.gridWidth = screenWidth;
//        Grid.gridHeight = screenHeight;
//        Grid.bufferSize_Cell = bufferSize;
//        Grid.cellSize = cellSize;
//        numVisibleColumns = screenWidth/cellSize;
//        numVisibleRows = screenHeight/cellSize;
//        buildGrid();
//    }
    
    public static void initGrid(Level level)
    {
        Grid.level = level;
        buildGrid();   
    }
    
    public static void initGridWithNewSize(int gridWidth, int gridHeight, int bufferSize, int cellSize)
    {
        Grid.gridWidth = gridWidth;
        Grid.gridHeight = gridHeight;
        Grid.cellSize = cellSize;
        Grid.bufferSize_Cell = bufferSize;
        Grid.bufferSize_Pixel = bufferSize * cellSize;
        numVisibleColumns = gridWidth/cellSize;
        numVisibleRows = gridHeight/cellSize;
        buildGrid();   
    }
    
    private static void buildGrid()
    {
        for (int y = -bufferSize_Cell; y < numVisibleRows + bufferSize_Cell; y++)
        {
            for (int x = -bufferSize_Cell; x < numVisibleColumns + bufferSize_Cell; x++)
            {
                CellCoord cc = new CellCoord(x,y);
                GridCell gc = new GridCell(cc);
               
                cellMap.put(cc, gc);
            }
        }
        initGridCellNeighbors();
    }
    
    public static void update()
    {
//        testPrintCellInhabitants();

    }
    
    public static void clear()
    {
        clearActiveCellsOfInhabitants();
        clearActiveCells();
    }
    
    public static void addToActiveCells(GridCell gc)
    {
        boolean isNotInList = true;
        Iterator<GridCell> itr = activeCells.iterator();
        while (itr.hasNext())
        {
            if (itr.next().equals(gc))
            {
                isNotInList = false;
                return;
            }
        }
        if (isNotInList)
        {
            activeCells.add(gc);
        }
    }
    
    private static void clearActiveCellsOfInhabitants()
    {
        Iterator<GridCell> itr = activeCells.iterator();
        
        while(itr.hasNext())
        {
            itr.next().clearInhabitants();
        }
    }
    
    private static void clearActiveCells()
    {
        activeCells.clear();
    }
    
    public static void testPrintCellInhabitants()
    {
        
        System.out.println("----------------------------------------------\n" +
                "Active Cells : " + activeCells.size() +
               "\n----------------------------------------------");
        for (int y = -bufferSize_Cell; y < numVisibleRows + bufferSize_Cell; y++)
        {
            for (int x = -bufferSize_Cell; x < numVisibleColumns + bufferSize_Cell; x++)
            {
                CellCoord cc = new CellCoord(x,y);
                if (cellMap.get(cc).inhabitants.isEmpty()) continue;
                else
                {
                    System.out.println("Cell @ x" + cc.getX() + ", y" + cc.getY());
                    Iterator<Entity> itr = cellMap.get(cc).inhabitants.iterator();
                    while (itr.hasNext())
                    {
                        System.out.println(itr.next().getType().toString());
                    }
                }
            }
        }
    }
    
    private static void initGridCellNeighbors()
    {
        //Can't do this one at a time. Need to wait til build is done, then use cellMap to find actual
        //instances of the cells, and store those instances in the appropriate vars
        for (int y = -bufferSize_Cell; y < numVisibleRows + bufferSize_Cell; y++)
        {
            for (int x = -bufferSize_Cell; x < numVisibleColumns + bufferSize_Cell; x++)
            {
                //Need to account for border Cells, that do not have any neighbors on specific sides
                String location = "isNone";
                
                if (y <= -bufferSize_Cell)
                {
                    if (x <= -bufferSize_Cell) location = "isTopLeft";
                    else if (x >= numVisibleColumns + bufferSize_Cell) location = "isTopRight";
                    else location = "isTop";
                }
                else if (y >= numVisibleRows + bufferSize_Cell) 
                {
                    if (x <= -bufferSize_Cell) location = "isBotLeft";
                    else if (x >= numVisibleColumns + bufferSize_Cell) location = "isBotRight";
                    else location = "isBot";
                }
                else if (x <= -bufferSize_Cell) location = "isLeft";
                else if (x >= numVisibleColumns + bufferSize_Cell) location = "isRight";
                
                //current Cell which is being assigned neighbors
                GridCell current = cellMap.get(new CellCoord(x,y));
                //neighboring Cells assigned DICTACTED BY STATUS CHECKS above, AT indicies from enum
                switch(location)
                {
                    case "isNone":
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopLeft(cellMap.get(new CellCoord(x-1, y-1)));
                        current.setnTopRight(cellMap.get(new CellCoord(x+1, y-1)));
                        current.setnBotLeft(cellMap.get(new CellCoord(x-1, y+1)));
                        current.setnBotRight(cellMap.get(new CellCoord(x+1, y+1)));
                        break;
                    case "isTop":
                        current.setnTop(null);
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopLeft(null);
                        current.setnTopRight(null);
                        current.setnBotLeft(cellMap.get(new CellCoord(x-1, y+1)));
                        current.setnBotRight(cellMap.get(new CellCoord(x+1, y+1)));
                        break;
                    case "isBot":
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(null);
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopLeft(cellMap.get(new CellCoord(x-1, y-1)));
                        current.setnTopRight(cellMap.get(new CellCoord(x+1, y-1)));
                        current.setnBotLeft(null);
                        current.setnBotRight(null);
                        break;
                    case "isLeft":
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(null);
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopRight(cellMap.get(new CellCoord(x+1, y-1)));
                        current.setnTopLeft(null);
                        current.setnBotRight(cellMap.get(new CellCoord(x+1, y+1)));
                        current.setnBotLeft(null);
                        break;
                    case "isRight":
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnRight(null);
                        current.setnTopLeft(cellMap.get(new CellCoord(x-1, y-1)));
                        current.setnTopRight(null);
                        current.setnBotLeft(cellMap.get(new CellCoord(x-1, y+1)));
                        current.setnBotRight(null);
                        break;
                    case "isTopLeft":
                        current.setnTop(null);
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(null);
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopLeft(null);
                        current.setnTopRight(null);
                        current.setnBotLeft(null);
                        current.setnBotRight(cellMap.get(new CellCoord(x+1, y+1)));
                        break;
                    case "isTopRight":
                        current.setnTop(null);
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnTopLeft(null);
                        current.setnTopRight(null);
                        current.setnBotLeft(cellMap.get(new CellCoord(x-1, y+1)));
                        current.setnBotRight(null);
                        break;
                    case "isBotLeft":
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(null);
                        current.setnLeft(null);
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopLeft(null);
                        current.setnTopRight(cellMap.get(new CellCoord(x+1, y-1)));
                        current.setnBotLeft(null);
                        current.setnBotRight(null);
                        break;
                    case "isBotRight":
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(null);
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnRight(null);
                        current.setnTopLeft(cellMap.get(new CellCoord(x-1, y-1)));
                        current.setnTopRight(null);
                        current.setnBotLeft(null);
                        current.setnBotRight(null);
                        break;
                    default:
                        current.setnTop(cellMap.get(new CellCoord(x, y-1)));
                        current.setnBot(cellMap.get(new CellCoord(x, y+1)));
                        current.setnLeft(cellMap.get(new CellCoord(x-1, y)));
                        current.setnRight(cellMap.get(new CellCoord(x+1, y)));
                        current.setnTopLeft(cellMap.get(new CellCoord(x-1, y-1)));
                        current.setnTopRight(cellMap.get(new CellCoord(x+1, y-1)));
                        current.setnBotLeft(cellMap.get(new CellCoord(x-1, y+1)));
                        current.setnBotRight(cellMap.get(new CellCoord(x+1, y+1)));
                }
            }
        }
    }
    
    public static boolean isInGridBounds(Entity e)
    {
        int xa = (int)e.getCenterX() - Camera.getOffsetX();
        int ya = (int)e.getCenterY() - Camera.getOffsetY();
        if (xa < -Grid.getBufferSizePixel() || xa > Grid.gridWidth + Grid.bufferSize_Pixel || 
            ya < -Grid.bufferSize_Pixel || ya > Grid.gridHeight + Grid.bufferSize_Pixel) 
        {
            return false;
        }
        else return true;
    }
    
    
    public static GridCell getCellAt(double x, double y)
    {
        int xa = (int)x - Camera.getOffsetX();
        int ya = (int)y - Camera.getOffsetY();
        return cellMap.get(new CellCoord(xa/cellSize,ya/cellSize));
    }
    
        public static int getGridWidth()
    {
        return gridWidth;
    }

    public static int getGridHeight()
    {
        return gridHeight;
    }

    public static int getCellSize()
    {
        return cellSize;
    }

    public static int getBufferSizePixel()
    {
        return bufferSize_Pixel;
    }
}

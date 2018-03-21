
package com.bradboughn.rain.level;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.level.tile.Tile;

/**
 *
 * @author Brad
 */
public class Level {
    
    protected int width, height;
    protected int tilesInt[];
    protected int[] tiles;
    
    public static Level spawn = new SpawnLevel ("/textures/levels/spawn_level.png");
    
    public Level(int width, int height){
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
        
    }
    
    public Level(String path){
        loadLevel(path);
        generateLevel();
    }
    
    protected void generateLevel(){
        
    }
    
    protected void loadLevel(String path){
        
    }
    
    public void update(){
        
    }
    
    private void time() {
        
    }
    //Render method is finding all 4 sides, and the current position on the map, of the screen, then renders each tile individually
    public void render(int xScroll,int yScroll, Screen screen){      //Method takes xScroll, yScroll, and tells where to render
        screen.setOffset(xScroll, yScroll);                          //setting/updating offset for player movement
        int x0 = xScroll >> 4; // left side                          >>4, is same as divided by 16. This has it check/render every tile, instead of pixels.
        int x1 = (xScroll + screen.width + 16) >> 4; // right side        >>4 puts numbers in tile precision
        int y0 = yScroll >> 4;//top side            
        int y1 = (yScroll + screen.height + 16) >> 4; // bottom side || +16 adds another tile to fully cover screen.
        
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
               
                Tile.voidTile.render(x, y, screen);
                   
                
                getTile(x,y).render(x, y, screen);
                // x and y grab every tile on screen currently, by taking the x0, y0 variable
            }    
        }                   
            
    }
    
    //Grass = 0x00FF00    
    //Flower = 0xFFFF00
    //Rock = 0x7F7F00
    
    //SPAWNS
    //Stone wall = 2E3D46
    //Bronze/Stone = C58F2D
    //Wood floor = 4D2201
    //Dark grass = 2C5E00
    //Spawn point = 00FFFF
    
    public Tile getTile (int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return Tile.spawn_water;
        if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass;
        if (tiles[x + y * width] == Tile.col_grass_flower) return Tile.grassFlower;
        if (tiles[x + y * width] == 0xff7f7f00) return Tile.grassRock;
        if (tiles[x + y * width] == Tile.col_spawn_stoneWall) return Tile.spawn_stoneWall; 
        if (tiles[x + y * width] == Tile.col_spawn_StoneWall2) return Tile.spawn_stoneWall2; 
        if (tiles[x + y * width] == Tile.col_spawn_woodFloor) return Tile.spawn_woodFloor;
        if (tiles[x + y * width] == Tile.col_spawn_hedge) return Tile.spawn_hedge;
        if (tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water;
        return Tile.voidTile;
    }
}

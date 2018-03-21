/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.level.tile;

import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.graphics.Sprite;
import com.bradboughn.rain.level.tile.spawn_level.SpawnGrassTile;
import com.bradboughn.rain.level.tile.spawn_level.SpawnHedgeTile;
import com.bradboughn.rain.level.tile.spawn_level.SpawnStoneWallTile;
import com.bradboughn.rain.level.tile.spawn_level.SpawnWaterTile;
import com.bradboughn.rain.level.tile.spawn_level.SpawnWoodFloorTile;

/**
 *
 * @author Brad
 */
public class Tile 
{
    
    public int x, y;
    public Sprite sprite;
    
    //Basic FIRST Tiles
    public static Tile grass = new GrassTile();
    public static Tile grassFlower = new FlowerTile();
    public static Tile grassRock = new RockTile();
    public static Tile water = new WaterTile();
    public static Tile voidTile = new VoidTile();
    
    //Spawn Level Tiles:
    public static Tile spawn_grassBasic = new GrassTile();
    public static Tile spawn_grass = new SpawnGrassTile();
    public static Tile spawn_hedge = new SpawnHedgeTile();
    public static Tile spawn_stoneWall = new SpawnStoneWallTile();
    public static Tile spawn_stoneWall2 = new SpawnStoneWallTile(Sprite.spawn_stoneWall2);
    public static Tile spawn_woodFloor = new SpawnWoodFloorTile();
    public static Tile spawn_water = new SpawnWaterTile();
    
    public static final int col_grass_flower = 0xfffe0000;
    
    
    public static final int col_spawn_grass = 0xff00ff00;
    public static final int col_spawn_hedge = 0xff2C5E00;
    public static final int col_spawn_stoneWall = 0xff2E3D46;
    public static final int col_spawn_StoneWall2 = 0xffC58F2D;
    public static final int col_spawn_woodFloor = 0xff4D2201;
    public static final int col_spawn_water = 0xff0026ff;
    public static final int col_spawn_player = 0xff00FFFF;
    

    
    public Tile(Sprite sprite)
    {
        this.sprite = sprite;
    }
    
    public Tile()
    {
        
    }
    
    public void render(int x, int y, Screen screen) 
    {
        screen.renderTile(x << 4 ,y << 4 , this);
    }
    
    public boolean solid() 
    {
        return false;
    }
    
    public boolean breakable() 
    {
        return false;
    }
}

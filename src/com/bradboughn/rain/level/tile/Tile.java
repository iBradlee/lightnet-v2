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
public class Tile {
    
    public int x, y;
    public Sprite sprite;
    
    //Basic FIRST Tiles
    public static Tile grass = new GrassTile(Sprite.grass);
    public static Tile grassFlower = new FlowerTile(Sprite.grassFlower);
    public static Tile grassRock = new RockTile(Sprite.grassRock);
    public static Tile water = new WaterTile(Sprite.water);
        public static Tile voidTile = new VoidTile(Sprite.voidSprite);
    
    //Spawn Level Tiles:
    public static Tile spawn_grassBasic = new GrassTile(Sprite.grass);
    public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
    public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
    public static Tile spawn_stoneWall = new SpawnStoneWallTile(Sprite.spawn_stoneWall);
    public static Tile spawn_stoneWall2 = new SpawnStoneWallTile(Sprite.spawn_stoneWall2);
    public static Tile spawn_woodFloor = new SpawnWoodFloorTile(Sprite.spawn_woodFloor);
    public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
    
    public static final int col_grass_flower = 0xfffe0000;
    
    
    public static final int col_spawn_grass = 0xff00ff00;
    public static final int col_spawn_hedge = 0xff2C5E00;
    public static final int col_spawn_stoneWall = 0xff2E3D46;
    public static final int col_spawn_StoneWall2 = 0xffC58F2D;
    public static final int col_spawn_woodFloor = 0xff4D2201;
    public static final int col_spawn_water = 0xff0026ff;
    public static final int col_spawn_player = 0xff00FFFF;
    

    
    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4 ,y << 4 , this);
    }
    
    public boolean solid() {
        return false;
    }
    
    public boolean breakable() {
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.graphics;

/**
 *
 * @author Brad
 */
public class Sprite {
    
    public final int SIZE;
    private int x, y;
    public int[] pixels;
    private SpriteSheet sheet;
    
    public static Sprite grass = new Sprite(16,0, 0, SpriteSheet.tiles);
    public static Sprite grassFlower = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite grassRock = new Sprite(16, 0, 2, SpriteSheet.tiles);
    
    public static Sprite water = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite voidSprite = new Sprite(16, 0x1B87E0);
    
    //Spawn Level Sprites Here:
    public static Sprite spawn_grassBasic = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_grass = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_hedge = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
    public static Sprite spawn_stoneWall = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_stoneWall2 = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_woodFloor = new Sprite(16, 1, 2, SpriteSheet.spawn_level);
    public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
    
    //Player Sprites Here:
    public static Sprite playerU = new Sprite(32, 0, 5, SpriteSheet.tiles);
    public static Sprite playerD = new Sprite(32, 2, 5, SpriteSheet.tiles);
    public static Sprite playerL = new Sprite(32, 3, 5, SpriteSheet.tiles);
    public static Sprite playerR = new Sprite(32, 1, 5, SpriteSheet.tiles);
    
    public static Sprite playerU1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
    public static Sprite playerD1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
    public static Sprite playerL1 = new Sprite(32, 3, 6, SpriteSheet.tiles);
    public static Sprite playerR1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
    
    public static Sprite playerU2 = new Sprite(32, 0, 7, SpriteSheet.tiles );
    public static Sprite playerD2 = new Sprite(32, 2, 7, SpriteSheet.tiles );
    public static Sprite playerL2 = new Sprite(32, 3, 7, SpriteSheet.tiles );
    public static Sprite playerR2 = new Sprite(32, 1, 7, SpriteSheet.tiles );
    
    
    
    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
    }
    
    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }
    
    public void setColor(int color) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            pixels[i] = color;
        }
    }
    
    public void load(){
        for (int y = 0; y < SIZE; y++){
            for (int x = 0; x < SIZE; x++){
                pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
            }
        }
    }
}

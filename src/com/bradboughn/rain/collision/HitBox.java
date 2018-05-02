
package com.bradboughn.rain.collision;

public class HitBox 
{

    //width, height, offsetX, offsetY | offset's are empty space in Sprite, and help form the box
    //that, as closely as possible, fits your Entitiy, or GameObject
    protected int[] hitbox;
    
    public HitBox(int width, int height, int offsetX, int offsetY )
    {
        int[] k = {width, height, offsetX, offsetY};
        hitbox = k;
    }
    
    public int[] getHitbox()
    {
        return hitbox;
    }

    public void setHitbox(int width, int height, int offsetX, int offsetY)
    {
        int[] k = {width, height, offsetX, offsetY};
        hitbox = k;
    }
    
}


package com.bradboughn.rain.util.debuggingtools;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.graphics.Sprite;

/**
 * This very basic class is used for niche cases in which I'd like to have a Sprite which can store
 * it's own coordinates, as well as all the basic functionality of the Sprite class. That's it. Really.
 * I realize while writing this, that I already designed my "renderSprite" method, inside my Camera
 * class to do basically this same thing. Although instead of doing that, I added a List in camera,
 * which holds sprites. That list, at the end of Camera's render method, renders the sprites it holds,
 * which is where the need for coordinates attached comes in. This is kinda all pointless. Good job, me.
 * 
 * @author Brad
 */

public class SpriteWithCoord extends Sprite
{

    private int x, y;
    
    public SpriteWithCoord(int width, int height, int color, int xCoord, int yCoord)
    {
        super(width, height, color);
        x = xCoord;
        y = yCoord;
    }
    
    public void render()
    {
        Camera.renderSprite(x, y, this, true);
    }

}

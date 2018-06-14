
package com.bradboughn.rain.collision;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Sprite;

public class AABB 
{
    private Entity go;
    
    //CenterPos[0] is x center point, and centerPos[1] is the y center point;
    private int centerX, centerY;
    //halfWidth[0] and [1] are halfwidth, and halfheight, respectively, for AABB
    private int halfwidth, halfheight;
    private int x,y;
    
    //Left & right sides, on x axis. Top & bottom sides, for y axis
    private int leftX, rightX, topY, bottomY;
    //Left, right sides (x axis of each); Top, bottom sides (y axis of each)
    
    
    public Entity.Type type;
    
    //@todo
    //HOW TO FIX : PROJECTILE AABB'S, AND PROBABLY PARTICLES TOO. They both keep track of movement (x,y/xa,ya)
    //with doubles. however, when making a bounding box, they use integers to create their boxes. 
    public AABB(int x, int y, int width, int height, Entity go)
    {
        this.go = go;
        halfwidth = width >> 1;
        halfheight = height >> 1;
        
        //actual center of the go's sprite, regardless of aabb's width/height
        centerX = x + go.getSprite().WIDTH >> 1;
        centerY  = y + go.getSprite().HEIGHT >> 1;
        
        leftX = centerX - halfwidth;
        rightX = centerX + halfwidth;
        topY = centerY - halfheight;
        bottomY = centerY + halfheight;
        
        this.x = leftX;
        this.y = topY;

        type = go.getType();
    }
    
    //second constructor for constructiong niche AABB's that aren't attached to an object
    public AABB(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        halfwidth = width>>1;
        halfheight = height>>1;
        
        centerX = x + halfwidth;
        centerY = y + halfheight;
        
        leftX = centerX - halfwidth;
        rightX = centerX + halfwidth;
        topY = centerY - halfheight;
        bottomY = centerY + halfheight;
    }
    
    public void setAABBpos()
    {
        setCenter( go.getCenterX(), go.getCenterY());
        setSides();
    }
    
    //second setAABB method for niche AABB's that aren't attached to an object
    public void setAABBpos(int x, int y)
    {
        setCenter(x + halfwidth, y + halfheight);
        setSides();
    }
    
    public static boolean checkAABBvAABB(AABB a, AABB b)
    {
        //x axis
        if (Math.abs(a.centerX - b.centerX) > (a.halfwidth + b.halfwidth)) return false;
        //y axis
        if (Math.abs(a.centerY - b.centerY) > (a.halfheight + b.halfheight)) return false;
        return true;
    }
    
    //keep private. since centerX isn't updated 'til it's set method is called in setAABBpos(), 
    //this could cause issues if unaccounted for.
    private void setSides()
    {
        leftX = centerX - halfwidth;
        rightX = centerX + halfwidth;
        topY = centerY - halfheight;
        bottomY = centerY + halfheight;
    }

    public void setCenter(int x, int y)
    {
        centerX = x;
        centerY = y;
    }
    
    public void renderAABB()
    {
        Camera.renderSprite(leftX, topY, new Sprite(halfwidth<<1, halfheight<<1, 0xff0fca0f), true);
    }
    
    public int getCenterX()
    {
        return centerX;
    }
    
    public int getCenterY()
    {
        return centerY;
    }
    
    public int getX()
    {
        return leftX;
    }

    public int getY()
    {
        return  topY;
    }

    public int getHalfwidth()
    {
        return halfwidth;
    }
    
    public int getHalfheight()
    {
        return halfheight;
    }

    public int getLeftX()
    {
        return leftX;
    }

    public int getRightX()
    {
        return rightX;
    }

    public int getTopY()
    {
        return topY;
    }

    public int getBottomY()
    {
        return bottomY;
    }
      
    public Entity getGo()
    {
        return go;
    }
    
}

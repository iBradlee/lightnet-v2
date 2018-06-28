
package com.bradboughn.rain.collision;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.entity.Entity;
import com.bradboughn.rain.graphics.Sprite;

public class AABB 
{
    private Entity entity;
    
    private double x,y;
    private double centerX, centerY;
    private int halfwidth, halfheight;
    
    //Left & right sides, on x axis. Top & bottom sides, for y axis
    private double leftX, rightX, topY, bottomY;
    //Left, right sides (x axis of each); Top, bottom sides (y axis of each)
    
    
    public Entity.Type type;
    
    /**
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of object/entity
     * @param height height of object/entity
     * @param go Entity that this AABB belongs to
     */
    public AABB(double x, double y, int width, int height, Entity go)
    {
        this.entity = go;
        halfwidth = width >> 1;
        halfheight = height >> 1;
        
        //actual center of the go's sprite, regardless of aabb's width/height
        centerX = x + go.getSprite().WIDTH /2;
        centerY  = y + go.getSprite().HEIGHT /2;
        
        leftX = centerX - halfwidth;
        rightX = centerX + halfwidth;
        topY = centerY - halfheight;
        bottomY = centerY + halfheight;
        
        this.x = leftX;
        this.y = topY;

        type = go.getType();
    }
    
    //second constructor for constructiong niche/static AABB's that aren't attached to an object
    /**
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of object/entity
     * @param height height of object/entity
     */
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
    
    public void update()
    {
        centerX = entity.getCenterX();
        centerY = entity.getCenterY();
    }
    
    public void render()
    {
        //rendering code, for a visual representation of an object's AABB
    }
    
    public void setAABBpos()
    {
        setCenter(entity.getCenterX(), entity.getCenterY());
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

    public void setCenter(double x, double y)
    {
        centerX = x;
        centerY = y;
    }
    
    public void renderAABB()
    {
        Camera.renderSprite((int)leftX, (int)topY, new Sprite(halfwidth<<1, halfheight<<1, 0xff0fca0f), true);
    }
    
    public double getCenterX()
    {
        return centerX;
    }
    
    public double getCenterY()
    {
        return centerY;
    }
    
    public double getX()
    {
        return leftX;
    }

    public double getY()
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

    public double getLeftX()
    {
        return leftX;
    }

    public double getRightX()
    {
        return rightX;
    }

    public double getTopY()
    {
        return topY;
    }

    public double getBottomY()
    {
        return bottomY;
    }
      
    public Entity getGo()
    {
        return entity;
    }
    
}

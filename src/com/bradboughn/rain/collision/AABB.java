
package com.bradboughn.rain.collision;

import com.bradboughn.rain.gameobject.GameObject;

public class AABB {

    //can change these to bytes? don't htink i'll have sprites over 120x120
    private int halfwidth, halfheight;
    private int[] centerPos;
    
    public AABB(int width, int height)
    {
        halfwidth = width/2;
        halfheight = height/2;
        centerPos = new int[2];
    }
    
    public void setAABBpos(int x, int y)
    {
        //need to double check GameObject's x and y arent' centered.
        centerPos[0] = x + halfwidth;
        centerPos[1] = y + halfheight;
        System.out.println("centerPos[0] : " + centerPos[0] + "        centerPos[1] : " + centerPos[1] );

    }
    
    public static boolean checkAABBvAABB(AABB a, AABB b)
    {
        //x axis
        if (Math.abs(a.centerPos[0] - b.centerPos[0]) > (a.halfwidth + b.halfwidth)) 
        {
            System.out.println("x axis, no collision");
            return false;
        }
        //y axis
        if (Math.abs(a.centerPos[1] - b.centerPos[1]) > (a.halfheight + b.halfheight)) 
        {
            System.out.println("y axis, no collision");
            return false;
        }
        System.out.println("COLLISION!");
        return true;
    }
    
}

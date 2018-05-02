
package com.bradboughn.rain.collision;

import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.gameobject.GameObject;

public class AABB 
{

    //CenterPos[0] is x center point, and centerPos[1] is the y center point;
    private int[] centerPos;
    //halfWidth[0] and [1] are halfwidth, and halfheight, respectively, for AABB
    private int[] halfwidth;
    private int x,y;
    
    //corner points. tl=top left; tr=top right; bl=bottom left; br=bottom right
    private int[] tl, tr, bl, br;
    //Left, right sides (x axis of each); Top, bottom sides (y axis of each)
    private int sideL, sideR, sideT, sideB;
    
    
    public AABB(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        halfwidth = new int[]{width>>1, height>>1};
        centerPos = new int[]{x + halfwidth[0], y + halfwidth[1]};
        tl = new int[] {x,y};
        tr = new int[] {x + width, y};
        bl = new int[] {x, y + height};
        br = new int[] {x + width, y + height};
//        tr = new int[] {
//        System.out.println("x : " + x + ",      y : " + y);
//        System.out.println("centerPos[0] : " + centerPos[0] + "        centerPos[1] : " + centerPos[1] );
    }
    
    public void setAABBpos(int x, int y)
    {
        setCenterPos(x + halfwidth[0], y + halfwidth[1]);
        setCornerPoints();
    }
    
    //Need to check later if Math.abs() is efficient enough, or if I should use unsigned bits to do it
    public static boolean checkAABBvAABB(AABB a, AABB b)
    {
        //x axis
        if (Math.abs(a.centerPos[0] - b.centerPos[0]) > (a.halfwidth[0] + b.halfwidth[0])) return false;
        //y axis
        if (Math.abs(a.centerPos[1] - b.centerPos[1]) > (a.halfwidth[1] + b.halfwidth[1])) return false;
        return true;
    }
    
    public void setCornerPoints()
    {
        tl[0] = x;
        tl[1] = y;
        
        tr[0] = x + halfwidth[0]<<1;
        tr[1] = y;
        
        bl[0] = x;
        bl[1] = y + halfwidth[1]<<1;
        
        br[0] = x + halfwidth[0]<<1;
        br[1] = y + halfwidth[1]<<1;
    }
    
    public void updateSides()
    {
        
    }

    public void setCenterPos(int x, int y)
    {
        centerPos[0] = x;
        centerPos[1] = y;
    }
    
    public int[] getCenterPos()
    {
        return centerPos;
    }
    
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int[] getHalfwidth()
    {
        return halfwidth;
    }

    public int[] getTl()
    {
        return tl;
    }

    public int[] getTr()
    {
        return tr;
    }

    public int[] getBl()
    {
        return bl;
    }

    public int[] getBr()
    {
        return br;
    }
    
    
    
    
}

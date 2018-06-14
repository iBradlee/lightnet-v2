
package com.bradboughn.rain.util;

//Purpose of this class is for one simple case:
//When initializing/building Grid, Cells are assigned neighbors.
//To account for the outside Cells, which do not have neighbors on top/bot/left/right
//this class is used to store a certain boolean variable, which would be used in a switch
//statement, to dictate which type of neighboring Cells to store. So this class will be assigned
//a "isTop", "isBot", "isTopRight", "isLeft", etc., etc., and when it comes to the switch statement
//this object will be in the argument of the switch, and whatever boolean variable it holds will
//direct the Cell to insert the appropriate neighbor Cells.

public class ObjectWrapper<T>
{
    private T data;
    
    public ObjectWrapper()
    {
        data = null;
    }
    
    public ObjectWrapper(T data)
    {
        this.data = data;
    }
    
    public T getData()
    {
        return data;
    }
    
    public void setData(T data)
    {
        this.data = data;
    }
}

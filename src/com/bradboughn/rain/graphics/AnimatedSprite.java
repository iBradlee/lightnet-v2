
package com.bradboughn.rain.graphics;

public class AnimatedSprite extends Sprite
{
    //frame can be changed, depending on how you want your animation to cycle. I can automate this later
    private int frame[];
    private int frameIndex = 0;
    private Sprite sprite;
    private int rate = 9;
    private int time = 0;
    
    public AnimatedSprite(int width, int height, SpriteSheet sheet)
    {
        super(width,height,sheet);
        sprite = sheet.getSprite()[0];
        frame = new int[]{0,1,0,2};
    }
    //Ghetto temporary constructor, to handle different animation cycles (ie. {0,1,0,2} or {0,1,2,3,4}
    public AnimatedSprite(int width, int height, int[] animCycle, SpriteSheet sheet)
    {
        super(width,height,sheet);
        sprite = sheet.getSprite()[0];
        frame = animCycle;
    }
    
    public void update()
    {
        time++;
        if (time % rate ==0)
        {
            if (time >= 9) time = 0;
            if (frameIndex >= frame.length-1) frameIndex = 0;
            else frameIndex++;
            sprite = sheet.getSprite()[frame[frameIndex]];
        }
    }

    public Sprite getSprite()
    {
        return sprite;
    }
    
    public Sprite getSpriteAt(int index)
    {
        if (index <= frame.length-1)
        {
            return sheet.getSprite()[frame[index]];
        }
        else return sheet.getSprite()[frame[0]];
    }
    
    public void setFrameRate(int frames)
    {
        rate = frames;
    }
}


package com.bradboughn.rain.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private static final byte NUM_MOUSE_BUTTONS = 6;
    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int scrollWheel;
    private static boolean mouseL;
    private static boolean mouseR;
    private static boolean mouseM;
    private static boolean[] mouseButtons = new boolean[NUM_MOUSE_BUTTONS];
    
    
    public static void update()
    {
        mouseL = mouseButtons[MouseEvent.BUTTON1];
        mouseR = mouseButtons[MouseEvent.BUTTON3];
        mouseM = mouseButtons[MouseEvent.BUTTON2];
    }
        
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        mouseButtons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        mouseButtons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        scrollWheel = e.getWheelRotation();
    }

    public static int getX() {
        return mouseX;
    }

    public static int getY() {
        return mouseY;
    }

    public static boolean isMouseL() {
        return mouseL;
    }

    public static boolean isMouseR() {
        return mouseR;
    }

    public static boolean isMouseM() {
        return mouseM;
    }

    public static int getScrollWheel() {
        return scrollWheel;
    }

}

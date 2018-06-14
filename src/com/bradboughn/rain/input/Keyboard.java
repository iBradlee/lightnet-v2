/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradboughn.rain.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Brad
 */
public class Keyboard implements KeyListener 
{
    private static final int NUM_KEYBOARD_KEYS = 192;
    private static final boolean[] keys = new boolean[NUM_KEYBOARD_KEYS];
    public static boolean up, down, left, right;
    public static boolean F1;
   
    //@todo
    //Redesign Keyboard class, without using KeyListener, as it's very brittle and finicky. Instead
    //using Key Bindings, or something similar. Need to research this.
    public static void update()
    {
        up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
        F1 = keys[KeyEvent.VK_F1];
    }
        
    public static boolean isKey(int keycode)
    {
        return keys[keycode];
    }

    

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

    public static boolean isUp()
    {
        return up;
    }

    public static boolean isDown()
    {
        return down;
    }

    public static boolean isLeft()
    {
        return left;
    }

    public static boolean isRight()
    {
        return right;
    }
    
    
}

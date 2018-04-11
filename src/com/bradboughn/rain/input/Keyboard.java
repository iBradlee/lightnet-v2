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
    private final boolean[] keys = new boolean[NUM_KEYBOARD_KEYS];
    public boolean up, down, left, right;
   
    //@todo
    //Redesign Keyboard class with the assumption that we'll only have 1 static Keyboard instance, just
    //as we did with Mouse.
    public void update()
    {
        up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }
        
    public boolean isKey(int keycode)
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
    
}

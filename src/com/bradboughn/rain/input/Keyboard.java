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
public class Keyboard implements KeyListener {
    
    private final boolean[] keys = new boolean[120];
    public boolean up, down, left, right;
   
    
    public void update()
    {
        up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
        System.out.println(up + " " + down + " " + left + " " + right);
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

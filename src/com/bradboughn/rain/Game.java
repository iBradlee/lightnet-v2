
package com.bradboughn.rain;

import com.bradboughn.rain.broadphase.BroadPhase;
import com.bradboughn.rain.broadphase.implicitgrid.Grid;
import com.bradboughn.rain.camera.Camera;
import com.bradboughn.rain.entity.mob.Player;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.input.Keyboard;
import com.bradboughn.rain.input.Mouse;
import com.bradboughn.rain.level.Level;
import com.bradboughn.rain.level.TileCoordinate;
import com.bradboughn.rain.entity.tile.TileMap;
import com.bradboughn.rain.util.debuggingtools.VisualImplicitGrid;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;


public class Game implements Runnable 
{
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 500;
    private static final int HEIGHT = WIDTH/ 16 * 9;
    private static final byte SCALE = 3;
    private static final int screenWcenter = (WIDTH * SCALE)/2;
    private static final int screenHcenter = (HEIGHT * SCALE)/2;
    private static final String title = "Rain";
    
    
    private Thread thread;
    private JFrame frame;
    private Canvas canvas;
    private Keyboard key;
    private Mouse mouse;
    private Level level;
    private Player player;
    private Grid broadPhaseGrid;
    private boolean running = false;
    
    private BufferedImage image = new BufferedImage(WIDTH , HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); // Converting BufferedImage to int, for ability to store Buffer in 1D array
    
    public Game() 
    {
        canvas = new Canvas();        
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        canvas.setPreferredSize(size);
        Screen.init(WIDTH, HEIGHT);
        frame = new JFrame(); 
        key = new Keyboard();
        mouse = new Mouse();
        initInput();
        level = Level.spawn;
        TileCoordinate playerSpawn = new TileCoordinate(20, 20);
        player = new Player(playerSpawn.getX(),playerSpawn.getY());
        Camera.init(WIDTH, HEIGHT, (int)player.getX() - Camera.width/2, (int)player.getY() - Camera.height/2, level, player);
        level.add(player);
        BroadPhase.init(level);
    }
    
    public synchronized void start() 
    {
        running = true;
        thread = new Thread(this, "Display"); // Main thread initializing
        thread.start(); 
    }
    
    public synchronized void stop()
    {
        running = false;
        try 
        {
        thread.join();
        }
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() 
    {
        long lastTime = System.nanoTime(); // Logging first instance of current time
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; // 1billion nanoseconds divided by 60 (target updates per second)
        double delta = 0;
        int frames = 0; // for keeping track of FPS
        int updates = 0; // and Updates per sec. only
        canvas.requestFocus();
        while (running) 
        {
            long now = System.nanoTime(); // Logging time at top of loop
            delta += (now - lastTime) / ns; // delta variable will record time between "now" time, and "lastTime" time
            lastTime = now; // Updating time
            while (delta >= 1)
            { // Because of "ns" and "delta" algorithms, this will tick as TRUE 60 times a second
                update();
                updates++; // Ups.++ 
                delta--;
            }
            render();
            frames++; // FPS++
            
            //FPS counter and title
            if (System.currentTimeMillis() - timer > 1000) 
            {
                timer += 1000;
                frame.setTitle(title + "    |   Ups: " + updates + " FPS: " + frames);
                updates = 0;
                frames = 0;
            //
            }
        }
    }
    
    public void update()
    { 
        Keyboard.update();
        Mouse.update();
    //make sure entity (player) isn't updated 2x per tick, as it would be if this was uncommented, bc it's updated in level
//    player.update();
        level.update();
        Camera.update();
        BroadPhase.update();
        Grid.clear();
    }
    
    public void render()
    {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null)
        { // Check if bs has been created
            canvas.createBufferStrategy(3); // if not, bs is created
            return; // kicks out of if statement, assigns bs to what was created
        }
        Screen.clear(); // clear objects from screen, so previous pixels of moving objects are destroyed
        Camera.clear();
        Camera.render();
        VisualImplicitGrid.render();

        for (int i = 0; i < pixels.length; i++) 
        { // cycle thru all pixels in created window
//            pixels[i] = Screen.getPixels()[i]; // takes pixel array data from Screen class (where image is actually being processed), and puts it into pixel array from Game class (this class)
            pixels[i] = Camera.getPixels()[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        g.dispose();
        bs.show();
    }
    
    public void initInput()
    {
        canvas.addKeyListener(key);
//        frame.addKeyListener(key);
        canvas.addMouseListener(mouse);
//        frame.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
//        frame.addMouseMotionListener(mouse);
        canvas.addMouseWheelListener(mouse);
//        frame.addMouseWheelListener(mouse);
    }

    public static int getScale()
    {
        return SCALE;
    }
    
    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getScreenWcenter() {
        return screenWcenter;
    }

    public static int getScreenHcenter() {
        return screenHcenter;
    }
    
    public static void main(String[] args) 
    {
        Game game = new Game();
        game.frame.setResizable(false); // setting up values for game window, using JFrame
        game.frame.setTitle("Rain");
        game.frame.add(game.canvas);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        game.frame.setFocusable(true);

        game.start();
    }
    
}

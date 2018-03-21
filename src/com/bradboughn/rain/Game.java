
package com.bradboughn.rain;

import com.bradboughn.rain.entity.mob.Player;
import com.bradboughn.rain.graphics.Screen;
import com.bradboughn.rain.input.Keyboard;
import com.bradboughn.rain.level.Level;
import com.bradboughn.rain.level.RandomLevel;
import com.bradboughn.rain.level.SpawnLevel;
import com.bradboughn.rain.level.TileCoordinate;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;


public class Game implements Runnable {
    private static final long serialVersionUID = 1L;

    public static int width = 500;
    public static int height = width/ 16 * 9;
    public static int scale = 3;
    public static String title = "Rain";
    
    private Thread thread;
    private JFrame frame;
    private Canvas canvas;
    private Keyboard key;
    private Level level;
    private Player player;
    private boolean running = false;
    
    private Screen screen;
    
    private BufferedImage image = new BufferedImage(width , height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); // Converting BufferedImage to int, for ability to store Buffer in 1D array
    
    public Game() {
        canvas = new Canvas();        
        Dimension size = new Dimension(width * scale, height * scale);
        canvas.setPreferredSize(size);
        screen = new Screen(width, height);
        frame = new JFrame(); 
        key = new Keyboard();
        level = Level.spawn;
        TileCoordinate playerSpawn = new TileCoordinate(23, 37);
        player = new Player(playerSpawn.getX(),playerSpawn.getY(), key);
        player.init(level);
        
        canvas.addKeyListener(key);
        frame.addKeyListener(key);
    }
    
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display"); // Main thread initializing
        thread.start(); 
    }
    
    public synchronized void stop() {
        running = false;
        try {
        thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime(); // Logging first instance of current time
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; // 1billion nanoseconds divided by 60 (target updates per second)
        double delta = 0;
        int frames = 0; // for keeping track of FPS
        int updates = 0; // and Updates per sec. only
        frame.requestFocus();
        while (running) {
            long now = System.nanoTime(); // Logging time at top of loop
            delta += (now - lastTime) / ns; // delta variable will record time between "now" time, and "lastTime" time
            lastTime = now; // Updating time
            while (delta >= 1) { // Because of "ns" and "delta" algorithms, this will tick as TRUE 60 times a second
                update();
                updates++; // Ups.++ 
                delta--;
            }
            render();
            frames++; // FPS++
            
            //FPS counter and title
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + "    |   Ups: " + updates + " FPS: " + frames);
                updates = 0;
                frames = 0;
            //
            }
        }
    }
    
        public void update(){ 
        key.update();
        player.update();
        

    }
    
    public void render(){
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null){ // Check if bs has been created
            canvas.createBufferStrategy(3); // if not, bs is created
            return; // kicks out of if statement, assigns bs to what was created
        }
        screen.clear(); // clear objects from screen, so previous pixels of moving objects are destroyed
        int xScroll = player.x - screen.width / 2;
        int yScroll = player.y - screen.height / 2;
        level.render(xScroll, yScroll, screen); // Render method from Level class, taking as parameters, xScroll and yScroll (x and y, from update method above, dictated by Keyboard class)
        player.render(screen);
        
        for (int i = 0; i < pixels.length; i++) { // cycle thru all pixels in created window
            pixels[i] = screen.pixels[i]; // takes pixel array data from Screen class (where image is actually being processed), and puts it into pixel array from Game class (this class)
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        g.dispose();
        bs.show();
    }
    
    
    public static void main(String[] args) {
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

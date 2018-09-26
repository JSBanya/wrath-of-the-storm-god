package jb.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import javax.swing.UIManager;
import jb.graphics.Screen;
import jb.level.Level;


public class Game extends Canvas implements Runnable
{
	/*
	 	Main Game Handler / Flow Handler
	 */
	
	public static final long serialVersionUID = 772866767793896687L;
	public static String versionNumber = "1.2.2"; // Format: (Game number).(Major Update).(Minor Update)
	
	// Game Frame constants
	public static final int WIDTH = 700;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final double SCALE = 2.0; // Scale the game (non-integer values may result in graphic distortion)
	public static final int TPS = 60;
	public static final String TITLE = "Wrath of the Storm God";
	
	// Window
	private JFrame frame;
	private Thread thread;
	
	public boolean running;
	
	// Graphics
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public static final int CLEAR_COLOR = 0;
	
	// Game Objects
	private Screen screen;
	private Level level;
	private Mouse mouse;
	private Keyboard keyboard;
	
	public Game()
	{

		Dimension size = new Dimension((int)(WIDTH*SCALE), (int)(HEIGHT*SCALE));
		setPreferredSize(size);
		
		screen = new Screen(WIDTH, HEIGHT, CLEAR_COLOR);
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
		
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		
		frame = new JFrame();
		
		level = new Level();
		
		System.out.println("Scale set at: " + SCALE);
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.frame.setTitle(TITLE + " v" + versionNumber);
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocation(150, 150);
		game.frame.setVisible(true);
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the frame to fit the System's default look-and-feel (Changes file-loading screen)
		}
		catch (Exception e) { e.printStackTrace(); }
		
		game.start();
	}
	
	public void start()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run()
	{
		// Runs on thread start
		
		requestFocus();
		
		// Variables used to keep track of ticks per second (tps) and frames per second (fps)
		int ticksPerSecond = 0;
		int framesPerSecond = 0;
		
		// Variables used in time-management
		long startTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		final double CONVERSION_FACTOR = 1000000000.0 / TPS; // Conversion number: 1x10^9 nanoseconds (1 second) = TPS ticks
		
		while(running)
		{
			// Keep game time consistent
			long currentTime = System.nanoTime();
			delta = delta + (currentTime - startTime) / CONVERSION_FACTOR;
			startTime = System.nanoTime();
			
			while(delta >= 1.0)
			{
				tick();
				delta--;
				ticksPerSecond++;
			}
			
			render();
			framesPerSecond++;
			
			// Display fps/tps
			if(System.currentTimeMillis() - timer >= 1000)
			{
				timer = System.currentTimeMillis();
				
				frame.setTitle(TITLE + " v" + versionNumber + " | " + ticksPerSecond + " tps, " + framesPerSecond + " fps");
				
				ticksPerSecond = 0;
				framesPerSecond = 0;
			}
		}
		
	}
	
	public void tick()
	{
		// Handles time-sensitive events
		level.tick();
		keyboard.tick();
	}
	
	public void render()
	{
		// Handles graphics events
		
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		level.render(screen);
		
		// Clear and update pixels
		int[] newPixels = screen.getPixels();
		
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = newPixels[i];
		}
		
		screen.clear();
		
		// Draw image
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
}

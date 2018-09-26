package jb.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet
{
	/*
		Represents an image spritesheet
	 */
	
	private String path;
	public final int SPRITE_SIZE;
	public final int WIDTH, HEIGHT;
	private int[] pixels;

	// SpriteSheets
	
	public static SpriteSheet stormgod_idle = new SpriteSheet("/res/stormgod_idle.png", 32, 32, 32);
	public static SpriteSheet stormgod_attack = new SpriteSheet("/res/stormgod_attack.png", 32, 864, 32);
	public static SpriteSheet stormgod_frontCloud = new SpriteSheet("/res/stormgod_frontCloud.png", 32, 192, 32);
	public static SpriteSheet stormgod_backCloud = new SpriteSheet("/res/stormgod_backCloud.png", 32, 192, 32);
	public static SpriteSheet grass = new SpriteSheet("/res/grass.png", 32, 32, 32);
	public static SpriteSheet towns = new SpriteSheet("/res/town.png", 160, 128, 32);
	public static SpriteSheet font_small = new SpriteSheet("/res/font_small.png", 60, 30, 6);
	public static SpriteSheet tornado = new SpriteSheet("/res/tornado.png", 160, 64, 32);
	
	public static SpriteSheet gui_selection = new SpriteSheet("/res/gui_selection.png", 36, 36, 36);
	public static SpriteSheet gui_fillbar = new SpriteSheet("/res/gui_fillbar.png", 128, 11, 128);
	public static SpriteSheet gui_shopBackground = new SpriteSheet("/res/gui_shopBackground.png", 600, 338, 357);
	public static SpriteSheet gui_button = new SpriteSheet("/res/gui_button.png", 8, 8, 8);
	public static SpriteSheet gui_buttonPressed = new SpriteSheet("/res/gui_buttonPressed.png", 8, 8, 8);
	
	public static SpriteSheet victory_message = new SpriteSheet("/res/victory_message.png", 300, 100, 300);
	public static SpriteSheet defeat_message = new SpriteSheet("/res/defeat_message.png", 300, 100, 300);
	
	public static SpriteSheet attackIntro = new SpriteSheet("/res/attackIntro.png", 150, 127, 150);
	// End SpriteSheets
	
	
	public SpriteSheet(String path, int width, int height, int spriteSize)
	{
		this.path = path;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH*HEIGHT];
		SPRITE_SIZE = spriteSize;
		
		load();
	}
	
	public SpriteSheet(int[] pixels, int width, int height, int spriteSize)
	{
		this.pixels = pixels;
		WIDTH = width;
		HEIGHT = height;
		SPRITE_SIZE = spriteSize;
	}
	
	private void load()
	{
		// Loads the pixels of an image file to a pixels array
		
		try
		{
			long startTime = System.currentTimeMillis(); // Keep track of time needed to load spritesheet
			
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w); // get pixels from image and copy to int[] pixels
			
			System.out.println("A Spritesheet was loaded successfully in " + (System.currentTimeMillis()-startTime) + " milliseconds");
		}
		catch(IOException e)
		{
			System.out.println("A Spritesheet has failed to load from the give path: " + path);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}

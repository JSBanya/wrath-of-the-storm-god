package jb.graphics;


public class Sprite
{
	/*
	 	Contains a set of pixels to represent a model
	 */
	
	public final int SIZE; // Sprite size
	public final int WIDTH, HEIGHT;
	private int[] pixels;
	
	private SpriteSheet sheet;
	private int sheetX, sheetY; // (x,y) coordinates of the sprite on the given spriteSheet
	
	
	// Sprites
	
	public static Sprite grass = new Sprite(SpriteSheet.grass, 0, 0);
	public static Sprite stormgod_idle = new Sprite(SpriteSheet.stormgod_idle, 0, 0);
	public static Sprite town1 = new Sprite(SpriteSheet.towns, 0, 0);
	public static Sprite town2 = new Sprite(SpriteSheet.towns, 0, 1);
	public static Sprite town3 = new Sprite(SpriteSheet.towns, 0, 2);
	public static Sprite town4 = new Sprite(SpriteSheet.towns, 0, 3);
	
	public static Sprite gui_selection = new Sprite(SpriteSheet.gui_selection, 0, 0);
	public static Sprite gui_fillbar = new Sprite(SpriteSheet.gui_fillbar.getPixels(), SpriteSheet.gui_fillbar.WIDTH, SpriteSheet.gui_fillbar.HEIGHT);
	public static Sprite gui_shopBackground = new Sprite(SpriteSheet.gui_shopBackground.getPixels(), SpriteSheet.gui_shopBackground.WIDTH, SpriteSheet.gui_shopBackground.HEIGHT);
	public static Sprite gui_button = new Sprite(SpriteSheet.gui_button, 0, 0);
	public static Sprite gui_buttonPressed = new Sprite(SpriteSheet.gui_buttonPressed, 0, 0);
	
	public static Sprite victory_message = new Sprite(SpriteSheet.victory_message.getPixels(), SpriteSheet.victory_message.WIDTH, SpriteSheet.victory_message.HEIGHT);
	public static Sprite defeat_message = new Sprite(SpriteSheet.defeat_message.getPixels(), SpriteSheet.defeat_message.WIDTH, SpriteSheet.defeat_message.HEIGHT);
	// End Sprites
	
	
	public Sprite(SpriteSheet sheet, int sheetX, int sheetY)
	{
		this.sheetX = sheetX;
		this.sheetY = sheetY;
		this.sheet = sheet;
		
		SIZE = sheet.SPRITE_SIZE;
		WIDTH = SIZE;
		HEIGHT = SIZE;
		pixels = new int[SIZE*SIZE];
		
		load();
	}
	
	public Sprite(SpriteSheet sheet)
	{
		// Load an entire spritesheet to a sprite
		
		sheetX = 0;
		sheetY = 0;
		this.sheet = sheet;
		
		SIZE = sheet.SPRITE_SIZE;
		WIDTH = sheet.WIDTH;
		HEIGHT = sheet.HEIGHT;
		
		pixels = new int[WIDTH*HEIGHT];
		
		load();
	}
	
	public Sprite(int[] pixels, int width, int height)
	{
		// Create a manual sprite without the use of a spritesheet
		
		SIZE = width;
		WIDTH = width;
		HEIGHT = height;
		
		this.pixels = pixels;
	}
	
	private void load()
	{
		// Copy desired pixels from spriteSheet
		
		int[] sheetPixels = sheet.getPixels();
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				pixels[x+y*WIDTH] = sheetPixels[(x + sheetX*SIZE) + (y + sheetY*SIZE) * sheet.WIDTH];
			}
		}
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}

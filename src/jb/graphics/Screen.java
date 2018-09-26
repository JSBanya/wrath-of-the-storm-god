package jb.graphics;

import java.awt.Color;


public class Screen
{
	/*
	 	Contains methods to systematically format pixels
	*/
	
	private int width, height;
	private int[] pixels;
	
	private int clearColor;
	
	public enum Direction
	{
		NORTH, SOUTH, EAST, WEST
	}
	
	
	public Screen(int width, int height, int clearColor)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width*height]; // Total pixels in screen
		
		this.clearColor = clearColor;
	}
	
	public void render(Sprite sprite, int x, int y)
	{
		// Standard Render Method
		
		/*
		 	@Parameters:
		 	Sprite sprite: 		The sprite to be rendered
		 	int x, int y: 		The (x, y) coordinates to render the sprite
		 	boolean ignoreOffsets: 		'true' or 'false' to ignore screen offsets
		 */
		
		int[] spritePixels = sprite.getPixels();
		
		for(int yl = 0; yl < sprite.HEIGHT; yl++)
		{
			int startY = yl + y; // Current y-location to render to on the screen
			
			for(int xl = 0; xl < sprite.WIDTH; xl++)
			{
				int startX = xl + x; // Current x-location to render to on the screen
				
				if(startX >= 0 && startX < width && startY >= 0 && startY < height) // Prevent rendering outside the bounds of the screen
				{
					int pixelColor = spritePixels[xl + yl * sprite.WIDTH];
					
					pixels[startX + startY * width] = pixelColor;
				}
			}
		}
	}
	
	public void render(Sprite sprite, int x, int y, int[] colorsToRemove)
	{
		// Render Method that allows removal of given colors
		
		/*
		 	@Parameters:
		 	Sprite sprite: 		The sprite to be rendered
		 	int x, int y: 		The (x, y) coordinates to render the sprite
		 	boolean ignoreOffsets: 		'true' or 'false' to ignore screen offsets
		 	int[] colorsToRemove:		List of colors to not render
		 */
		
		int[] spritePixels = sprite.getPixels();
		
		for(int yl = 0; yl < sprite.HEIGHT; yl++)
		{
			int startY = yl + y; // Current y-location to render to on the screen
			
			for(int xl = 0; xl < sprite.WIDTH; xl++)
			{
				int startX = xl + x; // Current x-location to render to on the screen
				
				if(startX >= 0 && startX < width && startY >= 0 && startY < height) // Prevent rendering outside the bounds of the screen
				{
					int pixelColor = spritePixels[xl + yl * sprite.WIDTH];
					boolean renderColor = true;
					
					for(int col : colorsToRemove) // loop through the colors to remove and check all of them with the pixel color to be rendered
					{
						if(pixelColor == col)
							renderColor = false;
					}
					
					if(renderColor)
						pixels[startX + startY * width] = pixelColor;
				}
			}
		}
	}
	
	public static Sprite swapColors(Sprite sprite, int[] colorsToSwap, int[] newColors)
	{
		// Accepts a sprite, swaps the colors in colorsToSwap with the corresponding index of newColors, and returns the new sprite
		
		if(colorsToSwap.length == newColors.length) // Only swap colors if the lengths are the same for both arrays (indexes correspond with each other)
		{
			int[] spritePixels = sprite.getPixels();
			int[] newPixels = new int[spritePixels.length];
			
			for(int i = 0; i < spritePixels.length; i++)
			{	
				boolean found = false;
				int col = spritePixels[i];
				
				for(int j = 0; j < colorsToSwap.length && !found; j++)
				{
					if(col == colorsToSwap[j])
					{
						col = newColors[j];
						found = true;
					}
				}
				
				newPixels[i] = col;
			}
			
			Sprite newSprite = new Sprite(newPixels, sprite.WIDTH, sprite.HEIGHT);
			
			return newSprite;
		}
		else
		{
			return sprite;
		}
	}
	
	public static Sprite swapColors(Sprite sprite, int[] colorsToSwap, int[] newColors, float replacement, Direction dir)
	{
		// Replace a color up to a certain % of the total sprite's width from the left
		
		/*
		 	Parameters:
		 		Sprite sprite: 		The sprite to work with
		 		int[] colorsToReplace:		Int array to hold the colors to replace. Indexes correspond with int[] newColor
		 		int[] newColors:		Int array to hold the new colors (that replace the old from int[] colorsToReplace). Indexes correspond with int[] colorsToReplace
		 		double replacement:		The % of the total width to replace colors (as a double out of 1.0)
		 		Direction dir: Direction to zero/start the replacement 
		 */
		
		if(colorsToSwap.length == newColors.length) // Only swap colors if the lengths are the same for both arrays (indexes correspond with each other)
		{
			int[] spritePixels = sprite.getPixels();
			int[] newPixels = new int[spritePixels.length];
		
			for(int y = 0; y < sprite.HEIGHT; y++)
			{
				for(int x = 0; x < sprite.WIDTH; x++)
				{
					int pixelColor = spritePixels[x + y * sprite.WIDTH];
					
					if(dir == Direction.WEST && ((float) x / (sprite.WIDTH-1) <= replacement)) // If the ratio is under the replacement limit, replace the colors
					{
						for(int i = 0; i < colorsToSwap.length; i++)
						{
							if(pixelColor == colorsToSwap[i])
								pixelColor = newColors[i];
						}
					}
					else if(dir == Direction.EAST && ((float) (sprite.WIDTH-x-1) / (sprite.WIDTH-1) <= replacement))
					{
						for(int i = 0; i < colorsToSwap.length; i++)
						{
							if(pixelColor == colorsToSwap[i])
								pixelColor = newColors[i];
						}
					}
					else if(dir == Direction.NORTH && ((float) y / (sprite.HEIGHT-1) <= replacement))
					{
						for(int i = 0; i < colorsToSwap.length; i++)
						{
							if(pixelColor == colorsToSwap[i])
								pixelColor = newColors[i];
						}
					}
					else if(dir == Direction.SOUTH && ((float) (sprite.HEIGHT-y-1) / (sprite.HEIGHT-1) <= replacement))
					{
						for(int i = 0; i < colorsToSwap.length; i++)
						{
							if(pixelColor == colorsToSwap[i])
								pixelColor = newColors[i];
						}
					}
				
					newPixels[x + y * sprite.WIDTH] = pixelColor;
				}
			}
			
			return new Sprite(newPixels, sprite.WIDTH, sprite.HEIGHT);
		}
		else
		{
			return sprite;
		}
	}
	
	public static Sprite overlayColor(Sprite sprite, int r, int g, int b, float replacement, Direction dir)
	{
		// Overlays a color up to a certain % of the total sprite
		
		/*
		 	Parameters:
		 		Sprite sprite: 		The sprite to work with
		 		int r, g, b: 	The red, green, and blue to add to the colors
		 		float replacement:		The % of the total width to replace colors (as a float out of 1.0)
		 		Direction dir:	 Direction to zero/start the replacement 
		 */
		
		if(replacement > 0 && replacement <= 1)
		{
			int[] spritePixels = sprite.getPixels();
			int[] newPixels = new int[spritePixels.length];
			boolean addColor = false;
			
			
			for(int y = 0; y < sprite.HEIGHT; y++)
			{
				for(int x = 0; x < sprite.WIDTH; x++)
				{
					int pixelColor = spritePixels[x + y * sprite.WIDTH];
					
					addColor = false;
					
					// If the ratio is under the replacement limit, replace the colors
					if(dir == Direction.WEST && ((float) x / (sprite.WIDTH-1) <= replacement)) 
						addColor = true;
					else if(dir == Direction.EAST && ((float) (sprite.WIDTH-x-1) / (sprite.WIDTH-1) <= replacement))
						addColor = true;
					else if(dir == Direction.NORTH && ((float) y / (sprite.HEIGHT-1) <= replacement))
						addColor = true;
					else if(dir == Direction.SOUTH && ((float) (sprite.HEIGHT-y-1) / (sprite.HEIGHT-1) <= replacement))
						addColor = true;
					
					if(addColor)
						pixelColor = addColor(pixelColor, r, g, b);
				
					newPixels[x + y * sprite.WIDTH] = pixelColor;
				}
			}
			
			return new Sprite(newPixels, sprite.WIDTH, sprite.HEIGHT);
		}
		else
		{
			return sprite;
		}	
	}
	
	private static int addColor(int currentColor, int r, int g, int b)
	{
		// Adds new rgb data to a given color and returns the new color hex
		
		
		// Split color into components
		Color color = new Color(currentColor);
		int cr = color.getRed();
		int cg = color.getGreen();
		int cb = color.getBlue();
		
		// Add new additions to color
		cr += r;
		cg += g;
		cb += b;
		
		// Check bounds of new colors (no less than 0 and no greater than 255)
		if(cr > 255)
			cr = 255;
		else if(cr < 0)
			cr = 0;
		
		if(cg > 255)
			cg = 255;
		else if(cg < 0)
			cg = 0;
		
		if(cb > 255)
			cb = 255;
		else if(cb < 0)
			cb = 0;
		
		
		return new Color(cr, cg, cb).getRGB();
	}
	
	public static Sprite combineSprites(Sprite sprite1, int x1, int y1, Sprite sprite2, int x2, int y2, int voidSpaceColor)
	{
		return combineSprites(sprite1, x1, y1, sprite2, x2, y2, voidSpaceColor, new int[] {}, new int[] {});
	}
	
	public static Sprite combineSprites(Sprite sprite1, int x1, int y1, Sprite sprite2, int x2, int y2, int voidSpaceColor, int[] colorsToRemove1, int[] colorsToRemove2)
	{
		// Combines two sprites together into one, with sprite2 overlaying sprite1
		
		/*
	 	Parameters:
	 		Sprite sprite1, sprite2: 		Sprites to combine (sprite2 overlays sprite1)
	 		int x1, y1, x2, y2: 	Positions of sprites
	 		int voidSpaceColor:		Color to fill empty space not covered by either sprite during the combination
	 		int[] colorsToRemove1:		List of colors to not render in sprite1
	 		int[] colorsToRemove1:		List of colors to not render in sprite2
		 */
		
		// Find the width of the combined sprites
		int endX1 = x1 + sprite1.WIDTH;
		int endX2 = x2 + sprite2.WIDTH;
		int width = Math.max(endX1, endX2) - Math.min(x1, x2);
		
		// Find the height of the combined sprites
		int endY1 = y1 + sprite1.HEIGHT;
		int endY2 = y2 + sprite2.HEIGHT;
		int height = Math.max(endY1, endY2) - Math.min(y1, y2);
		
		
		int[] newPixels = new int[width*height];
		
		for(int i = 0; i < newPixels.length; i++)
			newPixels[i] = voidSpaceColor;
		
		
		// Place down sprite1
		int startX = 0;
		if(Math.min(x1, x2) == x2)
			startX = x1-x2;
		
		int startY = 0;
		if(Math.min(y1, y2) == y2)
			startY = y1-y2;
		
		int[] sprite1Pixels = sprite1.getPixels();
		
		for(int x = startX; x < sprite1.WIDTH+startX; x++)
		{
			for(int y = startY; y < sprite1.HEIGHT+startY; y++)
			{
				boolean renderColor = true;
				int pixelColor = sprite1Pixels[(x-startX) + (y-startY) * sprite1.WIDTH];
				
				for(int col : colorsToRemove1)
				{
					if(pixelColor == col)
						renderColor = false;
				}
				
				if(renderColor)
					newPixels[x + y * width] = pixelColor;
			}
		}
	
		
		// Place down sprite2
		startX = 0;
		if(Math.min(x1, x2) == x1)
			startX = x2-x1;
		
		startY = 0;
		if(Math.min(y1, y2) == y1)
			startY = y2-y1;
		
		int[] sprite2Pixels = sprite2.getPixels();
		
		for(int x = startX; x < sprite2.WIDTH+startX; x++)
		{
			for(int y = startY; y < sprite2.HEIGHT+startY; y++)
			{
				boolean renderColor = true;
				int pixelColor = sprite2Pixels[(x-startX) + (y-startY) * sprite2.WIDTH];
				
				for(int col : colorsToRemove2)
				{
					if(pixelColor == col)
						renderColor = false;
				}
				
				if(renderColor)
					newPixels[x + y * width] = pixelColor;
			}
		}
		
		return new Sprite(newPixels, width, height);
	}
	
	public static Sprite mirrorSprite(Sprite sprite)
	{
		// Accepts a sprite to mirror (flip left-right) and then returns the resulting sprite
		
		int[] spritePixels = sprite.getPixels();
		int[] newPixels = new int[spritePixels.length];
		
		for(int y = 0; y < sprite.HEIGHT; y++)
		{
			for(int x = 0; x < sprite.WIDTH; x++)
			{
				newPixels[x + y * sprite.WIDTH] = spritePixels[((sprite.WIDTH-1)-x) + y * sprite.WIDTH];
			}
		}
		
		return new Sprite(newPixels, sprite.WIDTH, sprite.HEIGHT);
	}
	
	public static Sprite flipSprite(Sprite sprite)
	{
		// Accepts a sprite to flip upside-down and then returns the resulting sprite
		
		int[] spritePixels = sprite.getPixels();
		int[] newPixels = new int[spritePixels.length];
		
		for(int y = 0; y < sprite.HEIGHT; y++)
		{
			for(int x = 0; x < sprite.WIDTH; x++)
			{
				newPixels[x + y * sprite.WIDTH] = spritePixels[((sprite.WIDTH-1)-x) + ((sprite.HEIGHT-1)-y)*sprite.WIDTH];
			}
		}
		
		return new Sprite(newPixels, sprite.WIDTH, sprite.HEIGHT);
	}
	
	public static SpriteSheet mirrorSprite(SpriteSheet sheet)
	{
		Sprite sprite = mirrorSprite(new Sprite(sheet.getPixels(), sheet.WIDTH, sheet.HEIGHT));
		
		return new SpriteSheet(sprite.getPixels(), sheet.WIDTH, sheet.HEIGHT, sheet.SPRITE_SIZE);
	}
	
	public static SpriteSheet flipSprite(SpriteSheet sheet)
	{
		Sprite sprite = flipSprite(new Sprite(sheet.getPixels(), sheet.WIDTH, sheet.HEIGHT));
		
		return new SpriteSheet(sprite.getPixels(), sheet.WIDTH, sheet.HEIGHT, sheet.SPRITE_SIZE);
	}
	
	public static Sprite getRectangle(int width, int height, int color)
	{
		// Manually create a rectangle with a given width and height of a given color
		
		int[] newPixels = new int[width * height];
		
		for(int i = 0; i < newPixels.length; i++)
		{
			newPixels[i] = color;
		}
		
		return new Sprite(newPixels, width, height);
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = clearColor;
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setClearColor(int newColor)
	{
		clearColor = newColor;
	}
	
	public int getClearColor()
	{
		return clearColor;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}

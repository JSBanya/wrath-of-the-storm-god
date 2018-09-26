package jb.graphics;


public class AnimatedSprite
{
	/*
	 	Handles the animation of animated sprite sheets
	 */
	
	public static AnimatedSprite town1_fire = new AnimatedSprite(SpriteSheet.towns, 1, 0, 4, 8);
	public static AnimatedSprite town2_fire = new AnimatedSprite(SpriteSheet.towns, 2, 0, 4, 8);
	public static AnimatedSprite town3_fire = new AnimatedSprite(SpriteSheet.towns, 3, 0, 4, 8);
	public static AnimatedSprite town4_fire = new AnimatedSprite(SpriteSheet.towns, 4, 0, 4, 8);

	private SpriteSheet sheet;
	
	private int[] pixels;
	private int animationLength;
	private int sheetX, sheetY;
	
	private int currentFrame;
	
	private int animationSpeed; // Measured in ticks
	private int timer;
	
	private boolean reverseAnimation = false;
	
	public AnimatedSprite(SpriteSheet sheet, int x, int y, int animationLength, int animationSpeed)
	{
		this.sheet = sheet;
		sheetX = x;
		sheetY = y;
		
		this.animationLength = animationLength;
		this.animationSpeed = animationSpeed;
		
		pixels = new int[sheet.WIDTH * sheet.HEIGHT];
		load();
	}
	
	public void load()
	{
		// Copy selected pixels from spritesheet
		
		int[] sheetPixels = sheet.getPixels();
		int spriteSize = sheet.SPRITE_SIZE;
		
		for(int yl = 0; yl < (spriteSize * animationLength); yl++)
		{
			for(int xl = 0; xl < spriteSize; xl++)
			{
				pixels[xl + yl * spriteSize] = sheetPixels[ (xl + sheetX * spriteSize) + (yl + sheetY * spriteSize) * sheet.WIDTH];
			}
		}
	}
	
	public boolean tick()
	{
		// Tick the sprite
		
		boolean reset = false;
		
		if(animationLength > 1)
		{
			if(reverseAnimation == false)
			{
				timer++;
				
				if(timer % animationSpeed == 0)
				{
					currentFrame++;
					timer = 0;
					
					if(currentFrame > animationLength-1)
					{
						currentFrame = 0;
						reset = true;
					}
				}
			}
			else // Reverse animation
			{
				if(timer > 0)
					timer--;
				else
					timer = animationSpeed;
				
				if(timer == 0)
				{
					currentFrame--;
					timer = animationSpeed;
					
					if(currentFrame < 0)
					{
						currentFrame = animationLength-1;
						reset = true;
					}
				}
			}
		}
		
		return reset;
	}
	
	public void reset()
	{
		// Reset the sprite
		currentFrame = 0;
		timer = 0;
	}
	
	public Sprite getCurrentSprite()
	{
		// Returns the current sprite of the animation
		int spriteSize = sheet.SPRITE_SIZE;
		
		if(animationLength > 1)
		{
			int[] spritePixels = new int[ spriteSize * spriteSize ] ;
			
			for(int yl = 0; yl < spriteSize; yl++)
			{
				for(int xl = 0; xl < spriteSize; xl++)
				{
					spritePixels[xl + yl * spriteSize] = pixels[ xl + (yl + currentFrame * spriteSize) * spriteSize];
				}
			}
			
			return new Sprite(spritePixels, spriteSize, spriteSize);
		}
		else
		{
			return new Sprite(pixels, spriteSize, spriteSize);
		}
	}
	
	public void nextFrame()
	{
		// Force move to the next frame
		
		currentFrame++;
		timer = 0;
		
		if(currentFrame > animationLength-1)
		{
			currentFrame = 0;
		}
	}
	
	public void reverseAnimation()
	{
		if(reverseAnimation)
			reverseAnimation = false;
		else
			reverseAnimation = true;
	}
	
	public void resetTimer()
	{
		timer = 0;
	}
	
	public int getFrameNumber()
	{
		return currentFrame;
	}
	
	public int getAnimationLength()
	{
		return animationLength;
	}
	
	public int getAnimationSpeed()
	{
		return animationSpeed;
	}
	
	public int getSpriteSize()
	{
		return sheet.SPRITE_SIZE;
	}
	
	public void setCurrentFrame(int frame)
	{
		if(frame < animationLength && frame >= 0)
			currentFrame = frame;
	}
	
	public void setSpeed(int newSpeed)
	{
		if(newSpeed > 0)
		{
			animationSpeed = newSpeed;
		}
	}
}

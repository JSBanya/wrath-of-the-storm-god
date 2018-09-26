package jb.entities;

import java.util.Random;
import jb.graphics.AnimatedSprite;
import jb.graphics.Screen;
import jb.graphics.SpriteSheet;
import jb.main.Game;
import jb.main.Mouse;

public class Player
{
	/*
  		Handles player mechanics/rendering
	 */
	
	public enum Direction
	{
		EAST, WEST
	}
	
	private Direction direction = Direction.WEST;
	private boolean attacking = false;
	
	private int x, y;
	
	// Graphics
	private AnimatedSprite spriteAttackWest, spriteAttackEast;
	private AnimatedSprite spriteIdleWest, spriteIdleEast;
	private AnimatedSprite spriteCloudFront;
	private AnimatedSprite spriteCloudBack;
	
	public Player(int x, int y)
	{
		spriteAttackWest = new AnimatedSprite(SpriteSheet.stormgod_attack, 0, 0, 27, 3);
		spriteAttackEast = new AnimatedSprite(Screen.mirrorSprite(SpriteSheet.stormgod_attack), 0, 0, 27, 3);
		
		spriteIdleWest = new AnimatedSprite(SpriteSheet.stormgod_idle, 0, 0, 1, 5);
		spriteIdleEast = new AnimatedSprite(Screen.mirrorSprite(SpriteSheet.stormgod_idle), 0, 0, 1, 5);
		
		spriteCloudFront = new AnimatedSprite(SpriteSheet.stormgod_frontCloud, 0, 0, 6, 13);
		spriteCloudBack = new AnimatedSprite(SpriteSheet.stormgod_backCloud, 0, 0, 6, 13);
		
		this.x = x;
		this.y = y;
	}
	
	public void render(Screen screen)
	{
		screen.render(spriteCloudBack.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
		
		if(direction == Direction.WEST)
		{
			if(attacking)
				screen.render(spriteAttackWest.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
			else
				screen.render(spriteIdleWest.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
		}
		else // EAST
		{
			if(attacking)
				screen.render(spriteAttackEast.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
			else
				screen.render(spriteIdleEast.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
		}
		
		screen.render(spriteCloudFront.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
	}
	
	public void tick()
	{
		Random rand = new Random();
		
		// Determine if the front cloud animation should be reversed (1 in 100)
		if(rand.nextInt(100) == 0)
			spriteCloudFront.reverseAnimation();
		
		// Determine if the back cloud animation should be reversed (1 in 100)
		if(rand.nextInt(100) == 0)
			spriteCloudBack.reverseAnimation();
		
		spriteCloudFront.tick();
		spriteCloudBack.tick();
		
		if(attacking)
		{
			if(spriteAttackWest.tick() || spriteAttackEast.tick())
			{
				attacking = false;
				spriteAttackWest.reset();
				spriteAttackEast.reset();
			}
		}
		
		tickMouse();
	}
	
	private void tickMouse()
	{
		if(Mouse.getScaledX() > Game.WIDTH/2)
		{
			direction = Direction.EAST;
		}
		else
		{
			direction = Direction.WEST;
		}
	}
	
	public void startAttack()
	{
		attacking = true;
	}
}

package jb.gui;

import jb.graphics.Screen;
import jb.graphics.Sprite;
import jb.main.Mouse;

public class ButtonComponent
{
	/*
	 	Gui object to represent a button
	 */

	private int x, y;
	private Sprite sprite, pressedSprite;
	
	private boolean pressed;
	private boolean hasCompletedPress;
	
	public ButtonComponent(Sprite sprite, Sprite pressedSprite, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.pressedSprite = pressedSprite;
	}
	
	public void tick()
	{
		if(Mouse.getButton() == 1) // Mouse clicked
		{
			if((Mouse.getScaledX() >= x) && (Mouse.getScaledX() < x+sprite.WIDTH) && (Mouse.getScaledY() >= y) && (Mouse.getScaledY() < y+sprite.HEIGHT)) // In bounds of sprite
			{
				pressed = true;
			}
		}
		else
		{
			if(pressed)
			{
				pressed = false;
				hasCompletedPress = true;
			}
		}
	}
	
	public void render(Screen screen)
	{
		if(pressed)
			screen.render(pressedSprite, x, y, new int[] { 0xFFB200FF });
		else
			screen.render(sprite, x, y, new int[] { 0xFFB200FF });
	}
	
	public boolean hasBeenPressed()
	{
		if(hasCompletedPress)
		{
			hasCompletedPress = false;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setSprite(Sprite s)
	{
		sprite = s;
	}
}

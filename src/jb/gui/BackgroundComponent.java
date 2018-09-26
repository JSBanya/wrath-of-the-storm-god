package jb.gui;

import jb.graphics.Screen;
import jb.graphics.Sprite;

public class BackgroundComponent
{
	/*
 		Gui object that represents an image to be displayed behind all other components
	 */
	
	private int x, y;
	private Sprite sprite;
	
	public BackgroundComponent(Sprite sprite, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public BackgroundComponent(int color, int x, int y, int width, int height)
	{
		sprite = Screen.getRectangle(width, height, color);
	
		this.x = x;
		this.y = y;
	}
	
	public void render(Screen screen)
	{
		screen.render(sprite, x, y, new int[] { 0xFFB200FF });
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

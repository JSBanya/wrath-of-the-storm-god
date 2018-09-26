package jb.gui;

import jb.graphics.Screen;
import jb.graphics.Sprite;

public class ImageComponent
{
	/*
	 	Gui object that represents an image to be displayed
	 */
	
	private int x, y;
	private Sprite sprite;
	
	public ImageComponent(Sprite sprite, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
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

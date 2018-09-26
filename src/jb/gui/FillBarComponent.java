package jb.gui;

import jb.graphics.Screen;
import jb.graphics.Screen.Direction;
import jb.graphics.Sprite;
import jb.main.Game;

public class FillBarComponent
{
	/*
	 	Gui component that represents a bar that can be filled to a certain % by a color
	 */
	
	private Sprite sprite = Sprite.gui_fillbar;
	
	private int x, y;
	private int fillColor;
	private float amountFilled; // 0.0 - 1.0
	
	private Direction dir;
	
	public FillBarComponent(int x, int y, int fillColor, float amountFilled)
	{
		this.x = x;
		this.y = y;
		this.fillColor = fillColor;
		
		if(amountFilled > 1.0)
			amountFilled = 1.0f;
		else if(amountFilled < 0)
			amountFilled = 0.0f;
		
		dir = Direction.WEST;
	}
	
	public FillBarComponent(int x, int y, int fillColor, float amountFilled, Direction fillOrigin)
	{
		this.x = x;
		this.y = y;
		this.fillColor = fillColor;
		
		if(amountFilled > 1.0)
			amountFilled = 1.0f;
		else if(amountFilled < 0)
			amountFilled = 0.0f;
		
		dir = fillOrigin;
	}
	
	public void render(Screen screen)
	{
		Sprite spriteToRender = Screen.swapColors(sprite, new int[] { 0xFF969696 }, new int[] { fillColor }, amountFilled, dir);
		
		screen.render(spriteToRender, x, y, new int[] { 0xFFB200FF });
	}
	
	public void setFillColor(int col)
	{
		fillColor = col;
	}
	
	public void setAmountFilled(float amountFilled)
	{
		if(amountFilled > 1.0)
			amountFilled = 1.0f;
		else if(amountFilled < 0)
			amountFilled = 0.0f;
		
		this.amountFilled = amountFilled;
	}
	
	public float getAmountFilled()
	{
		return amountFilled;
	}
}

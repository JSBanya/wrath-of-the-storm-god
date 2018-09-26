package jb.gui;

import jb.graphics.GameText;
import jb.graphics.Screen;
import jb.graphics.Sprite;
import jb.graphics.SpriteSheet;
import jb.main.Keyboard;
import jb.main.Mouse;

public class SelectionComponent
{
	/*
	 	A selection component for a gui
	 */
	
	private int x, y;
	
	private GameText text;
	
	private Sprite backgroundSprite = Sprite.gui_selection;
	
	private Sprite background_selected;
	private Sprite background_deselected;
	private Sprite foreground;
	
	private Sprite spriteSelected;
	private Sprite spriteDeselected;
	
	private int foregroundOffsetX = 0;
	private int foregroundOffsetY = 0;
	
	private int selectionColor;
	
	private boolean mouseSelection; // True or false for whether or not mouse clicking for selection is possible
	private int bindNumberSelection; // Number that selects the component
	
	private int r, g, b;
	private float overlayFilled = 0.0f;
	private Screen.Direction dir = Screen.Direction.NORTH;
	
	private boolean selected = false;
	
	
	public SelectionComponent(Sprite sprite, int x, int y, int bindNumberSelection, boolean mouseSelection, int selectionColor, boolean selected)
	{
		text = null;
		
		foreground = sprite;
		
		this.x = x;
		this.y = y;
		
		this.bindNumberSelection = bindNumberSelection;
		this.mouseSelection = mouseSelection;
		
		this.selectionColor = selectionColor;
		
		this.selected = selected;
		
		calculateForegroundOffset();
		initBackground();
		initSprites();
	}
	
	public SelectionComponent(Sprite sprite, int x, int y, int bindNumberSelection, boolean mouseSelection, int selectionColor, boolean selected, String displayText, int textColor, int spriteX, int spriteY)
	{
		text = new GameText(SpriteSheet.font_small, displayText, x + spriteX, y + spriteY, textColor, 0, 0, false);
		
		foreground = sprite;
		
		this.x = x;
		this.y = y;
		
		this.bindNumberSelection = bindNumberSelection;
		this.mouseSelection = mouseSelection;
		
		this.selectionColor = selectionColor;
		
		this.selected = selected;
		
		calculateForegroundOffset();
		initBackground();
		initSprites();
	}
	
	
	private void calculateForegroundOffset()
	{
		// Calculate the offset of the foreground so that it is as centered as possible with the background
		
		int sizeDifference = backgroundSprite.SIZE - foreground.SIZE;
		
		foregroundOffsetX = sizeDifference/2;
		foregroundOffsetY = sizeDifference/2;
	}
	
	private void initBackground()
	{
		// Initialize the background sprites
		// Must be called to update background if background is changed 
		
		background_selected = Screen.swapColors(backgroundSprite, new int[] { 0xFFFF00DC }, new int[] { selectionColor });
		background_deselected = Screen.swapColors(backgroundSprite, new int[] { 0xFFFF00DC }, new int[] { 0xFF595959 });
	}
	
	private void initSprites()
	{
		spriteSelected = Screen.combineSprites(background_selected, x, y, foreground, x + foregroundOffsetX, y + foregroundOffsetY, 0xFFB200FF, new int[] {}, new int[] { 0xFFB200FF });
		spriteDeselected = Screen.combineSprites(background_deselected, x, y, foreground, x + foregroundOffsetX, y + foregroundOffsetY, 0xFFB200FF,  new int[] {}, new int[] { 0xFFB200FF });
	}
	
	public void tick()
	{
		if(mouseSelection)
			tickMouseSelection();
		
		tickKeySelection();
	}
	
	private void tickMouseSelection()
	{
		// Handle selection by mouse clicking
		
		if(Mouse.getButton() == 1) // Mouse clicked
		{
			if((Mouse.getScaledX() >= x) && (Mouse.getScaledX() < x+backgroundSprite.SIZE) && (Mouse.getScaledY() >= y) && (Mouse.getScaledY() < y+backgroundSprite.SIZE)) // In bounds of sprite
				selected = true;
		}
	}
	
	private void tickKeySelection()
	{
		// Handles selection by number presses
		
		if(bindNumberSelection == 1 && Keyboard.key_num1)
			selected = true;
		else if(bindNumberSelection == 2 && Keyboard.key_num2)
			selected = true;
		else if(bindNumberSelection == 3 && Keyboard.key_num3)
			selected = true;
		else if(bindNumberSelection == 4 && Keyboard.key_num4)
			selected = true;
		else if(bindNumberSelection == 5 && Keyboard.key_num5)
			selected = true;
		else if(bindNumberSelection == 6 && Keyboard.key_num6)
			selected = true;
		else if(bindNumberSelection == 7 && Keyboard.key_num7)
			selected = true;
		else if(bindNumberSelection == 8 && Keyboard.key_num8)
			selected = true;
		else if(bindNumberSelection == 9 && Keyboard.key_num9)
			selected = true;
		else if(bindNumberSelection == 0 && Keyboard.key_num0)
			selected = true;
	}
	
	public void render(Screen screen)
	{
		
		if(selected)
		{
			screen.render(Screen.overlayColor(spriteSelected, r, g, b, overlayFilled, dir), x, y, new int[] { 0xFFB200FF });
		}
		else
		{
			screen.render(Screen.overlayColor(spriteDeselected, r, g, b, overlayFilled, dir), x, y, new int[] { 0xFFB200FF });
		}
	
		if(text != null)
			text.render(screen);
	}
	
	public void setFilled(int r, int g, int b, Screen.Direction dir, float amount)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		overlayFilled = amount;	
		this.dir = dir;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean s)
	{
		selected = s;
	}
	
	public void setBackground(Sprite sprite)
	{
		backgroundSprite = sprite;
		initBackground();
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}

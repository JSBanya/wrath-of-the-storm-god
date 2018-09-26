package jb.graphics;


public class GameText
{
	/*
	 		Uses the font sprite file to load and display text for a given time/location
	 */
	
	// Font coordinates (x, y)
	private int[] text_a = { 0, 0 };
	private int[] text_b = { 1, 0 };
	private int[] text_c = { 2, 0 };
	private int[] text_d = { 3, 0 };
	private int[] text_e = { 4, 0 };
	private int[] text_f = { 5, 0 };
	private int[] text_g = { 6, 0 };
	private int[] text_h = { 7, 0 };
	private int[] text_i = { 8, 0 };
	private int[] text_j = { 9, 0 };
	private int[] text_k = { 0, 1 };
	private int[] text_l = { 1, 1 };
	private int[] text_m = { 2, 1 };
	private int[] text_n = { 3, 1 };
	private int[] text_o = { 4, 1 };
	private int[] text_p = { 5, 1 };
	private int[] text_q = { 6, 1 };
	private int[] text_r = { 7, 1 };
	private int[] text_s = { 8, 1 };
	private int[] text_t = { 9, 1 };
	private int[] text_u = { 0, 2 };
	private int[] text_v = { 1, 2 };
	private int[] text_w = { 2, 2 };
	private int[] text_x = { 3, 2 };
	private int[] text_y = { 4, 2 };
	private int[] text_z = { 5, 2 };
	private int[] text_0 = { 6, 2 };
	private int[] text_1 = { 7, 2 };
	private int[] text_2 = { 8, 2 };
	private int[] text_3 = { 9, 2 };
	private int[] text_4 = { 0, 3 };
	private int[] text_5 = { 1, 3 };
	private int[] text_6 = { 2, 3 };
	private int[] text_7 = { 3, 3 };
	private int[] text_8 = { 4, 3 };
	private int[] text_9 = { 5, 3 };
	private int[] text_dot = { 6, 3 };
	private int[] text_comma = { 7, 3 };
	private int[] text_exclamation = { 8, 3 };
	private int[] text_question = { 9, 3 };
	private int[] text_apostrophe = { 0, 4 };
	private int[] text_minus = { 1, 4 };
	private int[] text_plus = { 2, 4 };
	private int[] text_percent = { 3, 4 };
	private int[] text_div = { 4, 4 };
	private int[] text_left_parenthese = { 5, 4 };
	private int[] text_right_parenthese = { 6, 4 };
	private int[] text_colon = { 7, 4 };
	private int[] text_semicolon = { 8, 4 };
	private int[] text_space = { 9, 4 };
	
	
	// Text information
	private int displayStartTime = 0; // How long to wait before displaying the text (in ticks)
	private int displayEndTime = 0; // End number of ticks to display the text. If '0', the text is displayed until manually removed
	private int timer = 0; // Keep track of time passed
	private String text;
	private int x, y;
	private boolean fixedToScreen; // 'true' or 'false' for whether the text is affected by offsets
	private int color;
	
	private int[] pixels, shadowPixels;
	private int width_pixels; // Width of text to display in # of pixels
	
	private boolean removed = false; // Whether the text displayTime has been reached
	private boolean renderText = false; // 'true' or 'false' to render text
	private boolean shadow = false;
	
	private SpriteSheet fontSheet;
	
	public GameText(SpriteSheet fontSheet, String text, int x, int y, int color, int displayStartTime, int displayEndTime, boolean shadow)
	{
		this.fontSheet = fontSheet;
		this.text = text.toLowerCase();
		this.x = x;
		this.y = y;
		this.color = color;
		this.displayStartTime = displayStartTime;
		
		if(displayStartTime == 0)
			renderText = true;
		
		this.displayEndTime = displayEndTime;
		this.shadow = shadow;
		
		width_pixels = fontSheet.SPRITE_SIZE * this.text.length();
		pixels = new int[width_pixels * fontSheet.SPRITE_SIZE]; // Width is width_pixels, height is the font's sprite SPRITE_SIZE
		
		load();
	}
	
	private void load()
	{
		width_pixels = fontSheet.SPRITE_SIZE * text.length();
		pixels = new int[width_pixels * fontSheet.SPRITE_SIZE];
		shadowPixels = new int[pixels.length];
		
		int[] fontPixels = fontSheet.getPixels();
		int fontSpriteSPRITE_SIZE = fontSheet.SPRITE_SIZE;
		char currentChar = ' ';
		
		for(int i = 0; i < text.length(); i++) // Loop through the characters of the text and then copy the pixels for each character
		{
			currentChar = text.charAt(i); // The current character of the string to be copied
			
			for(int y_loop = 0; y_loop < fontSpriteSPRITE_SIZE; y_loop++)
			{
				for(int x_loop = 0; x_loop < fontSpriteSPRITE_SIZE; x_loop++)
				{
					int color = 0; // Color of pixel
					
					if(currentChar == 'a')
						color = fontPixels[ (x_loop + text_a[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_a[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character A
					else if(currentChar == 'b')
						color = fontPixels[ (x_loop + text_b[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_b[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character B
					else if(currentChar == 'c')
						color = fontPixels[ (x_loop + text_c[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_c[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character C
					else if(currentChar == 'd')
						color = fontPixels[ (x_loop + text_d[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_d[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character D
					else if(currentChar == 'e')
						color = fontPixels[ (x_loop + text_e[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_e[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character E
					else if(currentChar == 'f')
						color = fontPixels[ (x_loop + text_f[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_f[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character F
					else if(currentChar == 'g')
						color = fontPixels[ (x_loop + text_g[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_g[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character G
					else if(currentChar == 'h')
						color = fontPixels[ (x_loop + text_h[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_h[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character H
					else if(currentChar == 'i')
						color = fontPixels[ (x_loop + text_i[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_i[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character I
					else if(currentChar == 'j')
						color = fontPixels[ (x_loop + text_j[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_j[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character J
					else if(currentChar == 'k')
						color = fontPixels[ (x_loop + text_k[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_k[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character K
					else if(currentChar == 'l')
						color = fontPixels[ (x_loop + text_l[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_l[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character L
					else if(currentChar == 'm')
						color = fontPixels[ (x_loop + text_m[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_m[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character M
					else if(currentChar == 'n')
						color = fontPixels[ (x_loop + text_n[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_n[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character N
					else if(currentChar == 'o')
						color = fontPixels[ (x_loop + text_o[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_o[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character O
					else if(currentChar == 'p')
						color = fontPixels[ (x_loop + text_p[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_p[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character P
					else if(currentChar == 'q')
						color = fontPixels[ (x_loop + text_q[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_q[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character Q
					else if(currentChar == 'r')
						color = fontPixels[ (x_loop + text_r[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_r[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character R
					else if(currentChar == 's')
						color = fontPixels[ (x_loop + text_s[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_s[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character S
					else if(currentChar == 't')
						color = fontPixels[ (x_loop + text_t[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_t[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character T
					else if(currentChar == 'u')
						color = fontPixels[ (x_loop + text_u[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_u[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character U
					else if(currentChar == 'v')
						color = fontPixels[ (x_loop + text_v[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_v[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character V
					else if(currentChar == 'w')
						color = fontPixels[ (x_loop + text_w[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_w[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character W
					else if(currentChar == 'x')
						color = fontPixels[ (x_loop + text_x[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_x[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character X
					else if(currentChar == 'y')
						color = fontPixels[ (x_loop + text_y[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_y[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character Y
					else if(currentChar == 'z')
						color = fontPixels[ (x_loop + text_z[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_z[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character Z
					else if(currentChar == '0')
						color = fontPixels[ (x_loop + text_0[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_0[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 0
					else if(currentChar == '1')
						color = fontPixels[ (x_loop + text_1[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_1[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 1
					else if(currentChar == '2')
						color = fontPixels[ (x_loop + text_2[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_2[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 2
					else if(currentChar == '3')
						color = fontPixels[ (x_loop + text_3[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_3[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 3
					else if(currentChar == '4')
						color = fontPixels[ (x_loop + text_4[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_4[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 4
					else if(currentChar == '5')
						color = fontPixels[ (x_loop + text_5[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_5[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 5
					else if(currentChar == '6')
						color = fontPixels[ (x_loop + text_6[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_6[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 6
					else if(currentChar == '7')
						color = fontPixels[ (x_loop + text_7[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_7[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 7
					else if(currentChar == '8')
						color = fontPixels[ (x_loop + text_8[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_8[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 8
					else if(currentChar == '9')
						color = fontPixels[ (x_loop + text_9[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_9[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character 9
					else if(currentChar == '.')
						color = fontPixels[ (x_loop + text_dot[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_dot[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character .
					else if(currentChar == ',')
						color = fontPixels[ (x_loop + text_comma[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_comma[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character ,
					else if(currentChar == '!')
						color = fontPixels[ (x_loop + text_exclamation[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_exclamation[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character !
					else if(currentChar == '?')
						color = fontPixels[ (x_loop + text_question[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_question[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character ?
					else if(currentChar == '\'')
						color = fontPixels[ (x_loop + text_apostrophe[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_apostrophe[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character '
					else if(currentChar == '-')
						color = fontPixels[ (x_loop + text_minus[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_minus[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character -
					else if(currentChar == '+')
						color = fontPixels[ (x_loop + text_plus[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_plus[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character +
					else if(currentChar == '%')
						color = fontPixels[ (x_loop + text_percent[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_percent[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character %
					else if(currentChar == '/')
						color = fontPixels[ (x_loop + text_div[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_div[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character /
					else if(currentChar == '(')
						color = fontPixels[ (x_loop + text_left_parenthese[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_left_parenthese[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character (
					else if(currentChar == ')')
						color = fontPixels[ (x_loop + text_right_parenthese[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_right_parenthese[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character )
					else if(currentChar == ':')
						color = fontPixels[ (x_loop + text_colon[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_colon[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character :
					else if(currentChar == ';')
						color = fontPixels[ (x_loop + text_semicolon[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_semicolon[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character ;
					else if(currentChar == ' ')
						color = fontPixels[ (x_loop + text_space[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_space[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character ' '
					else
						color = fontPixels[ (x_loop + text_space[0]*fontSpriteSPRITE_SIZE) + (y_loop + text_space[1]*fontSpriteSPRITE_SIZE)*fontSheet.WIDTH]; // Copy pixels from character ' ' by default
					
					if(color == 0xFFFFFFFF) // The color white in a font is used to signal a color replacement
					{
						color = this.color;
					}
					
					
					pixels[ (x_loop + i * fontSpriteSPRITE_SIZE) + y_loop * width_pixels] = color;
				}
			}
		} // End loop
		
		if(shadow)
		{
			for(int i = 0; i < pixels.length; i++)
			{
				if(pixels[i] == color)
					shadowPixels[i] = 0; // Set all font colors to black
				else
					shadowPixels[i] = pixels[i];
			}
		}
		
	} // End load()
	
	public void tick()
	{
		timer++;
		
		if(timer >= displayStartTime && renderText == false)
		{
			renderText = true;
		}
		
		if(displayEndTime > 0)
		{
			if(timer % displayEndTime == 0)
			{
				renderText = false;
				removed = true;
			}
		}
	}
	
	public void render(Screen screen)
	{
		if(!removed && renderText)
		{
			if(!shadow)
			{
				screen.render(new Sprite(pixels, width_pixels, fontSheet.SPRITE_SIZE), x, y, new int[] { 0xFFB200FF });
			}
			else
			{
				screen.render(new Sprite(shadowPixels, width_pixels, fontSheet.SPRITE_SIZE), x-1, y+1, new int[] { 0xFFB200FF });
				screen.render(new Sprite(pixels, width_pixels, fontSheet.SPRITE_SIZE), x, y, new int[] { 0xFFB200FF });
			}
		}
	}
	
	
	// --- Return methods ---
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String getText()
	{
		return text;
	}
	
	public boolean isFixedToScreen()
	{
		return fixedToScreen;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public int getWidth()
	{
		return width_pixels;
	}
	
	public int getHeight()
	{
		return fontSheet.SPRITE_SIZE;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
	
	public int getDisplayTime()
	{
		return displayEndTime;
	}
	
	public int getCurrentTimeDisplayed()
	{
		return timer;
	}
	// --- End return methods ---
	
	
	// --- Set methods ---
	public void setRemoved(boolean removed)
	{
		this.removed = removed;
	}
	
	public void setText(String text)
	{
		this.text = text.toLowerCase();
		load();
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	// --- End Set methods ---
}

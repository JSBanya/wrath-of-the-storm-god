package jb.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener
{
	/*
	 		A class that reads user key presses
	 */
	
	private boolean[] keys = new boolean[120]; // stores the key presses on the keyboard at any given moment
	public static boolean key_num1, key_num2, key_num3, key_num4, key_num5, key_num6, key_num7, key_num8, key_num9, key_num0;
	public static boolean key_esc;
	
	public void tick()
	{
		// Apply specific key booleans 
	
		key_num1 = keys[KeyEvent.VK_1];
		key_num2 = keys[KeyEvent.VK_2];
		key_num3 = keys[KeyEvent.VK_3];
		key_num4 = keys[KeyEvent.VK_4];
		key_num5 = keys[KeyEvent.VK_5];
		key_num6 = keys[KeyEvent.VK_6];
		key_num7 = keys[KeyEvent.VK_7];
		key_num8 = keys[KeyEvent.VK_8];
		key_num9 = keys[KeyEvent.VK_9];
		key_num0 = keys[KeyEvent.VK_0];
		
		key_esc = keys[KeyEvent.VK_ESCAPE];
	}
	
	public void keyPressed(KeyEvent e)
	{
		// Set the index of the boolean[] key array that corresponds to the key-pressed number value to 'true'
		keys[e.getKeyCode()] = true;
	}


	public void keyReleased(KeyEvent e) 
	{
		// Set the index of the boolean[] key array that corresponds to the key-pressed number value to 'false'
		keys[e.getKeyCode()] = false;
	}


	public void keyTyped(KeyEvent e) 
	{
		// Do nothing
	}
	
}
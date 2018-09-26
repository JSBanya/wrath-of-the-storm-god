package jb.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{
	/*
		Handles mouse events and keeps track of mouse x-y (both scaled and unscaled)
	 */
	
	private static int mouseX;
	private static int mouseY;
	private static int mouseButton = 0;
	private static int mouseWheelMotion;
	
	public void mouseWheelMoved(MouseWheelEvent m) 
	{
		// Negative value is up, Positive value is down
		mouseWheelMotion = m.getWheelRotation();
	}
	
	
	public void mouseDragged(MouseEvent m) 
	{
		mouseX = m.getX();
		mouseY = m.getY();
	}
	
	public void mouseMoved(MouseEvent m) 
	{
		mouseX = m.getX();
		mouseY = m.getY();
	}
	
	
	public void mouseClicked(MouseEvent m)
	{
		// Do nothing
	}
	
	public void mouseEntered(MouseEvent m) 
	{
		// Do nothing
	}
	
	public void mouseExited(MouseEvent m) 
	{
		// Do nothing
	}
	
	public void mousePressed(MouseEvent m) 
	{
		mouseButton = m.getButton();
	}
	
	public void mouseReleased(MouseEvent m) 
	{
		mouseButton = 0;
	}
	
	
	// --- Return Methods ---
	public static int getX()
	{
		// return raw (unscaled) mouse x position
		return mouseX;
	}
	
	public static double getScaledX()
	{
		// returns scaled mouse x position
		return (mouseX * 1.0) / Game.SCALE;
	}
	
	public static int getY()
	{
		// return raw (unscaled) mouse y position
		return mouseY;
	}
	
	public static double getScaledY()
	{
		// returns scaled mouse y position
		return (mouseY * 1.0) / Game.SCALE;
	}
	
	public static int getButton()
	{
		return mouseButton;
	}
	
	public static int getMouseWheelMotion()
	{
		return mouseWheelMotion;
	}
	// --- End Return methods ---
	
	
	// --- Set methods ---
	public static void resetMouseWheel()
	{
		mouseWheelMotion = 0;
	}
	// --- End Set methods ---
}

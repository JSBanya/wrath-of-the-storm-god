package jb.util;

public class GameMathUtils
{
	/*
	 	Utility class for common mathematical operations
	 */
	
	public static double getDistance(double x1, double y1, double x2, double y2)
	{
		// Returns the distance between two points
		
		double xd = Math.pow((x2 - x1), 2);
		double yd = Math.pow((y2 - y1), 2);
		
		return Math.sqrt(xd + yd);
	}
	
	public static double[] getVectorComponents(double radius, double radians)
	{
		// Returns the vector components (x, y)
		
		double[] coords = new double[] { 0.0, 0.0 };
		
		coords[0] = Math.cos(radians) * radius;
		coords[1] = Math.sin(radians) * radius;
		
		return coords;
	}
	
	public static double getRadians(double x1, double y1, double x2, double y2)
	{
		// Return the angle in radians between the two points
		
		double radians = Math.atan2(y2-y1, x2-x1);
		
		return radians;
	}
}

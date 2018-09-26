package jb.util;

import java.io.Serializable;
import jb.main.Game;

public class TownSave implements Serializable
{
	/*
	 * Stores the information of a town in the process of saving/loading
	 */
	
	private static final long serialVersionUID = Game.serialVersionUID;
	
	private int x;
	private int y;
	private double population;
	private double newTownCreationTimer;
	private int burnTimer;
	
	public TownSave(int x, int y, double population, double newTownCreationTimer, int burnTimer)
	{
		this.x = x;
		this.y = y;
		this.population = population;
		this.newTownCreationTimer = newTownCreationTimer;
		this.burnTimer = burnTimer;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	public double getPopulation()
	{
		return population;
	}

	public double getNewTownCreationTimer()
	{
		return newTownCreationTimer;
	}
	
	public int getBurnTimer()
	{
		return burnTimer;
	}
}

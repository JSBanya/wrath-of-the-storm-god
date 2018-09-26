package jb.util;

import java.io.Serializable;
import jb.main.Game;

public class TornadoSave implements Serializable
{
	/*
	 * Stores the information of a tornado in the process of saving/loading
	 */
	
	private static final long serialVersionUID = Game.serialVersionUID;
	
	private double x, y;
	private double xMovement, yMovement;
	private double rawDamage;
	private double damage;
	private double damageFalloff;
	private int tornadoLevel;
	
	public TornadoSave(double x, double y, double xMovement, double yMovement,
			double rawDamage, double damage, double damageFalloff, int tornadoLevel)
	{
		this.x = x;
		this.y = y;
		this.xMovement = xMovement;
		this.yMovement = yMovement;
		this.rawDamage = rawDamage;
		this.damage = damage;
		this.damageFalloff = damageFalloff;
		this.tornadoLevel = tornadoLevel;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getxMovement()
	{
		return xMovement;
	}

	public double getyMovement()
	{
		return yMovement;
	}

	public double getRawDamage()
	{
		return rawDamage;
	}

	public double getDamage()
	{
		return damage;
	}

	public double getDamageFalloff()
	{
		return damageFalloff;
	}
	
	public int getTornadoLevel()
	{
		return tornadoLevel;
	}
}

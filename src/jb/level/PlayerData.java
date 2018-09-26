package jb.level;

import java.io.Serializable;
import jb.main.Game;

public class PlayerData implements Serializable
{
	/*
	 	Handles and stores player data
	 */
	
	private static final long serialVersionUID = Game.serialVersionUID;
	
	private double energy_max = 1000;
	private double currentEnergy = 1000;
	private double energyIncreaseRate = 1.0; // per tick
	
	private double money = 0;

	private double money_gain_percent = 100; // % of original money gain for damaging cities (ex. 150% = 50% more money for damaging cities)
	
	public void tick()
	{
		currentEnergy += energyIncreaseRate;
		
		if(currentEnergy > energy_max)
			currentEnergy = energy_max;
	}
	
	public float getEnergyRatio()
	{
		// Returns the ratio between max energy and current energy;
		
		return (((float)currentEnergy) *1.0f)/((float)energy_max);
	}
	
	public void addEnergy(double e)
	{
		currentEnergy += e;
		
		if(currentEnergy > energy_max)
			currentEnergy = energy_max;
		else if(currentEnergy < 0)
			currentEnergy = 0f;
	}
	
	public void setEnergyMax(double e)
	{
		if(e > 0)
			energy_max = e;
	}
	
	public double getMaxEnergy()
	{
		return energy_max;
	}
	
	public double getEnergy()
	{
		return currentEnergy;
	}
	
	public double getMoney()
	{
		return money;
	}
	
	public void addMoney(double m)
	{
		money += m;
	}
	
	public void setEnergyRegen(double r)
	{
		if(r >= 0.0)
			energyIncreaseRate = r;
	}
	
	public double getEnergyRegen()
	{
		return energyIncreaseRate;
	}
	
	public double getMoneyGainPercent()
	{
		return money_gain_percent;
	}
	
	public void setMoneyGainPercent(double money_gain_percent)
	{
		this.money_gain_percent = money_gain_percent;
	}
}

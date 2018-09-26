package jb.level;

import java.io.Serializable;
import jb.main.Game;

public class ShopData implements Serializable
{
	
	private static final long serialVersionUID = Game.serialVersionUID;
	
	public final double COST_MULTIPLIER = 1.45;
	public final int MAX_UPGRADES = 10; // Maximum amount of times allowed to upgrade a single item
	
	// Costs
	public final int EF1_DAMAGE_COST = 600;
	public final int EF1_RANGE_COST = 600;
	public final int EF1_ENERGY_COST = 300;
	public final int EF1_COOLDOWN_COST = 400;
	
	public final int EF2_DAMAGE_COST = 300;
	public final int EF2_RANGE_COST = 800;
	public final int EF2_ENERGY_COST = 300;
	public final int EF2_COOLDOWN_COST = 600;
	
	public final int EF3_DAMAGE_COST = 600;
	public final int EF3_RANGE_COST = 1000;
	public final int EF3_ENERGY_COST = 600;
	public final int EF3_COOLDOWN_COST = 800;
	
	public final int EF4_DAMAGE_COST = 1000;
	public final int EF4_RANGE_COST = 1400;
	public final int EF4_ENERGY_COST = 1200;
	public final int EF4_COOLDOWN_COST = 1000;
	
	public final int EF5_DAMAGE_COST = 1400;
	public final int EF5_RANGE_COST = 1800;
	public final int EF5_ENERGY_COST = 1500;
	public final int EF5_COOLDOWN_COST = 1400;
	
	public final int MAXIMUM_ENERGY_COST = 800;
	public final int ENERGY_REGEN_COST = 2000;
	
	public final int MONEY_GAIN_COST = 3000;
	
	public final int BURN_CHANCE_COST = 1600;
	public final int BURN_DAMAGE_COST = 2000;
	
	// Additions per upgrade
	public final double EF1_DAMAGE_ADDITION = 0.2;
	public final double EF1_RANGE_ADDITION = 4.0;
	public final double EF1_ENERGY_ADDITION = -2.0;
	public final int EF1_COOLDOWN_ADDITION = -2;
	
	public final double EF2_DAMAGE_ADDITION = 0.5;
	public final double EF2_RANGE_ADDITION = 5.0;
	public final double EF2_ENERGY_ADDITION = -15.0;
	public final int EF2_COOLDOWN_ADDITION = -8;
	
	public final double EF3_DAMAGE_ADDITION = 1.0;
	public final double EF3_RANGE_ADDITION = 6.0;
	public final double EF3_ENERGY_ADDITION = -35.0;
	public final int EF3_COOLDOWN_ADDITION = -30;
	
	public final double EF4_DAMAGE_ADDITION = 2.0;
	public final double EF4_RANGE_ADDITION = 7.0;
	public final double EF4_ENERGY_ADDITION = -50.0;
	public final int EF4_COOLDOWN_ADDITION = -50;
	
	public final double EF5_DAMAGE_ADDITION = 2.2;
	public final double EF5_RANGE_ADDITION = 8.0;
	public final double EF5_ENERGY_ADDITION = -70.0;
	public final int EF5_COOLDOWN_ADDITION = -90;
	
	public final int MAXIMUM_ENERGY_ADDITION = 250;
	public final double ENERGY_REGEN_ADDITION = 0.16;
	
	public final double MONEY_GAIN_ADDITION = 10.0; //+%
	
	public final double BURN_CHANCE_ADDITION = -700.0;
	public final double BURN_DAMAGE_ADDITION = 1.0;
	
	// Number of upgrades
	public int ef1_damage_upgrades = 0; 
	public int ef1_range_upgrades = 0; 
	public int ef1_energy_upgrades = 0; 
	public int ef1_cooldown_upgrades = 0; 
	
	public int ef2_damage_upgrades = 0; 
	public int ef2_range_upgrades = 0; 
	public int ef2_energy_upgrades = 0; 
	public int ef2_cooldown_upgrades = 0; 
	
	public int ef3_damage_upgrades = 0; 
	public int ef3_range_upgrades = 0; 
	public int ef3_energy_upgrades = 0; 
	public int ef3_cooldown_upgrades = 0; 
	
	public int ef4_damage_upgrades = 0; 
	public int ef4_range_upgrades = 0; 
	public int ef4_energy_upgrades = 0; 
	public int ef4_cooldown_upgrades = 0; 
	
	public int ef5_damage_upgrades = 0; 
	public int ef5_range_upgrades = 0; 
	public int ef5_energy_upgrades = 0; 
	public int ef5_cooldown_upgrades = 0; 
	
	public int maximum_energy_upgrades = 0;
	public int energy_regen_upgrades = 0;
	
	public int money_gain_upgrades = 0;
	
	public int burn_chance_upgrades = 0;
	public int burn_damage_upgrades = 0;
	
}

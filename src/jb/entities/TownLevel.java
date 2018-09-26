package jb.entities;

public enum TownLevel
{
	LEVEL1(1.0, 1.0/(60.0 * 1.0), 1.0/(60.0 * 120.0), 60, 15000), 
	LEVEL2(0.90, 1.0/(60.0 * 0.75), 1.0/(60.0 * 70), 100, 10000),
	LEVEL3(0.75, 1.0/(60.0 * 0.40), 1.0/(60.0 * 45), 125, 6000), 
	LEVEL4(0.66, 1.0/(60.0 * 0.10), 1.0/(60.0 * 25), 160, 3000);
	
	public final double DEFENSE; // Defense constant of a town (% in decimal format of damage taken: 1.0 = 100% normal damage taken, 0.5 = 50% normal damage taken)
	public final double GROWTH_SPEED; // Speed constant of town's population growth (# of people per tick)
	public final double NEW_TOWN_CREATION_SPEED; // Speed constant of the creation of new towns (# of towns per tick)
	public final double MAX_SPAWN_DISTANCE; // Maximum distance to create new town
	public final int FIRE_DISPEL_CHANCE; // Chance (1 out of value) to dispel fire effect per tick
	
	// Minimum populations for town levels
	public static final int LEVEL_2_POPULATION_MINIMUM = 300;
	public static final int LEVEL_3_POPULATION_MINIMUM = 2500;
	public static final int LEVEL_4_POPULATION_MINIMUM = 8000;
	
	TownLevel(double defense, double growthSpeed, double townCreationSpeed, double newTownSpawnDistance, int fireDispelChance)
	{
		DEFENSE = defense;
		GROWTH_SPEED = growthSpeed;
		NEW_TOWN_CREATION_SPEED = townCreationSpeed;
		MAX_SPAWN_DISTANCE = newTownSpawnDistance;
		FIRE_DISPEL_CHANCE = fireDispelChance;
	}
}

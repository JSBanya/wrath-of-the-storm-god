package jb.entities;

import java.util.Random;
import jb.graphics.AnimatedSprite;
import jb.graphics.GameText;
import jb.graphics.Screen;
import jb.graphics.Sprite;
import jb.graphics.SpriteSheet;
import jb.level.Level;
import jb.main.Game;
import jb.util.GameMathUtils;
import jb.util.TownSave;

public class Town
{
	/*
	 	Represents a population center
	 */
	

	private Level level;
	
	private int x, y;
	private TownLevel townLevel;
	private Sprite sprite;
	private double population = 0;
	
	private boolean removed = false;
	private boolean createNewTown = false;

	
	private AnimatedSprite burningSprite;
	public static int burn_chance = 10000; // Chance for a tornado hit to cause burning
	public static double burn_damage_multiplier = 3.0;
	public static final int FIRE_SPREAD_CHANCE = 1000; // Chance for fire to spread to nearby towns
	private final int BURN_TIMER = 1200;
	private int burnTimer = 0;
	
	// Util
	private double newTownCreationTimer = 0;
	
	// Constants
	public static final int MINIMUM_SPAWN_DISTANCE = 34; // Used to create a minimum distance between town spawns (prevent town overlap)
	public static final int TOWN_MERGE_CHANCE = 25000; // Change for two nearby towns to merge into one per tick :: 1 out of TOWN_MERGE_CHANCE value
	
	
	public Town(int x, int y, Level level, double population)
	{
		this.x = x;
		this.y = y;
		
		this.level = level;
		
		this.population = population;
		updateSprite();
	}
	
	public Town(TownSave save, Level level)
	{
		x = save.getX();
		y = save.getY();
		
		this.level = level;
		
		population = save.getPopulation();
		newTownCreationTimer = save.getNewTownCreationTimer();
		
		this.burnTimer = save.getBurnTimer();
		
		updateSprite();
	}
	
	public void tick()
	{
		tickPopulation();
		
		if(isBurning())
			tickBurning();
		
		tickTownCreation();
		updateSprite();
	}
	
	public void render(Screen screen)
	{
		if(!removed)
		{
			if(isBurning())
				screen.render(burningSprite.getCurrentSprite(), x, y, new int[] { 0xFFB200FF });
			else
				screen.render(sprite, x, y, new int[] { 0xFFB200FF });
			
			new GameText(SpriteSheet.font_small, "" + ((int)population), x+12, y+25, 0xFFFFFFFF, 0, 0, true).render(screen);
		}
	}
	
	public void damage(double damage)
	{
		// Damage the town and reduce the population
		damage = damage * townLevel.DEFENSE;
		population -= damage;
		
		if(!isBurning() && new Random().nextInt(burn_chance) == 0)
			burn();
	}
	
	private void tickPopulation()
	{
		// Tick population growth
		
		if(isBurning())
			population -= townLevel.GROWTH_SPEED*burn_damage_multiplier;
		else
			population += townLevel.GROWTH_SPEED;
	}
	
	private void tickBurning()
	{
		Random rand = new Random();
	
		burningSprite.tick();
		
		if(rand.nextInt(FIRE_SPREAD_CHANCE) == 0) // Spread fire to nearby town
		{
			Town[] towns = level.findTown(x, y, MINIMUM_SPAWN_DISTANCE*3, 0, 0);
			
			if(towns.length > 1) // Towns are available
			{
				boolean found = false;
				
				for(int i = 0; i < towns.length && !found; i++)
				{
					if(!towns[i].isBurning() && towns[i] != this)
					{
						found = true;
						towns[i].burn();
						burnTimer /= 2;
					}
				}
			}
		}
		
		if(rand.nextInt(townLevel.FIRE_DISPEL_CHANCE) == 0)
			burnTimer = 0;
		else
			burnTimer--;
	}
	
	public void tickTownCreation()
	{
		// Tick town creation
		Random rand = new Random();
		
		double timerAddition = townLevel.NEW_TOWN_CREATION_SPEED + townLevel.NEW_TOWN_CREATION_SPEED * ((rand.nextInt(50)/100.0) * (rand.nextInt(3)-1)); // Create slight random variation in spawn time
		
		newTownCreationTimer += timerAddition;
		
		if(newTownCreationTimer >= 1.0)
		{
			newTownCreationTimer--;
			createNewTown = true;
		}
		
		if(townLevel != TownLevel.LEVEL4 && rand.nextInt(TOWN_MERGE_CHANCE) == 0)
		{
			// Merge with a nearby town (if available)
			
			Town[] towns = level.findTown(x, y, MINIMUM_SPAWN_DISTANCE*2, 0, 0);
			
			if(towns.length > 1) // Towns are available
			{
				boolean found = false;
				
				for(int i = 0; i < towns.length && !found; i++)
				{
					if(towns[i].getTownLevel() != TownLevel.LEVEL4 && towns[i] != this)
					{
						found = true;
						
						double newPopulation = population + towns[i].getPopulation();
						
						// Create new town (overwrite current town and set other town to be removed)
						population = newPopulation;
						
						if(towns[i].isBurning() && !isBurning()) // Begin to burn if the town being merged is burning
							burn();
						
						towns[i].setPopulation(0);
						towns[i].setRemoved(true);
					}
				}
			}
		}
	}
	
	private void updateSprite()
	{
		// Updates the sprite and town level based on the population
		
		if(population >= TownLevel.LEVEL_4_POPULATION_MINIMUM)
		{
			townLevel = TownLevel.LEVEL4;
			sprite = Sprite.town4;
			burningSprite = AnimatedSprite.town4_fire;
		}
		else if(population >= TownLevel.LEVEL_3_POPULATION_MINIMUM)
		{
			townLevel = TownLevel.LEVEL3;
			sprite = Sprite.town3;
			burningSprite = AnimatedSprite.town3_fire;
		}
		else if(population >= TownLevel.LEVEL_2_POPULATION_MINIMUM)
		{
			townLevel = TownLevel.LEVEL2;
			sprite = Sprite.town2;
			burningSprite = AnimatedSprite.town2_fire;
		}
		else if(population >= 1.0)
		{
			townLevel = TownLevel.LEVEL1;
			sprite = Sprite.town1;
			burningSprite = AnimatedSprite.town1_fire;
		}
		else // town destroyed
		{
			removed = true;
		}
	}
	
	public Town getNewTown()
	{
		// Returns a new town ('null' if no new town can be created)
		
		Town town = null;
		
		
		// Create new town only if all of the following a true: 
		//			time has been reached for a new town to be made 
		//			the currently population is greater than 50
		
		if(createNewTown && population > 50) 
		{
			createNewTown = false;
			
			// New town gains 25% of the town's population.
			double newTownPopulation = population * 0.25;
			
			
			// Calculate (x, y) of new town
			// New towns are created within a certain radius from the original
			
			Random rand = new Random();
			
			boolean found = false;
			
			int xDistance = 0;
			int yDistance = 0;
			
			double radians = Math.toRadians(rand.nextDouble() * 360); // Radian angle to spawn new town
			double[] maxCoords = GameMathUtils.getVectorComponents(townLevel.MAX_SPAWN_DISTANCE, radians);
			
			xDistance = (int) (rand.nextDouble() * maxCoords[0]);
			yDistance = (int) (rand.nextDouble() * maxCoords[1]);
			
			if((x + xDistance) <= 0 || (x + xDistance + 32) > Game.WIDTH) // If town is out of bounds in x direction
			{
				xDistance *= -1; // Reverse x
			}
			
			if((y + yDistance) <= 0 || (y + yDistance + 32) > Game.HEIGHT) // If town is out of bounds in y direction
			{
				yDistance *= -1; // Reverse y
			} 
			
			if(level.findNumberOfTowns((x + xDistance), (y + yDistance), MINIMUM_SPAWN_DISTANCE) > 0) // If the town is overlapping a current town
			{
				for(int i = 0; i < 360 && !found; i += 45) // Change the angle of new town creation by 45 degrees until a valid location is found
				{
					radians += Math.toRadians(45);
					
					maxCoords = GameMathUtils.getVectorComponents(townLevel.MAX_SPAWN_DISTANCE, radians);
					
					xDistance = (int) (rand.nextDouble() * maxCoords[0]);
					yDistance = (int) (rand.nextDouble() * maxCoords[1]);
					
					if((x + xDistance) <= 0 || (x + xDistance + 32) > Game.WIDTH) // If town is out of bounds in x direction
					{
						xDistance *= -1; // Reverse x
					}
					
					if((y + yDistance) <= 0 || (y + yDistance + 32) > Game.HEIGHT) // If town is out of bounds in y direction
					{
						yDistance *= -1; // Reverse y
					} 
					
					if(level.findNumberOfTowns((x + xDistance), (y + yDistance), MINIMUM_SPAWN_DISTANCE) == 0)
							found = true;
				}
			}
			else
			{
				found = true;
			}
			
			if(found)
			{
				town = new Town(x + xDistance, y + yDistance, level, newTownPopulation);
			}
		}
		
		return town;
	}
	
	public TownSave saveToFile()
	{
		return new TownSave(x, y, population, newTownCreationTimer, burnTimer);
	}
	
	public boolean isBurning()
	{
		if(burnTimer > 0)
			return true;
		else
			return false;
	}
	
	public void setBurnTimer(int burnTimer)
	{
		this.burnTimer = burnTimer;
	}
	
	public void burn()
	{
		burnTimer = BURN_TIMER;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
	
	public void setRemoved(boolean removed)
	{
		this.removed = removed;
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
	
	public void setPopulation(double population)
	{
		this.population = population;
	}
	
	public TownLevel getTownLevel()
	{
		return townLevel;
	}
}

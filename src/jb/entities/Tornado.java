package jb.entities;

import jb.graphics.AnimatedSprite;
import jb.graphics.Screen;
import jb.graphics.SpriteSheet;
import jb.main.Game;
import jb.util.GameMathUtils;
import jb.util.TornadoSave;

public class Tornado
{
	/*
	 	Represents a tornado entity
	 */
	
	private final double DAMAGE_LOSS = ((10.0)/Game.TPS)/100; // Decimal of % lost per tick;  ((% lost per second)/Game.TPS)/100
	
	private double x, y;
	private double xMovement, yMovement; // Represents the (x,y) movement per tick
	
	private final double RAW_DAMAGE;
	private double damage;
	
	private final double DAMAGE_FALLOFF; // Damage lost per unit distance from tornado
	
	private AnimatedSprite sprite;
	private boolean removed = false;
	
	private int tornadoLevel;
	
	public Tornado(int tornadoLevel, int x, int y, double xMovement, double yMovement, double damage, double damageRange)
	{
		this.tornadoLevel = tornadoLevel;
		this.x = x;
		this.y = y;
		this.xMovement = xMovement;
		this.yMovement = yMovement;
		
		this.RAW_DAMAGE = damage;
		this.damage = damage;

		// Calculate damage fall-off
		DAMAGE_FALLOFF = damage/damageRange;
		
		sprite = getSprite();
	}
	
	public Tornado(TornadoSave save)
	{
		this.tornadoLevel = save.getTornadoLevel();
		x = save.getX();
		y = save.getY();
		xMovement = save.getxMovement();
		yMovement = save.getyMovement();
		
		RAW_DAMAGE = save.getRawDamage();
		damage = save.getDamage();
		
		DAMAGE_FALLOFF = save.getDamageFalloff();
		
		sprite = getSprite();
	}
	
	public void tick()
	{
		// Move tornado
		x += xMovement;
		y += yMovement;
		
		// Tick sprite
		sprite.tick();
		
		damage = damage - (RAW_DAMAGE * DAMAGE_LOSS); // Damage loss over time
		
		// Check removed
		if(damage <= 0.0)
			removed = true;
	}
	
	public void render(Screen screen)
	{
		if(!removed)
			screen.render(sprite.getCurrentSprite(), (int)x, (int)y, new int[] { 0xFFB200FF });
	}
	
	public double getDamage(int x, int y)
	{
		// Returns the damage of the tornado at the given (x, y)
		
		double distance = GameMathUtils.getDistance(this.x, this.y, x, y);
		
		double newDamage = damage - (DAMAGE_FALLOFF * distance); 
		
		if(newDamage <= 0) // (x,y) is out of tornado's range
			return 0.0;
		else
			return newDamage;
	}
	
	public double getRange()
	{
		// Returns the range within which the tornado can currently damage
		
		return damage/DAMAGE_FALLOFF;
		
	}
	
	public AnimatedSprite getSprite()
	{
		if(tornadoLevel == 1)
			return new AnimatedSprite(SpriteSheet.tornado, 0, 0, 2, 10);
		else if(tornadoLevel == 2)
			return new AnimatedSprite(SpriteSheet.tornado, 1, 0, 2, 10);
		else if(tornadoLevel == 3)
			return new AnimatedSprite(SpriteSheet.tornado, 2, 0, 2, 10);
		else if(tornadoLevel == 4)
			return new AnimatedSprite(SpriteSheet.tornado, 3, 0, 2, 10);
		else
			return new AnimatedSprite(SpriteSheet.tornado, 4, 0, 2, 10);
	}
	
	public TornadoSave saveToFile()
	{
		return new TornadoSave(x, y, xMovement, yMovement, RAW_DAMAGE, damage, DAMAGE_FALLOFF, tornadoLevel);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getXMovement()
	{
		return xMovement;
	}
	
	public double getYMovement()
	{
		return yMovement;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
}

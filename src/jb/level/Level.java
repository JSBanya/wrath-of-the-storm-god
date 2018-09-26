package jb.level;

import java.util.ArrayList;
import java.util.Random;
import jb.entities.Player;
import jb.entities.Tornado;
import jb.entities.TornadoStats;
import jb.entities.Town;
import jb.graphics.GameText;
import jb.graphics.Screen;
import jb.graphics.Sprite;
import jb.graphics.SpriteSheet;
import jb.gui.BackgroundComponent;
import jb.gui.FillBarComponent;
import jb.gui.GraphicsInterface;
import jb.gui.ImageComponent;
import jb.gui.SelectionComponent;
import jb.main.Game;
import jb.main.Keyboard;
import jb.main.Mouse;
import jb.util.GameMathUtils;
import jb.util.SaveFile;
import jb.util.TornadoSave;
import jb.util.TownSave;

public class Level
{
	/*
	  	Handles level mechanics
	 */
	
	private ArrayList<Town> towns = new ArrayList<Town>();
	private ArrayList<Tornado> tornadoes = new ArrayList<Tornado>();
	
	private Player player;
	private PlayerData playerData;
	
	private boolean hasWon = false;
	private boolean hasLost = false;
	private boolean winDisplayAdded = false;
	private final int MAXIMUM_TOWNS_TO_LOSS = 80;
	
	private int time = 0;
	private double difficultyIncrease = 0.0;
	private int increaseUnits = 0; // Number of units that have been added to difficulty
	private final double DIFFICULTY_INCREASE_UNIT = 0.08; // Increase in difficulty per addition
	private final int DIFFICULTY_ALERT_TIMER = 150;
	private int difficultyAlertTimer = 0;
	private GameText difficultyAlert;
	
	private GameText timerText;
	
	// Tornado addition variables
	private double t_mouseX = -1, t_mouseY = -1;
	private boolean tornadoReady = false;
	private double t_radian_angle = 0;
	
	// GUI
	private GraphicsInterface userHud = new GraphicsInterface(); // In-game hud (Heads up display)
	private GameText energyRatioText, moneyText;
	
	// Shop  Variables
	private Shop shop;
	private ShopData shopData = new ShopData();
	private final int SHOP_TIMER_MAX = 15;
	private int shopTimer = 15;
	private boolean shopActivated = false;
	
	// Tornado Timer variables
	private int ef1_timer = 0, ef2_timer = 0, ef3_timer = 0, ef4_timer = 0, ef5_timer = 0;
	
	// Introduction
	private boolean intro = true;
	private ArrayList<GraphicsInterface> introFrames = new ArrayList<GraphicsInterface>();
	private int currentIntroFrame = 0;
	private final int INTRO_FRAME_CLICK_TIMER_MAX = 20; // Timer used in limited clicks during introduction
	private int introFrameClickTimer = INTRO_FRAME_CLICK_TIMER_MAX;
	
	public Level()
	{
		player = new Player(Game.WIDTH/2-16, Game.HEIGHT/2-16);
		playerData = new PlayerData();
		
		// Add random starting metropolis
		int startPos = new Random().nextInt(4);
		
		if(startPos == 0)
			towns.add(new Town(50, 50, this, 12000));
		else if(startPos == 1)
			towns.add(new Town(Game.WIDTH-(50+32), 50, this, 12000));
		else if(startPos == 2)
			towns.add(new Town(50, Game.HEIGHT-(50+32), this, 12000));
		else
			towns.add(new Town(Game.WIDTH-(50+32), Game.HEIGHT-(50+32), this, 12000));
		
		
		// Add random starting cities
		int numAdded = 0;
		
		while(numAdded < 4)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 150) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 5000));
					numAdded++;
				}
			}
		}
		
		// Add random starting town
		numAdded = 0;
		
		while(numAdded < 6)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 150) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 750));
					numAdded++;
				}
			}
		}
		
		// Add random starting villages
		numAdded = 0;
		
		while(numAdded < 10)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 75) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 100));
					numAdded++;
				}
			}
		}
		
		initHud();
		initIntro();
		
		shop = new Shop(playerData, shopData, this);
	}
	
	private void initHud()
	{
		// Initialize the HUD
		int sel_startX = Game.WIDTH/2 - 100;
		int sel_startY = Game.HEIGHT - 44;
		int sel_xShift = 40;
		
		userHud.addSelectionComponent(new SelectionComponent(new Sprite(SpriteSheet.tornado, 0, 0), sel_startX + sel_xShift*0, sel_startY, 1, true, 0xFFFFFFFF, true, "1", 0xFFFFFFFF, 2, 2)); // EF1
		userHud.addSelectionComponent(new SelectionComponent(new Sprite(SpriteSheet.tornado, 1, 0), sel_startX + sel_xShift*1, sel_startY, 2, true, 0xFFFFFFFF, true, "2", 0xFFFFFFFF, 2, 2)); // EF2
		userHud.addSelectionComponent(new SelectionComponent(new Sprite(SpriteSheet.tornado, 2, 0), sel_startX + sel_xShift*2, sel_startY, 3, true, 0xFFFFFFFF, true, "3", 0xFFFFFFFF, 2, 2)); // EF3
		userHud.addSelectionComponent(new SelectionComponent(new Sprite(SpriteSheet.tornado, 3, 0), sel_startX + sel_xShift*3, sel_startY, 4, true, 0xFFFFFFFF, true, "4", 0xFFFFFFFF, 2, 2)); // EF4
		userHud.addSelectionComponent(new SelectionComponent(new Sprite(SpriteSheet.tornado, 4, 0), sel_startX + sel_xShift*4, sel_startY, 5, true, 0xFFFFFFFF, true, "5", 0xFFFFFFFF, 2, 2)); // EF5
		userHud.selectSelectionComponent(0);
		
		userHud.addFillBarComponent(new FillBarComponent(Game.WIDTH/2 - 64, Game.HEIGHT - 60, 0xFF00DB19, 1.0f));
		
		energyRatioText = new GameText(SpriteSheet.font_small, "" + (int)playerData.getEnergy() + "/" + (int)playerData.getMaxEnergy(), 0, Game.HEIGHT - 58, 0xFFFFFFFF, 0, 0, false);
		energyRatioText.setX(Game.WIDTH/2 - energyRatioText.getWidth()/2); // Center text
		
		moneyText = new GameText(SpriteSheet.font_small, "Money: " + (int)playerData.getMoney(), Game.WIDTH/2, 10, 0xFFFFFFFF, 0, 0, true);
		moneyText.setX(Game.WIDTH/2 - moneyText.getWidth()/2);
		
		timerText = new GameText(SpriteSheet.font_small, "Timer: 0 seconds", 5, 5, 0xFFFFFFFF, 0, 0, false);
		
		difficultyAlert = new GameText(SpriteSheet.font_small, "Difficulty Increased!", Game.WIDTH/2, 40, 0xFFFF2E00, 0, 0, true);
		difficultyAlert.setX(Game.WIDTH/2 - difficultyAlert.getWidth()/2); // Center text
	}
	
	private void initIntro()
	{
		// Initialize the introduction frames
		
		final int BACKGROUND_COLOR = 0xFF050505;
		
		//Frame 0
		GraphicsInterface frame0 = new GraphicsInterface();
		String frame0_text1 = "Welcome to Wrath of the Storm God!";
		String frame0_text2 = "In Wrath of the Storm God, you take control of a deity with the power to create tornadoes.";
		String frame0_text3 = "Humanity has recently been creeping into the lands you call home, destroying fields";
		String frame0_text4 = "to make room for factories and real estate. It's all about location, they say!";
		String frame0_text5 = "But now it's time for you to take a stand. Can you drive them back before it's too late?";
		
		int frame0_start_y = 100, frame0_y_spacing = 20;
		
		frame0.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame0.addText(new GameText(SpriteSheet.font_small, frame0_text1, Game.WIDTH/2 - (frame0_text1.length()/2)*6, frame0_start_y + 0, 0xFFFF6A00, 0, 0, false));
		frame0.addText(new GameText(SpriteSheet.font_small, frame0_text2, Game.WIDTH/2 - (frame0_text2.length()/2)*6, frame0_start_y + 50, 0xFFFFFFFF, 0, 0, false));
		frame0.addText(new GameText(SpriteSheet.font_small, frame0_text3, Game.WIDTH/2 - (frame0_text3.length()/2)*6, frame0_start_y + 50 + frame0_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		frame0.addText(new GameText(SpriteSheet.font_small, frame0_text4, Game.WIDTH/2 - (frame0_text4.length()/2)*6, frame0_start_y + 50 + frame0_y_spacing*2, 0xFFFFFFFF, 0, 0, false));
		frame0.addText(new GameText(SpriteSheet.font_small, frame0_text5, Game.WIDTH/2 - (frame0_text5.length()/2)*6, frame0_start_y + 50 + frame0_y_spacing*3, 0xFFFFFFFF, 0, 0, false));
		
		
		//Frame 1
		GraphicsInterface frame1 = new GraphicsInterface();
		String frame1_text1 = "Tornadoes will damage nearby cities, reducing population.";
		String frame1_text2 = "Bigger cities take less damage than smaller ones.";
		
		int frame1_start_y = 150, frame1_y_spacing = 20;
		
		frame1.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame1.addText(new GameText(SpriteSheet.font_small, frame1_text1, Game.WIDTH/2 - (frame1_text1.length()/2)*6, frame1_start_y + frame1_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame1.addText(new GameText(SpriteSheet.font_small, frame1_text2, Game.WIDTH/2 - (frame1_text2.length()/2)*6, frame1_start_y + frame1_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		
		
		// Frame 2
		GraphicsInterface frame2 = new GraphicsInterface();
		
		String frame2_text1 = "Cities can randomly create new ones nearby or join together with a nearby city.";
		String frame2_text2 = "Bigger cities will create new ones faster!";
		
		int frame2_start_y = 150, frame2_y_spacing = 20;
		
		frame2.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame2.addText(new GameText(SpriteSheet.font_small, frame2_text1, Game.WIDTH/2 - (frame2_text1.length()/2)*6, frame2_start_y + frame2_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame2.addText(new GameText(SpriteSheet.font_small, frame2_text2, Game.WIDTH/2 - (frame2_text2.length()/2)*6, frame2_start_y + frame2_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		
		frame2.addImageComponent(new ImageComponent(Sprite.town1, Game.WIDTH/2 - 96 - Sprite.town1.WIDTH/2, frame2_start_y + frame2_y_spacing*1 + 32));
		frame2.addImageComponent(new ImageComponent(Sprite.town2, Game.WIDTH/2 - 32 - Sprite.town1.WIDTH/2, frame2_start_y + frame2_y_spacing*1 + 32));
		frame2.addImageComponent(new ImageComponent(Sprite.town3, Game.WIDTH/2 + 32 - Sprite.town1.WIDTH/2, frame2_start_y + frame2_y_spacing*1 + 32));
		frame2.addImageComponent(new ImageComponent(Sprite.town4, Game.WIDTH/2 + 96 - Sprite.town1.WIDTH/2, frame2_start_y + frame2_y_spacing*1 + 32));
		
		
		// Frame 3
		GraphicsInterface frame3 = new GraphicsInterface();
		
		String frame3_text1 = "The base damage of a tornado (visible in the shop screen) determines how much is done to a city.";
		String frame3_text2 = "Damage weakens over time and with distance away from the center of the tornado.";
		
		int frame3_start_y = 150, frame3_y_spacing = 20;
		
		frame3.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame3.addText(new GameText(SpriteSheet.font_small, frame3_text1, Game.WIDTH/2 - (frame3_text1.length()/2)*6, frame3_start_y + frame3_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame3.addText(new GameText(SpriteSheet.font_small, frame3_text2, Game.WIDTH/2 - (frame3_text2.length()/2)*6, frame3_start_y + frame3_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		
		
		// Frame 4
		GraphicsInterface frame4 = new GraphicsInterface();
		
		String frame4_text1 = "Tornadoes created closer to the storm god are more powerful.";
		String frame4_text2 = "Don't create tornadoes too far away!";
		
		int frame4_start_y = 150, frame4_y_spacing = 20;
		
		frame4.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame4.addText(new GameText(SpriteSheet.font_small, frame4_text1, Game.WIDTH/2 - (frame4_text1.length()/2)*6, frame4_start_y + frame4_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame4.addText(new GameText(SpriteSheet.font_small, frame4_text2, Game.WIDTH/2 - (frame4_text2.length()/2)*6, frame4_start_y + frame4_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		
		
		// Frame 5
		GraphicsInterface frame5 = new GraphicsInterface();
		
		String frame5_text1 = "Each tornado requires a certain amount of energy to create.";
		String frame5_text2 = "Energy regenerates slowly, so use it wisely.";
		
		int frame5_start_y = 150, frame5_y_spacing = 20;
		
		frame5.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame5.addText(new GameText(SpriteSheet.font_small, frame5_text1, Game.WIDTH/2 - (frame5_text1.length()/2)*6, frame5_start_y + frame5_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame5.addText(new GameText(SpriteSheet.font_small, frame5_text2, Game.WIDTH/2 - (frame5_text2.length()/2)*6, frame5_start_y + frame5_y_spacing*1, 0xFFFFFFFF, 0, 0, false));

		
		// Frame 6
		GraphicsInterface frame6 = new GraphicsInterface();
		
		String frame6_alert = "New!";
		
		String frame6_text1 = "Tornadoes have a small chance to set a town ablaze, dealing damage over time.";
		String frame6_text2 = "Fire can spread to nearby towns, causing a devastating chain reaction!";
		
		int frame6_start_y = 150, frame6_y_spacing = 20;
		
		frame6.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame6.addText(new GameText(SpriteSheet.font_small, frame6_alert, Game.WIDTH/2 - (frame6_alert.length()/2)*6, frame6_start_y - 40, 0xFF00FF21, 0, 0, false));
		frame6.addText(new GameText(SpriteSheet.font_small, frame6_text1, Game.WIDTH/2 - (frame6_text1.length()/2)*6, frame6_start_y + frame6_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame6.addText(new GameText(SpriteSheet.font_small, frame6_text2, Game.WIDTH/2 - (frame6_text2.length()/2)*6, frame6_start_y + frame6_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		
		frame6.addImageComponent(new ImageComponent(new Sprite(SpriteSheet.towns,1,0), Game.WIDTH/2 - 96 - Sprite.town1.WIDTH/2, frame6_start_y + frame6_y_spacing*1 + 32)); // Town 1 burning
		frame6.addImageComponent(new ImageComponent(new Sprite(SpriteSheet.towns,2,0), Game.WIDTH/2 - 32 - Sprite.town1.WIDTH/2, frame6_start_y + frame6_y_spacing*1 + 32)); // Town 2 burning
		frame6.addImageComponent(new ImageComponent(new Sprite(SpriteSheet.towns,3,0), Game.WIDTH/2 + 32 - Sprite.town1.WIDTH/2, frame6_start_y + frame6_y_spacing*1 + 32)); // Town 3 burning
		frame6.addImageComponent(new ImageComponent(new Sprite(SpriteSheet.towns,4,0), Game.WIDTH/2 + 96 - Sprite.town1.WIDTH/2, frame6_start_y + frame6_y_spacing*1 + 32)); // Town 4 burning
		
		
		// Frame 7
		GraphicsInterface frame7 = new GraphicsInterface();
		
		String frame7_text1 = "The game is won when all cities are destroyed and lost when too many fill the screen.";
		String frame7_text2 = "Small towns are worth as much as larger cities, so distribute your attention wisely.";
		String frame7_text3 = "Good luck!";
		
		int frame7_start_y = 150, frame7_y_spacing = 20;
		
		frame7.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame7.addText(new GameText(SpriteSheet.font_small, frame7_text1, Game.WIDTH/2 - (frame7_text1.length()/2)*6, frame7_start_y + frame7_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame7.addText(new GameText(SpriteSheet.font_small, frame7_text2, Game.WIDTH/2 - (frame7_text2.length()/2)*6, frame7_start_y + frame7_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		frame7.addText(new GameText(SpriteSheet.font_small, frame7_text3, Game.WIDTH/2 - (frame7_text3.length()/2)*6, frame7_start_y + frame7_y_spacing*1 + 100, 0xFFFFFFFF, 0, 0, false));
		
		
		//Frame 8
		
		GraphicsInterface frame8 = new GraphicsInterface();
		String frame8_text1 = "Controls:";
		String frame8_text2 = "Mouse: Click and drag to create selected tornado.";
		String frame8_text3 = "Number Keys (1-5): Select tornado (EF1-EF5 respectively)";
		String frame8_text4 = "Escape (esc): Open upgrade menu.";
		
		int frame8_start_y = 50, frame8_y_spacing = 20;
		
		frame8.addBackgroundComponent(new BackgroundComponent(BACKGROUND_COLOR, 0, 0, Game.WIDTH, Game.HEIGHT));
		frame8.addText(new GameText(SpriteSheet.font_small, frame8_text1, Game.WIDTH/5, frame8_start_y + frame8_y_spacing*0, 0xFFFFFFFF, 0, 0, false));
		frame8.addText(new GameText(SpriteSheet.font_small, frame8_text2, Game.WIDTH/5 + 25, frame8_start_y + frame8_y_spacing*1, 0xFFFFFFFF, 0, 0, false));
		frame8.addText(new GameText(SpriteSheet.font_small, frame8_text3, Game.WIDTH/5 + 25, frame8_start_y + frame8_y_spacing*2, 0xFFFFFFFF, 0, 0, false));
		frame8.addText(new GameText(SpriteSheet.font_small, frame8_text4, Game.WIDTH/5 + 25, frame8_start_y + frame8_y_spacing*3, 0xFFFFFFFF, 0, 0, false));
		frame8.addImageComponent(new ImageComponent(new Sprite(SpriteSheet.attackIntro), Game.WIDTH/2 - SpriteSheet.attackIntro.WIDTH/2, frame8_start_y + frame8_y_spacing*3 + 30));
		
		
		introFrames.add(frame0);
		introFrames.add(frame1);
		introFrames.add(frame2);
		introFrames.add(frame3);
		introFrames.add(frame4);
		introFrames.add(frame5);
		introFrames.add(frame6);
		introFrames.add(frame7);
		introFrames.add(frame8);
	}
	
	
	
	public void render(Screen screen)
	{
		if(intro)
		{
			renderIntroduction(screen);
		}
		else
		{	
			for(int y = 0; y < screen.getHeight(); y += 32) // Render grass background
			{
				for(int x = 0; x < screen.getWidth(); x += 32)
				{
					screen.render(Sprite.grass, x, y);
				}
			}
			
			for(Town town : towns)
				town.render(screen);
			
			for(Tornado t : tornadoes)
				t.render(screen);
			
			player.render(screen);
			
			// Update cool-down
			final int R = 150;
			final int G = -50;
			final int B = -50;
			
			userHud.getSelectionComponent(0).setFilled(R, G, B, Screen.Direction.SOUTH, (float)((ef1_timer*1.0)/(TornadoStats.EF1_COOLDOWN + shopData.EF1_COOLDOWN_ADDITION * shopData.ef1_cooldown_upgrades)));
			userHud.getSelectionComponent(1).setFilled(R, G, B, Screen.Direction.SOUTH, (float)((ef2_timer*1.0)/(TornadoStats.EF2_COOLDOWN + shopData.EF2_COOLDOWN_ADDITION * shopData.ef2_cooldown_upgrades)));
			userHud.getSelectionComponent(2).setFilled(R, G, B, Screen.Direction.SOUTH, (float)((ef3_timer*1.0)/(TornadoStats.EF3_COOLDOWN + shopData.EF3_COOLDOWN_ADDITION * shopData.ef3_cooldown_upgrades)));
			userHud.getSelectionComponent(3).setFilled(R, G, B, Screen.Direction.SOUTH, (float)((ef4_timer*1.0)/(TornadoStats.EF4_COOLDOWN + shopData.EF4_COOLDOWN_ADDITION * shopData.ef4_cooldown_upgrades)));
			userHud.getSelectionComponent(4).setFilled(R, G, B, Screen.Direction.SOUTH, (float)((ef5_timer*1.0)/(TornadoStats.EF5_COOLDOWN + shopData.EF5_COOLDOWN_ADDITION * shopData.ef5_cooldown_upgrades)));
			userHud.render(screen);
			
			energyRatioText.render(screen);
			moneyText.render(screen);
			
			if(difficultyAlertTimer > 0)
				difficultyAlert.render(screen);
			
			timerText.render(screen);
			
			if(shopActivated) // Rendered last; shop renders over all
				shop.render(screen);
		}
	}
	
	public void tick()
	{
		if(intro)
		{
			tickIntroduction();
		}
		else if(!hasWon && !hasLost) // Play game normally
		{	
			if(!shopActivated)
			{
				tickGame();
				
				if(shopTimer < SHOP_TIMER_MAX)
					shopTimer++;
				
				if(Keyboard.key_esc && shopTimer == SHOP_TIMER_MAX)
				{
					shopActivated = true;
					shop.refresh();
					shopTimer = 0;
				}
			}
			else
			{
				shop.tick();
				
				if(shopTimer < SHOP_TIMER_MAX)
					shopTimer++;
				
				if(Keyboard.key_esc && shopTimer == SHOP_TIMER_MAX)
				{
					shopActivated = false;
					shopTimer = 0;
				}
			}
			
			moneyText.setText("Money: " + (int)playerData.getMoney());
			moneyText.setX(Game.WIDTH/2 - moneyText.getWidth()/2);
		}
		else if(hasWon) // Win
		{
			if(!winDisplayAdded)
			{
				userHud.addImageComponent(new ImageComponent(Sprite.victory_message, Game.WIDTH/2 - 150, Game.HEIGHT/2 - 50));
				winDisplayAdded = true;
			}
		}
		else // Lose
		{
			if(!winDisplayAdded)
			{
				userHud.addImageComponent(new ImageComponent(Sprite.defeat_message, Game.WIDTH/2 - 150, Game.HEIGHT/2 - 50));
				winDisplayAdded = true;
			}
		}
	}
	
	private void tickGame()
	{
		// Tick game mechanics
		
		time++;
		timerText.setText("Time: " + ((int)(time/Game.TPS)) + " seconds");
		
		player.tick();
		playerData.tick();
		tickTornadoTimers();
		tickDifficulty();
		
		for(int i = 0; i < towns.size(); i++) // Tick towns
		{
			Town town = towns.get(i);
			
			town.tick();
			
			// Spawn new Town
			Town newTown = town.getNewTown();

			if(newTown != null)
				towns.add(newTown);
			
			// Remove destroyed towns
			if(town.isRemoved())
				towns.remove(i);
			
			if(town.isBurning())
				playerData.addMoney( ((town.getTownLevel().GROWTH_SPEED * Town.burn_damage_multiplier)/2) * (playerData.getMoneyGainPercent()/100.0) ); 
		}
		
		
		for(int i = 0; i < tornadoes.size(); i++) // Tick tornadoes
		{
			Tornado t = tornadoes.get(i);
			
			t.tick();
			
			// Damage towns
			Town[] townsToDamage = findTown((int)t.getX(), (int)t.getY(), (int)t.getRange(), 16, 16);
			
			if(townsToDamage.length != 0)
			{
				for(Town town : townsToDamage)
				{
					double damage = t.getDamage(town.getX(), town.getY());
					playerData.addMoney( (damage/2.0) * (playerData.getMoneyGainPercent()/100.0) ); // Half the damage is added to money multiplied by upgraded percentage decimal
					town.damage(damage);
				}
			}
			
			if(t.isRemoved())
			{
				tornadoes.remove(i);
			}
		}
		
		// Tick player actions
		tickTornadoAddition();
		
		// Hud
		userHud.tick();
		userHud.getFillBar(0).setAmountFilled(playerData.getEnergyRatio()); // Update energy bar
		energyRatioText.setText("" + (int)playerData.getEnergy() + "/" + (int)playerData.getMaxEnergy());
		energyRatioText.setX(Game.WIDTH/2 - energyRatioText.getWidth()/2);
		
	
		// Add random towns at random intervals
		addRandomVillage();
		
		// Check win conditions
		if(towns.size() == 0)
			hasWon = true;
		
		if(findNumberOfTowns(Game.WIDTH/2, Game.HEIGHT/2, 32, 16, 16) > 0)
		{
			hasLost = true;
		}
		
		if(towns.size() >= MAXIMUM_TOWNS_TO_LOSS)
			hasLost = true;
	}
	

	private void tickTornadoTimers()
	{
		// Ticks the timers for tornado timers
		
		if(ef1_timer != 0)
			ef1_timer--;
		
		if(ef2_timer != 0)
			ef2_timer--;
		
		if(ef3_timer != 0)
			ef3_timer--;
		
		if(ef4_timer != 0)
			ef4_timer--;
		
		if(ef5_timer != 0)
			ef5_timer--;
	}
	
	private void tickTornadoAddition()
	{
		// Handles the player addition of tornadoes
		
		if(Mouse.getButton() == 1)
		{
			if(t_mouseX == -1 || t_mouseY == -1) // if the player has not clicked previously
			{
				t_mouseX = Mouse.getScaledX();
				t_mouseY = Mouse.getScaledY();
			}
			else // Player has previously clicked
			{
				if((t_mouseX != Mouse.getScaledX() || t_mouseY != Mouse.getScaledY()) && !tornadoReady) // If the player has clicked and dragged
					tornadoReady = true;
			}
		}
		else
		{
			if(tornadoReady)
			{
				t_radian_angle = GameMathUtils.getRadians(t_mouseX, t_mouseY, Mouse.getScaledX(), Mouse.getScaledY());
			
				addTornado();
				
				tornadoReady = false;
				t_radian_angle = 0.0;
			}
			
			t_mouseX = -1.0;
			t_mouseY = -1.0;
		}
	}
	
	private void addTornado()
	{
		// Handles addition of tornadoes to a level
		
		int tornadoLevel = userHud.getSelectedComponent()+1;
		double playerEnergy = playerData.getEnergy();
		
		Tornado t = null;

		double[] vectorComponent = GameMathUtils.getVectorComponents(1.0, t_radian_angle);
		
		int distanceFromCenter = (int) GameMathUtils.getDistance((int)Mouse.getScaledX(), (int)Mouse.getScaledY(), (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
		double damageReductionPercent = (distanceFromCenter * TornadoStats.MaxDamageReduction_fromCenter) / (Game.WIDTH/2); // Damage % lost based off of distance from center
		
		if(tornadoLevel == 1 && playerEnergy >= TornadoStats.EF1_ENERGY + shopData.EF1_ENERGY_ADDITION * shopData.ef1_energy_upgrades && ef1_timer == 0)
		{
			ef1_timer = TornadoStats.EF1_COOLDOWN + shopData.EF1_COOLDOWN_ADDITION * shopData.ef1_cooldown_upgrades;
			playerData.addEnergy(-(TornadoStats.EF1_ENERGY + shopData.EF1_ENERGY_ADDITION * shopData.ef1_energy_upgrades));
			
			double xMotion = vectorComponent[0] * TornadoStats.EF1_SPEED;
			double yMotion = vectorComponent[1] * TornadoStats.EF1_SPEED;
			
			double damage = (TornadoStats.EF1_DAMAGE + shopData.EF1_DAMAGE_ADDITION * shopData.ef1_damage_upgrades) - 
					((TornadoStats.EF1_DAMAGE + shopData.EF1_DAMAGE_ADDITION * shopData.ef1_damage_upgrades)*damageReductionPercent);
			
			double range = (TornadoStats.EF1_RANGE + shopData.EF1_RANGE_ADDITION * shopData.ef1_range_upgrades);
			
			t = new Tornado(1, (int)Mouse.getScaledX(), (int)Mouse.getScaledY(), xMotion, yMotion, damage, range);
		}
		else if(tornadoLevel == 2 && playerEnergy >= TornadoStats.EF2_ENERGY + shopData.EF2_ENERGY_ADDITION * shopData.ef2_energy_upgrades && ef2_timer == 0)
		{
			ef2_timer = TornadoStats.EF2_COOLDOWN + shopData.EF2_COOLDOWN_ADDITION * shopData.ef2_cooldown_upgrades;
			playerData.addEnergy(-(TornadoStats.EF2_ENERGY + shopData.EF2_ENERGY_ADDITION * shopData.ef2_energy_upgrades));
			
			double xMotion = vectorComponent[0] * TornadoStats.EF2_SPEED;
			double yMotion = vectorComponent[1] * TornadoStats.EF2_SPEED;
			
			double damage = (TornadoStats.EF2_DAMAGE + shopData.EF2_DAMAGE_ADDITION * shopData.ef2_damage_upgrades) - 
					((TornadoStats.EF2_DAMAGE + shopData.EF2_DAMAGE_ADDITION * shopData.ef2_damage_upgrades)*damageReductionPercent);
			
			double range = (TornadoStats.EF2_RANGE + shopData.EF2_RANGE_ADDITION * shopData.ef2_range_upgrades);
			
			t = new Tornado(2, (int)Mouse.getScaledX(), (int)Mouse.getScaledY(), xMotion, yMotion, damage, range);
		}
		else if(tornadoLevel == 3 && playerEnergy >= TornadoStats.EF3_ENERGY + shopData.EF3_ENERGY_ADDITION * shopData.ef3_energy_upgrades && ef3_timer == 0)
		{
			ef3_timer = TornadoStats.EF3_COOLDOWN + shopData.EF3_COOLDOWN_ADDITION * shopData.ef3_cooldown_upgrades;
			playerData.addEnergy(-(TornadoStats.EF3_ENERGY + shopData.EF3_ENERGY_ADDITION * shopData.ef3_energy_upgrades));
			
			double xMotion = vectorComponent[0] * TornadoStats.EF3_SPEED;
			double yMotion = vectorComponent[1] * TornadoStats.EF3_SPEED;
			
			double damage = (TornadoStats.EF3_DAMAGE + shopData.EF3_DAMAGE_ADDITION * shopData.ef3_damage_upgrades) - 
					((TornadoStats.EF3_DAMAGE + shopData.EF3_DAMAGE_ADDITION * shopData.ef3_damage_upgrades)*damageReductionPercent);
			
			double range = (TornadoStats.EF3_RANGE + shopData.EF3_RANGE_ADDITION * shopData.ef3_range_upgrades);
			
			t = new Tornado(3, (int)Mouse.getScaledX(), (int)Mouse.getScaledY(), xMotion, yMotion, damage, range);
		}
		else if(tornadoLevel == 4 && playerEnergy >= TornadoStats.EF4_ENERGY + shopData.EF4_ENERGY_ADDITION * shopData.ef4_energy_upgrades && ef4_timer == 0)
		{
			ef4_timer = TornadoStats.EF4_COOLDOWN + shopData.EF4_COOLDOWN_ADDITION * shopData.ef4_cooldown_upgrades;
			playerData.addEnergy(-(TornadoStats.EF4_ENERGY + shopData.EF4_ENERGY_ADDITION * shopData.ef4_energy_upgrades));
			
			double xMotion = vectorComponent[0] * TornadoStats.EF4_SPEED;
			double yMotion = vectorComponent[1] * TornadoStats.EF4_SPEED;
			
			double damage = (TornadoStats.EF4_DAMAGE + shopData.EF4_DAMAGE_ADDITION * shopData.ef4_damage_upgrades) - 
					((TornadoStats.EF4_DAMAGE + shopData.EF4_DAMAGE_ADDITION * shopData.ef4_damage_upgrades)*damageReductionPercent);
			
			double range = (TornadoStats.EF4_RANGE + shopData.EF4_RANGE_ADDITION * shopData.ef4_range_upgrades);
			
			t = new Tornado(4, (int)Mouse.getScaledX(), (int)Mouse.getScaledY(), xMotion, yMotion, damage, range);
		}
		else if(tornadoLevel == 5 && playerEnergy >= TornadoStats.EF5_ENERGY + shopData.EF5_ENERGY_ADDITION * shopData.ef5_energy_upgrades && ef5_timer == 0)
		{
			ef5_timer = TornadoStats.EF5_COOLDOWN + shopData.EF5_COOLDOWN_ADDITION * shopData.ef5_cooldown_upgrades;
			playerData.addEnergy(-(TornadoStats.EF5_ENERGY + shopData.EF5_ENERGY_ADDITION * shopData.ef5_energy_upgrades));
			
			double xMotion = vectorComponent[0] * TornadoStats.EF5_SPEED;
			double yMotion = vectorComponent[1] * TornadoStats.EF5_SPEED;
			
			double damage = (TornadoStats.EF5_DAMAGE + shopData.EF5_DAMAGE_ADDITION * shopData.ef5_damage_upgrades) - 
					((TornadoStats.EF5_DAMAGE + shopData.EF5_DAMAGE_ADDITION * shopData.ef5_damage_upgrades)*damageReductionPercent);
			
			double range = (TornadoStats.EF5_RANGE + shopData.EF5_RANGE_ADDITION * shopData.ef5_range_upgrades);
			
			t = new Tornado(5, (int)Mouse.getScaledX(), (int)Mouse.getScaledY(), xMotion, yMotion, damage, range);
		}
		
		if(t != null)
		{
			tornadoes.add(t);
			player.startAttack();
		}
	}
	
	private void tickDifficulty()
	{
		// Tick Difficulty (Increases with time)
		
		if(difficultyAlertTimer > 0)
			difficultyAlertTimer--;
		
		int newIncreaseUnits = time / (100 * Game.TPS); // time / (seconds * Game.TPS), where seconds is how often to increase difficulty
		if(newIncreaseUnits > increaseUnits) 
			difficultyAlertTimer = DIFFICULTY_ALERT_TIMER;
		increaseUnits = newIncreaseUnits;
		
		difficultyIncrease = 0;
		
		for(int i = 0; i < increaseUnits; i++) 
		{
			difficultyIncrease += DIFFICULTY_INCREASE_UNIT;
		}	
		
		if(difficultyIncrease > 0.9) // Difficulty Cap
			difficultyIncrease = 0.9;
	}
	
	public void addRandomVillage()
	{
		// Add random villages based on chance
		
		// Chance values:
		int smallCity = 600;
		int mediumCity = 2000;
		int largeCity = 5000;
		int veryLargeCity = 20000;
		
		// Factor in difficulty
		smallCity -= (smallCity * difficultyIncrease);
		mediumCity -= (mediumCity * difficultyIncrease);
		largeCity -= (largeCity * difficultyIncrease);
		veryLargeCity -= (veryLargeCity * difficultyIncrease);
		
		
		boolean villageAdded = true;
		
		// Small
		if(new Random().nextInt(smallCity) == 0)
			villageAdded = false;
		
		while(!villageAdded)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 75) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 50));
					villageAdded = true;
				}
			}
		}
		
		
		// Medium
		if(new Random().nextInt(mediumCity) == 0)
			villageAdded = false;
		
		while(!villageAdded)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 120) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 350));
					villageAdded = true;
				}
			}
		}
		
		
		// Large
		if(new Random().nextInt(largeCity) == 0)
			villageAdded = false;
		
		while(!villageAdded)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 150) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 3500));
					villageAdded = true;
				}
			}
		}
		
		// Very Large
		if(new Random().nextInt(veryLargeCity) == 0)
			villageAdded = false;
		
		while(!villageAdded)
		{
			int startX = new Random().nextInt(Game.WIDTH - 100) + 50;
			int startY = new Random().nextInt(Game.HEIGHT - 100) + 50;
			
			int distance = (int) GameMathUtils.getDistance(startX, startY, Game.WIDTH/2, Game.HEIGHT/2);
			
			if(distance > 200) // Make sure cities spawn a certain minimum distance away
			{
				if(findNumberOfTowns(startX, startY, Town.MINIMUM_SPAWN_DISTANCE) == 0) // Make sure city is not on top of a current population center
				{
					towns.add(new Town(startX, startY, this, 8000));
					villageAdded = true;
				}
			}
		}
	}
	
	public int findNumberOfTowns(int x, int y, int distance)
	{
		// Find the number of towns within a certain distance (x, y)
		
		int numberOfTowns = 0;
		
		for(Town town : towns)
		{
			int townX = town.getX();
			int townY = town.getY();
			
			if(GameMathUtils.getDistance(x, y, townX, townY) <= distance)
				numberOfTowns++;
		}
		
		return numberOfTowns;
	}
	
	public int findNumberOfTowns(int x, int y, int distance, int townXOffset, int townYOffset)
	{
		// Find the number of towns within a certain distance (x, y)
		// Town offsets to create a custom town center location
		
		int numberOfTowns = 0;
		
		for(Town town : towns)
		{
			int townX = town.getX() + townXOffset;
			int townY = town.getY() + townYOffset;
			
			if(GameMathUtils.getDistance(x, y, townX, townY) <= distance)
				numberOfTowns++;
		}
		
		return numberOfTowns;
	}
	
	
	public Town[] findTown(int x, int y, int distance, int townXOffset, int townYOffset)
	{
		// Find towns within a certain distance of (x, y)
		// int x, int y: Origin of search
		// int distance: Distance from (x,y) to end search
		// int townXOffset, int townYOffet: Offset for town (x, y) location (default: top left corner; 0,0)
		
		ArrayList<Town> t = new ArrayList<Town>();
		
		for(Town town : towns)
		{
			int townX = town.getX() + townXOffset;
			int townY = town.getY() + townYOffset;
			
			if(GameMathUtils.getDistance(x, y, townX, townY) <= distance)
				t.add(town);
		}
		
		Town[] returnedTowns = new Town[t.size()];
		
		for(int i = 0; i < t.size(); i++)
		{
			returnedTowns[i] = t.get(i);
		}
		
		return returnedTowns;
	}
	

	private void tickIntroduction()
	{
		// Ticks introduction activity
		
		if(currentIntroFrame < introFrames.size())
		{
			if(Mouse.getButton() == 1 && introFrameClickTimer == 0) // Go to next frame if the mouse is clicked
			{
				currentIntroFrame++;
				introFrameClickTimer = INTRO_FRAME_CLICK_TIMER_MAX;
			}
			
			if(introFrameClickTimer > 0)
				introFrameClickTimer--;
			
		}
		else
		{
			intro = false;
		}
	}
	
	private void renderIntroduction(Screen screen)
	{
		// Renders the current introduction frame
		
		if(currentIntroFrame < introFrames.size())
		{
			introFrames.get(currentIntroFrame).render(screen);
			
			String continueText = "(Click anywhere to continue)";
			new GameText(SpriteSheet.font_small, continueText, Game.WIDTH/2 - (continueText.length()/2)*6, Game.HEIGHT - 50, 0xFFFFE97F, 0, 0, false).render(screen);
			String frameText = "(" +  (currentIntroFrame+1) + "/" + introFrames.size() + ")";
			new GameText(SpriteSheet.font_small, frameText, Game.WIDTH/2 - (frameText.length()/2)*6, Game.HEIGHT - 35, 0xFFFFE97F, 0, 0, false).render(screen);
		}
	}
	
	public SaveFile saveToFile()
	{
		int[] efTimers = { ef1_timer, ef2_timer, ef3_timer, ef4_timer, ef5_timer };
		
		ArrayList<TownSave> townSaves = new ArrayList<TownSave>();
		ArrayList<TornadoSave> tornadoSaves = new ArrayList<TornadoSave>();
		
		for(int i = 0; i < towns.size(); i++)
		{
			townSaves.add(towns.get(i).saveToFile());
		}
		
		for(int i = 0; i < tornadoes.size(); i++)
		{
			tornadoSaves.add(tornadoes.get(i).saveToFile());
		}
		
		return new SaveFile(townSaves, tornadoSaves, playerData, shopData, efTimers, time);
	}
	
	public void loadSaveFile(SaveFile save)
	{
		ArrayList<TownSave> townSaves = save.getTowns();
		ArrayList<TornadoSave> tornadoSaves = save.getTornadoes();
		
		// Towns
		towns.clear();
		for(int i = 0; i < townSaves.size(); i++)
		{
			towns.add(new Town(townSaves.get(i), this));
		}
		
		// Tornadoes
		tornadoes.clear();
		for(int i = 0; i < tornadoSaves.size(); i++)
		{
			tornadoes.add(new Tornado(tornadoSaves.get(i)));
		}

		playerData = save.getPlayerData();
		shopData = save.getShopData();
		
		int[] efTimers = save.getEfTimers();
		
		ef1_timer = efTimers[0];
		ef2_timer = efTimers[1];
		ef3_timer = efTimers[2];
		ef4_timer = efTimers[3];
		ef5_timer = efTimers[4];
		
		shop = new Shop(playerData, shopData, this);
		
		time = save.getTime();
	}
	
	public ShopData getShopData()
	{
		return shopData;
	}
}

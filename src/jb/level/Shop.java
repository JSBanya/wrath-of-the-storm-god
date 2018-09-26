package jb.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jb.entities.TornadoStats;
import jb.entities.Town;
import jb.graphics.GameText;
import jb.graphics.Screen;
import jb.graphics.Sprite;
import jb.graphics.SpriteSheet;
import jb.gui.BackgroundComponent;
import jb.gui.ButtonComponent;
import jb.gui.GraphicsInterface;
import jb.main.Game;
import jb.main.Mouse;
import jb.util.SaveFile;

public class Shop
{
	/*
 		Handles shop activities
	 */
	
	private Level level;
	
	private DecimalFormat df;
	private GraphicsInterface shopGui;
	private PlayerData playerData;
	private ShopData shopData;
	
	private final boolean TEXT_SHADOW = false;
	
	// Save/Load game utils
	private boolean mouseClicked;
	private String saveText = "Save Game";
	private String loadText = "Load Game";
	private int saveX = Game.WIDTH-65, saveY = Game.HEIGHT-22;
	
	
	public Shop(PlayerData playerData, ShopData shopData, Level level)
	{
		this.playerData = playerData;
		this.shopData = shopData;
		this.level = level;
		
		df = new DecimalFormat("0.0");
		
		refresh();
	}
	
	public void tick()
	{
		// Tick shop mechanics/interface
		shopGui.tick();
		
		// ------- EF1
		if(shopGui.getButtonComponent(0).hasBeenPressed())
		{
			// Upgrade damage
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF1_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_damage_upgrades)) && (shopData.ef1_damage_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF1_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_damage_upgrades)));
				shopData.ef1_damage_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(1).hasBeenPressed())
		{
			// Upgrade range
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF1_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_range_upgrades)) && (shopData.ef1_range_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF1_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_range_upgrades)));
				shopData.ef1_range_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(2).hasBeenPressed())
		{
			// Upgrade energy
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF1_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_energy_upgrades)) && (shopData.ef1_energy_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF1_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_energy_upgrades)));
				shopData.ef1_energy_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(3).hasBeenPressed())
		{
			// Upgrade cooldown
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF1_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_cooldown_upgrades)) && (shopData.ef1_cooldown_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF1_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_cooldown_upgrades)));
				shopData.ef1_cooldown_upgrades++;
				refresh();
			}
		}
		
		// ------- EF2
		else if(shopGui.getButtonComponent(4).hasBeenPressed())
		{
			// Upgrade damage
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF2_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_damage_upgrades)) && (shopData.ef2_damage_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF2_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_damage_upgrades)));
				shopData.ef2_damage_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(5).hasBeenPressed())
		{
			// Upgrade range
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF2_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_range_upgrades)) && (shopData.ef2_range_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF2_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_range_upgrades)));
				shopData.ef2_range_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(6).hasBeenPressed())
		{
			// Upgrade energy
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF2_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_energy_upgrades)) && (shopData.ef2_energy_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF2_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_energy_upgrades)));
				shopData.ef2_energy_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(7).hasBeenPressed())
		{
			// Upgrade cooldown
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF2_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_cooldown_upgrades)) && (shopData.ef2_cooldown_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF2_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_cooldown_upgrades)));
				shopData.ef2_cooldown_upgrades++;
				refresh();
			}
		}
		
		// ------- EF3
		else if(shopGui.getButtonComponent(8).hasBeenPressed())
		{
			// Upgrade damage
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF3_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_damage_upgrades)) && (shopData.ef3_damage_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF3_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_damage_upgrades)));
				shopData.ef3_damage_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(9).hasBeenPressed())
		{
			// Upgrade range
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF3_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_range_upgrades)) && (shopData.ef3_range_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF3_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_range_upgrades)));
				shopData.ef3_range_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(10).hasBeenPressed())
		{
			// Upgrade energy
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF3_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_energy_upgrades)) && (shopData.ef3_energy_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF3_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_energy_upgrades)));
				shopData.ef3_energy_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(11).hasBeenPressed())
		{
			// Upgrade cooldown
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF3_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_cooldown_upgrades)) && (shopData.ef3_cooldown_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF3_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_cooldown_upgrades)));
				shopData.ef3_cooldown_upgrades++;
				refresh();
			}
		}
		
		// ------- EF4
		else if(shopGui.getButtonComponent(12).hasBeenPressed())
		{
			// Upgrade damage
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF4_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_damage_upgrades)) && (shopData.ef4_damage_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF4_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_damage_upgrades)));
				shopData.ef4_damage_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(13).hasBeenPressed())
		{
			// Upgrade range
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF4_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_range_upgrades)) && (shopData.ef4_range_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF4_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_range_upgrades)));
				shopData.ef4_range_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(14).hasBeenPressed())
		{
			// Upgrade energy
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF4_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_energy_upgrades)) && (shopData.ef4_energy_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF4_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_energy_upgrades)));
				shopData.ef4_energy_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(15).hasBeenPressed())
		{
			// Upgrade cooldown
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF4_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_cooldown_upgrades)) && (shopData.ef4_cooldown_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF4_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_cooldown_upgrades)));
				shopData.ef4_cooldown_upgrades++;
				refresh();
			}
		}
		
		// ------- EF5
		else if(shopGui.getButtonComponent(16).hasBeenPressed())
		{
			// Upgrade damage
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF5_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_damage_upgrades)) && (shopData.ef5_damage_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF5_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_damage_upgrades)));
				shopData.ef5_damage_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(17).hasBeenPressed())
		{
			// Upgrade range
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF5_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_range_upgrades)) && (shopData.ef5_range_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF5_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_range_upgrades)));
				shopData.ef5_range_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(18).hasBeenPressed())
		{
			// Upgrade energy
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF5_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_energy_upgrades)) && (shopData.ef5_energy_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF5_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_energy_upgrades)));
				shopData.ef5_energy_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(19).hasBeenPressed())
		{
			// Upgrade cooldown
			
			if( (int)playerData.getMoney() >= (int)(shopData.EF5_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_cooldown_upgrades)) && (shopData.ef5_cooldown_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.EF5_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_cooldown_upgrades)));
				shopData.ef5_cooldown_upgrades++;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(20).hasBeenPressed())
		{
			// Upgrade maximum energy
			
			if( (int)playerData.getMoney() >= (int)(shopData.MAXIMUM_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.maximum_energy_upgrades)) && (shopData.maximum_energy_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.MAXIMUM_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.maximum_energy_upgrades)));
				shopData.maximum_energy_upgrades++;
				playerData.setEnergyMax(playerData.getMaxEnergy() + shopData.MAXIMUM_ENERGY_ADDITION);
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(21).hasBeenPressed())
		{
			// Upgrade energy regen
			
			if( (int)playerData.getMoney() >= (int)(shopData.ENERGY_REGEN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.energy_regen_upgrades)) && (shopData.energy_regen_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.ENERGY_REGEN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.energy_regen_upgrades)));
				shopData.energy_regen_upgrades++;
				playerData.setEnergyRegen(playerData.getEnergyRegen() + shopData.ENERGY_REGEN_ADDITION);
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(22).hasBeenPressed())
		{
			// Upgrade Money Gain
			
			if( (int)playerData.getMoney() >= (int)(shopData.MONEY_GAIN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.money_gain_upgrades)) && (shopData.money_gain_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.MONEY_GAIN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.money_gain_upgrades)));
				shopData.money_gain_upgrades++;
				playerData.setMoneyGainPercent(playerData.getMoneyGainPercent() + shopData.MONEY_GAIN_ADDITION);
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(23).hasBeenPressed())
		{
			// Upgrade Fire Chance
			
			if( (int)playerData.getMoney() >= (int)(shopData.BURN_CHANCE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.burn_chance_upgrades)) && (shopData.burn_chance_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.BURN_CHANCE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.burn_chance_upgrades)));
				shopData.burn_chance_upgrades++;
				Town.burn_chance += shopData.BURN_CHANCE_ADDITION;
				refresh();
			}
		}
		else if(shopGui.getButtonComponent(24).hasBeenPressed())
		{
			// Upgrade Fire Damage Multiplier
			
			if( (int)playerData.getMoney() >= (int)(shopData.BURN_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.burn_damage_upgrades)) && (shopData.burn_damage_upgrades < shopData.MAX_UPGRADES))
			{
				playerData.addMoney(-(shopData.BURN_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.burn_damage_upgrades)));
				shopData.burn_damage_upgrades++;
				Town.burn_damage_multiplier += shopData.BURN_DAMAGE_ADDITION;
				refresh();
			}
		}
		
		if(!mouseClicked && Mouse.getButton() == 1)
		{
			mouseClicked = true;
		}
		
		// Save/Load Game
		if(mouseClicked == true && Mouse.getButton() == 0)
		{
			mouseClicked = false;
			
			int mouseX = (int)Mouse.getScaledX();
			int mouseY = (int)Mouse.getScaledY();
			
			if(mouseX >= saveX && mouseX <= saveX + saveText.length() * SpriteSheet.font_small.SPRITE_SIZE
					&& mouseY >= saveY && mouseY <= saveY + SpriteSheet.font_small.SPRITE_SIZE) // Check bounds of save text
			{
				// Save game
				System.out.println("SAVE");
				saveGame();
			}
			
			if(mouseX >= saveX && mouseX <= saveX + loadText.length() * SpriteSheet.font_small.SPRITE_SIZE
					&& mouseY >= saveY + SpriteSheet.font_small.SPRITE_SIZE + 4 && mouseY <= saveY + 4 + 2*SpriteSheet.font_small.SPRITE_SIZE) // Check bounds of save text
			{
				// Load game
				System.out.println("LOAD");
				loadGame();
			}
		}
	}
	
	public void refresh()
	{
		// Initialize the Shop GUI
		shopGui = new GraphicsInterface();
		
		shopGui.addBackgroundComponent(new BackgroundComponent(Sprite.gui_shopBackground, Game.WIDTH/2 - 300, 25));
		
		
		final int COST_COLOR_ACTIVE = 0xFF00870D;
		final int COST_COLOR_INACTIVE = 0xFFA50000;
		final int COST_COLOR_MAX = 0xFFE0BB00;
		int costColorToUse = COST_COLOR_ACTIVE; // Changes for each cost text display based on availability of the purchase
		double currentPrice = 0;
		double currentMoney = playerData.getMoney();
		String costString = "";
		
		/*
		 * Page 1
		 */
		
		final int TITLE_X = 130;
		final int BUTTON_X = 135;
		final int INFO_X = 150;
		final int COST_X = 255;
		final int START_Y = 65;
		final int GROUP_SPACING = 64;
		final int TEXT_SPACING = 12;
		
		final int TITLE_COLOR = 0xFFFFFFFF;
		final int TEXT_COLOR = 0xFF383838;
		
		
		// EF 1
		shopGui.addText(new GameText(SpriteSheet.font_small, "EF 1", TITLE_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*0), TITLE_COLOR, 0, 0, true));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Damage: " + df.format(TornadoStats.EF1_DAMAGE + shopData.EF1_DAMAGE_ADDITION * shopData.ef1_damage_upgrades), INFO_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*1), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Range: " + df.format(TornadoStats.EF1_RANGE + shopData.EF1_RANGE_ADDITION * shopData.ef1_range_upgrades), INFO_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*2), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Energy: " + df.format(TornadoStats.EF1_ENERGY + shopData.EF1_ENERGY_ADDITION * shopData.ef1_energy_upgrades), INFO_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*3), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Cooldown: " + df.format(TornadoStats.EF1_COOLDOWN + shopData.EF1_COOLDOWN_ADDITION * shopData.ef1_cooldown_upgrades), INFO_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*4), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*1)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*2)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*3)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*4)));
		
		
		currentPrice = (shopData.EF1_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_damage_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
			
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef1_damage_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*1), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF1_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_range_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef1_range_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF1_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_energy_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef1_energy_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*3), costColorToUse, 0, 0, TEXT_SHADOW));

		
		currentPrice = (shopData.EF1_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef1_cooldown_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef1_cooldown_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*0 + TEXT_SPACING*4), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		// EF 2
		shopGui.addText(new GameText(SpriteSheet.font_small, "EF 2", TITLE_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*0), TITLE_COLOR, 0, 0, true));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Damage: " + df.format(TornadoStats.EF2_DAMAGE + shopData.EF2_DAMAGE_ADDITION * shopData.ef2_damage_upgrades), INFO_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*1), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Range: " + df.format(TornadoStats.EF2_RANGE + shopData.EF2_RANGE_ADDITION * shopData.ef2_range_upgrades), INFO_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*2), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Energy: " + df.format(TornadoStats.EF2_ENERGY + shopData.EF2_ENERGY_ADDITION * shopData.ef2_energy_upgrades), INFO_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*3), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Cooldown: " + df.format(TornadoStats.EF2_COOLDOWN + shopData.EF2_COOLDOWN_ADDITION * shopData.ef2_cooldown_upgrades), INFO_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*4), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*1)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*2)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*3)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*4)));
		
		
		currentPrice = (shopData.EF2_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_damage_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef2_damage_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*1), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF2_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_range_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef2_range_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF2_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_energy_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef2_energy_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*3), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF2_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef2_cooldown_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef2_cooldown_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*1 + TEXT_SPACING*4), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		// EF 3
		shopGui.addText(new GameText(SpriteSheet.font_small, "EF 3", TITLE_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*0), TITLE_COLOR, 0, 0, true));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Damage: " + df.format(TornadoStats.EF3_DAMAGE + shopData.EF3_DAMAGE_ADDITION * shopData.ef3_damage_upgrades), INFO_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*1), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Range: " + df.format(TornadoStats.EF3_RANGE + shopData.EF3_RANGE_ADDITION * shopData.ef3_range_upgrades), INFO_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*2), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Energy: " + df.format(TornadoStats.EF3_ENERGY + shopData.EF3_ENERGY_ADDITION * shopData.ef3_energy_upgrades), INFO_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*3), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Cooldown: " + df.format(TornadoStats.EF3_COOLDOWN + shopData.EF3_COOLDOWN_ADDITION * shopData.ef3_cooldown_upgrades), INFO_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*4), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*1)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*2)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*3)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*4)));
		
		
		currentPrice = (shopData.EF3_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_damage_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef3_damage_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*1), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF3_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_range_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef3_range_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF3_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_energy_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef3_energy_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*3), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF3_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef3_cooldown_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef3_cooldown_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*2 + TEXT_SPACING*4), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		// EF 4
		shopGui.addText(new GameText(SpriteSheet.font_small, "EF 4", TITLE_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*0), TITLE_COLOR, 0, 0, true));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Damage: " + df.format(TornadoStats.EF4_DAMAGE + shopData.EF4_DAMAGE_ADDITION * shopData.ef4_damage_upgrades), INFO_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*1), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Range: " + df.format(TornadoStats.EF4_RANGE + shopData.EF4_RANGE_ADDITION * shopData.ef4_range_upgrades), INFO_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*2), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Energy: " + df.format(TornadoStats.EF4_ENERGY + shopData.EF4_ENERGY_ADDITION * shopData.ef4_energy_upgrades), INFO_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*3), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Cooldown: " + df.format(TornadoStats.EF4_COOLDOWN + shopData.EF4_COOLDOWN_ADDITION * shopData.ef4_cooldown_upgrades), INFO_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*4), TEXT_COLOR, 0, 0, TEXT_SHADOW));
		
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*1)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*2)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*3)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*4)));
		
		
		currentPrice = (shopData.EF4_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_damage_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef4_damage_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*1), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF4_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_range_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef4_range_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF4_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_energy_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef4_energy_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*3), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF4_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef4_cooldown_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef4_cooldown_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X, (START_Y + GROUP_SPACING*3 + TEXT_SPACING*4), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		
		
		/*
		 * Page 2
		 */
		
		final int TITLE_X_2 = 363;
		final int BUTTON_X_2 = 375;
		final int INFO_X_2 = 388;
		final int COST_X_2 = 505;
		final int START_Y_2 = 67;
		final int GROUP_SPACING_2 = 64;
		final int TEXT_SPACING_2 = 12;
		
		final int titleColor_2 = 0xFFFFFFFF;
		final int textColor_2 = 0xFF383838;
		
		// EF 5
		shopGui.addText(new GameText(SpriteSheet.font_small, "EF 5", TITLE_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*0), titleColor_2, 0, 0, true));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Damage: " + df.format(TornadoStats.EF5_DAMAGE + shopData.EF5_DAMAGE_ADDITION * shopData.ef5_damage_upgrades), INFO_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*1), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Range: " + df.format(TornadoStats.EF5_RANGE + shopData.EF5_RANGE_ADDITION * shopData.ef5_range_upgrades), INFO_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*2), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Energy: " + df.format(TornadoStats.EF5_ENERGY + shopData.EF5_ENERGY_ADDITION * shopData.ef5_energy_upgrades), INFO_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*3), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addText(new GameText(SpriteSheet.font_small, "Cooldown: " + df.format(TornadoStats.EF5_COOLDOWN + shopData.EF5_COOLDOWN_ADDITION * shopData.ef5_cooldown_upgrades), INFO_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*4), textColor_2, 0, 0, TEXT_SHADOW));
		
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*1)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*2)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*3)));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*4)));
		
		
		currentPrice = (shopData.EF5_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_damage_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef5_damage_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*1), costColorToUse, 0, 0, TEXT_SHADOW));
			
		
		currentPrice = (shopData.EF5_RANGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_range_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef5_range_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF5_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_energy_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef5_energy_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*3), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		currentPrice = (shopData.EF5_COOLDOWN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.ef5_cooldown_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.ef5_cooldown_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*0 + TEXT_SPACING_2*4), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		
		shopGui.addText(new GameText(SpriteSheet.font_small, "Player", TITLE_X_2, (START_Y_2 + GROUP_SPACING_2*1 + TEXT_SPACING_2*0), 0xFFFFFFFF, 0, 0, true));
		
		// Maximum Energy
		shopGui.addText(new GameText(SpriteSheet.font_small, "Max Energy: " + df.format(playerData.getMaxEnergy()), INFO_X_2, (START_Y_2 + GROUP_SPACING_2*1 + TEXT_SPACING_2*1), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*1 + TEXT_SPACING_2*1)));
		
		
		currentPrice = (shopData.MAXIMUM_ENERGY_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.maximum_energy_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.maximum_energy_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*1 + TEXT_SPACING_2*1), costColorToUse, 0, 0, TEXT_SHADOW));
	
		// Energy Regeneration Speed
		shopGui.addText(new GameText(SpriteSheet.font_small, "Regeneration: " + df.format(playerData.getEnergyRegen()*60), INFO_X_2, (START_Y_2 + GROUP_SPACING_2 + 13 + TEXT_SPACING_2*1), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2 + 13 + TEXT_SPACING_2*1)));
		
		
		currentPrice = (shopData.ENERGY_REGEN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.energy_regen_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.energy_regen_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2 + 13 + TEXT_SPACING_2*1), costColorToUse, 0, 0, TEXT_SHADOW));
		
		// Energy Regeneration Speed
		shopGui.addText(new GameText(SpriteSheet.font_small, "Money Gain: " + playerData.getMoneyGainPercent() + "%", INFO_X_2, (START_Y_2 + GROUP_SPACING_2 + 13 + TEXT_SPACING_2*2), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2 + 13 + TEXT_SPACING_2*2)));
		
		
		currentPrice = (shopData.MONEY_GAIN_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.money_gain_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.money_gain_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2 + 13 + TEXT_SPACING_2*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		
		shopGui.addText(new GameText(SpriteSheet.font_small, "Fire", TITLE_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*0), 0xFFFFFFFF, 0, 0, true));
		
		shopGui.addText(new GameText(SpriteSheet.font_small, "Chance: 1/" + Town.burn_chance, INFO_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*1), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*1)));
		
		currentPrice = (shopData.BURN_CHANCE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.burn_chance_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.burn_chance_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*1), costColorToUse, 0, 0, TEXT_SHADOW));
		
		
		shopGui.addText(new GameText(SpriteSheet.font_small, "Damage Factor: " + Town.burn_damage_multiplier, INFO_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*2), textColor_2, 0, 0, TEXT_SHADOW));
		shopGui.addButtonComponent(new ButtonComponent(Sprite.gui_button, Sprite.gui_buttonPressed, BUTTON_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*2)));
		
		currentPrice = (shopData.BURN_DAMAGE_COST * Math.pow(shopData.COST_MULTIPLIER, shopData.burn_damage_upgrades));
		
		if(currentMoney >= currentPrice)
			costColorToUse = COST_COLOR_ACTIVE;
		else
			costColorToUse = COST_COLOR_INACTIVE;
		
		costString = "Cost: " + ((int)currentPrice);
		if(shopData.burn_damage_upgrades == shopData.MAX_UPGRADES)
		{
			costColorToUse = COST_COLOR_MAX;
			costString = "(MAX)";
		}
		
		shopGui.addText(new GameText(SpriteSheet.font_small, costString, COST_X_2, (START_Y_2 + GROUP_SPACING_2*2 + TEXT_SPACING_2*2), costColorToUse, 0, 0, TEXT_SHADOW));
		
		// Save/load
		shopGui.addText(new GameText(SpriteSheet.font_small, saveText, saveX, saveY, 0xFFFFFFFF, 0, 0, true));
		shopGui.addText(new GameText(SpriteSheet.font_small, loadText, saveX, saveY + SpriteSheet.font_small.SPRITE_SIZE + 3, 0xFFFFFFFF, 0, 0, true));
	}
	
	private void saveGame()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Ser Files", "ser");
		
		JFileChooser fileSave = new JFileChooser();
		fileSave.setFileFilter(filter);
		fileSave.setSelectedFile(new File("Storm_God_Save_" + Game.versionNumber.replace(".", "-") + "_" + dateFormat.format(cal.getTime()) + ".ser"));
		fileSave.showSaveDialog(new JFrame());
		File userFile = fileSave.getSelectedFile();
		
		String extension, path = userFile.getPath();
		
		if(path.contains("."))
			extension = path.substring(path.lastIndexOf("."));
		else
			extension = "";

		if(!extension.equals(".ser"))
		{
			  JOptionPane.showMessageDialog(null, "Unrecognized or invalid save file extension!", "Error", JOptionPane.ERROR_MESSAGE);
			  return;
		}
		
		try
		{
			FileOutputStream fileStream = new FileOutputStream(userFile);
			ObjectOutputStream out = new ObjectOutputStream(fileStream);
			out.writeObject(level.saveToFile());
			out.close();
			fileStream.close();
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "An error has occurred in saving!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void loadGame()
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Ser Files", "ser");
		
		JFileChooser fileSave = new JFileChooser();
		fileSave.setFileFilter(filter);
		fileSave.showSaveDialog(new JFrame());
		File userFile = fileSave.getSelectedFile();
		
		String extension, path = userFile.getPath();
		
		if(path.contains("."))
			extension = path.substring(path.lastIndexOf("."));
		else
			extension = "";

		if(!extension.equals(".ser"))
		{
			  JOptionPane.showMessageDialog(null, "Unrecognized or invalid save file extension!", "Error", JOptionPane.ERROR_MESSAGE);
			  return;
		}	
		
		try
		{
			FileInputStream fileIn = new FileInputStream(userFile);
			ObjectInputStream is = new ObjectInputStream(fileIn);
			SaveFile save = (SaveFile) is.readObject();
			is.close();
			
			level.loadSaveFile(save);
		} 
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "An error has occurred in loading!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void render(Screen screen)
	{
		shopGui.render(screen);
	}
}

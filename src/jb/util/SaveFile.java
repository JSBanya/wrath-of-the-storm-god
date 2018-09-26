package jb.util;

import java.io.Serializable;
import java.util.ArrayList;
import jb.level.PlayerData;
import jb.level.ShopData;
import jb.main.Game;

public class SaveFile implements Serializable
{
	/*
	 * An object used in saving and loading games
	 */
	
	private static final long serialVersionUID = Game.serialVersionUID;
	
	private ArrayList<TownSave> towns;
	private ArrayList<TornadoSave> tornadoes;
	private PlayerData playerData;
	private ShopData shopData;
	private int[] efTimers;
	private int time;
	
	public SaveFile(ArrayList<TownSave> towns, ArrayList<TornadoSave> tornadoes, PlayerData playerData, ShopData shopData, int[] efTimers, int time)
	{
		this.towns = towns;
		this.tornadoes = tornadoes;
		this.playerData = playerData;
		this.shopData = shopData;
		this.efTimers = efTimers;
		this.time = time;
	}

	public ArrayList<TownSave> getTowns()
	{
		return towns;
	}

	public ArrayList<TornadoSave> getTornadoes()
	{
		return tornadoes;
	}

	public PlayerData getPlayerData()
	{
		return playerData;
	}

	public ShopData getShopData()
	{
		return shopData;
	}

	public int[] getEfTimers()
	{
		return efTimers;
	}
	
	public int getTime()
	{
		return time;
	}
}

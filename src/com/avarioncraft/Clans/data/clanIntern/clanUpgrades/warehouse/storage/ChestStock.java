package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.storage;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.Maps;

public class ChestStock {
	
	public static void newConfig(FileConfiguration config) {
		ClanChest chest;
		for(int n = 0; n < 54; n++) {
			chest = new ClanChest(n);
			chest.saveChest(config);
		}
	}
	
	public ChestStock(FileConfiguration config) {
		for(int n = 0; n < 54; n++) {
			this.stock.put(n, new ClanChest(n,config));
		}
	}
	
	private Map<Integer, ClanChest> stock = Maps.newHashMap();
	
	public ClanChest getChest(int slot) {
		return this.stock.get(slot);
	}
	
}
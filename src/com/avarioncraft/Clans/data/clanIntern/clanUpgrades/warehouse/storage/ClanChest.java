package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.storage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;

public class ClanChest {
	
	public ClanChest(int slot, FileConfiguration config) {
		
		this.slot = slot;
		this.clearance = config.getInt("Content.Chest_" + this.slot + ".Clearance");
		this.loadContent(config);
		
	}
	
	public ClanChest(int slot) {
		this.slot = slot;
	}
	
	@Getter
	private int clearance = 0;
	@Getter
	private final int slot;
	private Inventory inv = Bukkit.createInventory(null, 54);
	@Getter @Setter
	boolean enabled = false;
	
	public void open(Player player) {
		player.openInventory(this.inv);
	}
	
	public void saveChest(FileConfiguration config) {
		
		for(int cslot = 0; cslot < 54; cslot++) {
			config.set("Content.Chest_" + this.slot + ".Content.Slot_" + cslot, inv.getItem(cslot));
		}
		
		config.set("Content.Chest_" + this.slot + ".Clearance", this.clearance);
		
	}
	
	public void loadContent(FileConfiguration config) {
		ItemStack[] items = new ItemStack[54];
		
		for(int cslot = 0; cslot < 54; cslot++) {
			items[cslot] = config.getItemStack("Content.Chest_" + this.slot + ".Content.Slot_" + cslot);
		}
		
		inv.setContents(items);
		
	}
}

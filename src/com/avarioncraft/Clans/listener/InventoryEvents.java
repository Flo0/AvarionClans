package com.avarioncraft.Clans.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.avarioncraft.Clans.data.Clan;

public class InventoryEvents implements Listener{
	
	@EventHandler
	public void resourceAddOnClose(InventoryCloseEvent event) {
		
		if(event.getInventory().getName().equals("Clan Resourcen")) {
			Clan clan = Clan.ofMember((Player) event.getPlayer()).get();
			Inventory inv = event.getInventory();
			for(int slot = 0; slot < event.getInventory().getSize(); slot++) {
				if(inv.getItem(slot) == null) continue;
				inv.setItem(slot, clan.getClanResources().addRecource((Player) event.getPlayer(), inv.getItem(slot)));
			}

		}
		
	}
	
}
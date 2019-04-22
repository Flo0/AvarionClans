package com.avarioncraft.Clans.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.avarioncraft.Clans.core.AvarionClans;

public class LeaveEvents implements Listener {
	
	public LeaveEvents() {
		Bukkit.getPluginManager().registerEvents(this, AvarionClans.getPlugin());
	}
	
	@EventHandler
	public void savePlayerInfo(PlayerQuitEvent event) {
		
	}
	
}

package com.avarioncraft.Clans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.MessageBuilder;

import net.crytec.api.util.F;

public class JoinEvents implements Listener {
	
	public JoinEvents() {
		Bukkit.getPluginManager().registerEvents(this, AvarionClans.getPlugin());
	}
	
	
	// Hier wird die PlayerClanInfo für Spieler geladen/erstellt
	@EventHandler
	public void loadPlayerClanInfo(PlayerJoinEvent event) {
		//TODO remove
		Player player = event.getPlayer();
		MessageBuilder.get().newPlayer(player);		
		
		if (Clan.ofMember(player).isPresent()) {
			player.sendMessage(F.main("Clan", "Dein Clan: " + Clan.ofMember(player).get().getName()));
		}
	}
}

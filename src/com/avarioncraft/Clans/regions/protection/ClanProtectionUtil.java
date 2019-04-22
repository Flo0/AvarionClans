package com.avarioncraft.Clans.regions.protection;

import java.util.Optional;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class ClanProtectionUtil {

	private static final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	
	
	public static boolean isOnClanArea(Player player) {
		RegionManager manager = container.get(BukkitAdapter.adapt(player.getWorld()));
		Optional<Clan> clan = Clan.ofMember(player);
		
		if (!clan.isPresent()) return false;
		
		String region = "clan-" + clan.get().getIdentifier();
		return manager.getApplicableRegionsIDs(BukkitAdapter.asVector(player.getLocation()).toBlockPoint()).contains(region);
	}
	
}
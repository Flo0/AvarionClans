package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory;

import org.bukkit.entity.Player;

public interface IArmoryUpgrade {
	
	public void applyAll();
	public void applyFor(Player player);
	
}

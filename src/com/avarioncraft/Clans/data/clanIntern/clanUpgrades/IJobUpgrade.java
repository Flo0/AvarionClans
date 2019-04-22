package com.avarioncraft.Clans.data.clanIntern.clanUpgrades;

import org.bukkit.entity.Player;

public interface IJobUpgrade {
	
	public void applyAll();
	public void applyFor(Player player);

}

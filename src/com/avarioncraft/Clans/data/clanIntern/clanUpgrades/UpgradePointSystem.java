package com.avarioncraft.Clans.data.clanIntern.clanUpgrades;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

public interface UpgradePointSystem {
	
	public int getUpgradePoints();
	public int getSpendPoints();
	public void removeUpgradePoints(int amount);
	public void addUpgradePoints(int amount);
	public Set<UpgradeComponent> getUpgrades();
	public ItemStack getGuiIcon();
	
}

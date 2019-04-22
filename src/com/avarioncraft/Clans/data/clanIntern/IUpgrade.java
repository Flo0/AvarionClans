package com.avarioncraft.Clans.data.clanIntern;

public interface IUpgrade {
	
	public int getMaxLevel();
	public int getLevel();
	public String getDisplayname();
	public UpgradeCosts getUnlockCost();
	
}

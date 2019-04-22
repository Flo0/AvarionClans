package com.avarioncraft.Clans.data.clanIntern.clanUpgrades;

import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;

import lombok.Getter;
import lombok.Setter;
import net.crytec.api.util.RomanNumsUtil;

public abstract class UpgradeComponent {
	
	public UpgradeComponent(int maxLevel, UpgradePointSystem system, Clan clan, String displayName) {
		this.maxLevel = maxLevel;
		this.system = system;
		this.clan = clan;
		this.displayName = displayName;
	}
	
	@Getter
	private final int maxLevel;
	@Getter @Setter
	private int level = 0;
	@Getter @Setter
	private int upgradePoints = 0;
	@Getter @Setter
	private int spendPoints = 0;
	@Getter
	private final UpgradePointSystem system;
	@Getter
	private final Clan clan;
	private final String displayName;
	
	public String getDisplayName() {
		return this.displayName + " " + RomanNumsUtil.toRoman(this.level);
	}
	
	public abstract ItemStack getGuiItem();
	public abstract int getUpgradeCost();
	public abstract boolean isUpgradeable();
	public abstract void onUpgrade();
}

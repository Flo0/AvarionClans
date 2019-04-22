package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.trader.upgradeables;

import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradeComponent;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;
import com.avarioncraft.Clans.util.enums.upgradeSubTypes.TraderUpgradeType;

import lombok.Getter;

public class AvarionTrade extends UpgradeComponent{

	public AvarionTrade(UpgradePointSystem system, Clan clan, String displayName) {
		super(100, system, clan, displayName);
		this.type = TraderUpgradeType.AVARION;
	}

	@Getter
	private final TraderUpgradeType type;

	@Override
	public boolean isUpgradeable() {
		if(super.getMaxLevel() > super.getLevel() && this.getUpgradeCost() <= super.getSystem().getUpgradePoints()) {
			return true;
		}
		return false;
	}

	@Override
	public int getUpgradeCost() {
		return super.getLevel() + 5 + (super.getLevel() * 5);
	}

	@Override
	public void onUpgrade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return null;
	}

}

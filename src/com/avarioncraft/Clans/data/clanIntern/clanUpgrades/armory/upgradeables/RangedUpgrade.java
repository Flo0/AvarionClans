package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.AvarionCombat.buffs.StatBuff;
import com.avarioncraft.AvarionCombat.util.enums.Stat;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradeComponent;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.IArmoryUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.buffs.UpgradeBuff;
import com.avarioncraft.Clans.util.enums.upgradeSubTypes.ArmoryUpgradeType;

import lombok.Getter;
import net.crytec.api.itemstack.ItemBuilder;

public class RangedUpgrade extends UpgradeComponent implements IArmoryUpgrade{

	public RangedUpgrade(UpgradePointSystem system, Clan clan, String displayName) {
		super(10, system, clan, displayName);
		this.type = ArmoryUpgradeType.RANGE;
	}
	
	// Variablen
	@Getter
	private final ArmoryUpgradeType type;
	@Getter
	private StatBuff statBuff;
	private int percentBuff = 5;
	
	// Util Methoden
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
	public void applyAll() {
		for(Player player : super.getClan().getOnlineMembers()) {
			this.applyFor(player);
		}
	}
	
	@Override
	public void applyFor(Player player) {
		
		UpgradeBuff buff = new UpgradeBuff(player, Stat.MELEE_DAMAGE, percentBuff);
		buff.setDisplayName("§eClan Buff: §f" + super.getDisplayName());
		buff.addDescLine("");
		buff.addDescLine("§f[§a+§f] Dieser Buff erhöht deinen Angriffs-");
		buff.addDescLine("§fschaden im Fernkampf um §e" + percentBuff + "%");
		buff.setIconMaterial(Material.BOW);
		
	}
	
	@Override
	public void onUpgrade() {
		
		super.getSystem().removeUpgradePoints(this.getUpgradeCost());
		super.setSpendPoints(super.getSpendPoints() + this.getUpgradeCost());
		super.setLevel(super.getLevel() + 1);
		this.percentBuff = 5 * this.getLevel();
		this.applyAll();
		
	}
	
	@Override
	public ItemStack getGuiItem() {
		return new ItemBuilder(Material.BOW)
				.name("§e" + super.getDisplayName())
				.lore("")
				.lore("§fErhöht den Stat Fernkampfschaden")
				.lore("§fum §e" + percentBuff + "%")
				.build();
	}

}

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

public class PrecisionUpgrade extends UpgradeComponent implements IArmoryUpgrade{

	public PrecisionUpgrade(UpgradePointSystem system, Clan clan, String displayName) {
		super(6, system, clan, displayName);
		this.type = ArmoryUpgradeType.PRECISION;
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
		return super.getLevel() + 8 + (super.getLevel() * 10);
	}

	@Override
	public void applyAll() {
		for(Player player : super.getClan().getOnlineMembers()) {
			this.applyFor(player);
		}
	}
	
	@Override
	public void applyFor(Player player) {
		
		UpgradeBuff buff = new UpgradeBuff(player, Stat.CRIT_CHANCE, percentBuff);
		buff.setDisplayName("§eClan Buff: §f" + super.getDisplayName());
		buff.addDescLine("");
		buff.addDescLine("§f[§a+§f] Dieser Buff erhöht deine §7zusätzliche");
		buff.addDescLine("§fChance auf Kritische Treffer um §e" + percentBuff + "%");
		buff.setIconMaterial(Material.IRON_HOE);
		
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
		return new ItemBuilder(Material.IRON_HOE)
				.name("§e" + super.getDisplayName())
				.lore("")
				.lore("§fErhöht den Stat Rüstungsdurchdringung")
				.lore("§fum §e" + percentBuff + "%")
				.lore("")
				.lore("§fDies hängt vom aktuellen Stat ab und")
				.lore("§fwird nicht aufsummiert.")
				.build();
	}

}

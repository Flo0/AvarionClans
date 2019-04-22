package com.avarioncraft.Clans.data.clanIntern;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.util.enums.UpgradeType;

import lombok.Getter;
import lombok.Setter;
import net.crytec.api.itemstack.ItemBuilder;

public abstract class ClanUpgrade implements IUpgrade {
	
	public ClanUpgrade(Clan clan, UpgradeType type, int level) {
		this.type = type;
		this.upgradeName = this.type.getDisplayName();
		this.level = level;
		this.reqClanLvl = level * 10;
		this.clan = clan;
	}
	
	@Getter
	private final int reqClanLvl;
	@Getter
	private final UpgradeType type;
	@Getter
	private final String upgradeName;
	@Getter @Setter
	private int level;
	@Getter @Setter
	private UpgradeCosts upgradeCosts;
	@Getter
	private final Clan clan;
	
	public abstract void levelUP(boolean silent);
	
	public abstract void nextUpgradeCost(boolean silent);
	
	public abstract void loadFrom(FileConfiguration config);
	
	public abstract void saveTo(FileConfiguration config);
	
	public ItemStack getIcon() {
		return new ItemBuilder(type.getIcon())
		.lore("")
		.name("§e" + type.getDisplayName())
		.lore("")
		.lore("§eLevel: §f" + level + " / " + type.getMaxLvl())
		.lore("")
		.lore("§e[Klicke für mehr Optionen]")
		.setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
		.build();
	}
}
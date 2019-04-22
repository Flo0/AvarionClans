package com.avarioncraft.Clans.util.enums;

import org.bukkit.Material;

import lombok.Getter;

public enum UpgradeType {
	
	BANK("Bank", 10, Material.GOLD_BLOCK),
	CLAN_RECOURCES("Clan Recourcen", 10, Material.CHEST),
	CLAN_WAREHOUSE("Lagerhaus", 5, Material.ENDER_CHEST),
	TEMPLE("Tempel", 10, Material.CHISELED_STONE_BRICKS),
	ARMORY("Waffenkammer", 10, Material.IRON_SWORD),
	GATHERER_JOBS("Sammler Werkstätte", 10, Material.IRON_PICKAXE),
	PRODUCER_JOBS("Produktions Werkstätte", 10, Material.CRAFTING_TABLE),
	TRADING_COMPLEX("Handels Komplex", 10, Material.GOLD_INGOT),
	FLAG("Standarte", 10, Material.WHITE_BANNER),
	BARRACKS("Kaserne", 10, Material.LEATHER_HELMET);
	
	@Getter
	private String displayName;
	@Getter
	private int maxLvl;
	@Getter
	private Material icon;
	
	private UpgradeType(String diaplyName, int maxLvl, Material material) {
		this.displayName = diaplyName;
		this.maxLvl = maxLvl;
		this.icon = material;
	}
	
	
	
}

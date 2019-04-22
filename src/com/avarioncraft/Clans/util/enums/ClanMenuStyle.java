package com.avarioncraft.Clans.util.enums;

import org.bukkit.Material;

import lombok.Getter;

public enum ClanMenuStyle {
	
	RED(Material.RED_STAINED_GLASS_PANE, "Rot"),
	BLUE(Material.BLUE_STAINED_GLASS_PANE, "Blau"),
	YELLOW(Material.YELLOW_STAINED_GLASS_PANE, "Gelb"),
	BLACK(Material.BLACK_STAINED_GLASS_PANE, "Schwarz"),
	GRAY(Material.GRAY_STAINED_GLASS_PANE, "Grau"),
	MAGENTA(Material.MAGENTA_STAINED_GLASS_PANE, "Magenta"),
	LIGHT_BLUE(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "Hellblau"),
	ORANGE(Material.ORANGE_STAINED_GLASS_PANE, "Orange"),
	LIME(Material.LIME_STAINED_GLASS_PANE, "Hellgrün");
	
	
	@Getter
	private Material glass;
	
	@Getter
	private String displayName;
	
	private ClanMenuStyle(Material glass, String displayName) {
		this.glass = glass;
		this.displayName = displayName;
	}
	
}

package com.avarioncraft.Clans.util.enums.resourceValues;

import java.util.EnumMap;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public final class WoodValues implements IResourceValue {
	
	public WoodValues() {
		for(Material material : Material.values()) {
			if(material.toString().contains("_WOOD")) {
				this.values.put(material, 4);
			}
			if(material.toString().contains("WOODEN")) {
				this.values.put(material, 2);
			}
			if(material.toString().contains("PLANKS")) {
				this.values.put(material, 1);
			}
		}

	}
	
	private EnumMap<Material, Integer> values = Maps.newEnumMap(Material.class);
	
	@Override
	public boolean isValid(Material material) {
		return values.containsKey(material);
	}

	@Override
	public int getValue(Material material) {
		return values.getOrDefault(material, 0);
	}
	
}

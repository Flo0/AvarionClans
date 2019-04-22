package com.avarioncraft.Clans.util.enums.resourceValues;

import java.util.EnumMap;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public class WoolValues implements IResourceValue {

	private EnumMap<Material, Integer> values = Maps.newEnumMap(Material.class);

	public WoolValues() {
		
		for (Material mat : Material.values()) {
			if (mat.toString().contains("_WOOL")) {
				values.put(mat, 1);
			}
		}
	}

	@Override
	public boolean isValid(Material material) {
		return values.containsKey(material);
	}

	@Override
	public int getValue(Material material) {
		return values.getOrDefault(material, 0);
	}
}

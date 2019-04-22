package com.avarioncraft.Clans.util.enums.resourceValues;

import java.util.EnumMap;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public class DiamondValues implements IResourceValue {

	private EnumMap<Material, Integer> values = Maps.newEnumMap(Material.class);

	public DiamondValues() {
		values.put(Material.DIAMOND, 1);
		values.put(Material.EMERALD, 1);
		values.put(Material.EMERALD_BLOCK, 9);
		values.put(Material.DIAMOND_BLOCK, 9);
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

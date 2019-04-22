package com.avarioncraft.Clans.util.enums.resourceValues;

import java.util.EnumMap;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public class IronValues implements IResourceValue {

	private EnumMap<Material, Integer> values = Maps.newEnumMap(Material.class);

	public IronValues() {
		values.put(Material.IRON_INGOT, 1);
		values.put(Material.IRON_BLOCK, 9);
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

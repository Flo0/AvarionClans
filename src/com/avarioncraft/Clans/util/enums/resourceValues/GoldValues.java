package com.avarioncraft.Clans.util.enums.resourceValues;

import java.util.EnumMap;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public class GoldValues implements IResourceValue {

	private EnumMap<Material, Integer> values = Maps.newEnumMap(Material.class);

	public GoldValues() {		
		values.put(Material.GOLD_INGOT, 1);
		values.put(Material.GOLD_BLOCK, 9);
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

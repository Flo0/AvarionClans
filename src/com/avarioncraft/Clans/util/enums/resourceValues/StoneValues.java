package com.avarioncraft.Clans.util.enums.resourceValues;

import java.util.EnumMap;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public final class StoneValues implements IResourceValue{

	public StoneValues() {
		 this.values.put(Material.STONE, 1);
		 this.values.put(Material.COBBLESTONE, 1);
		 this.values.put(Material.ANDESITE, 1);
		 this.values.put(Material.DIORITE, 1);
		 for(Material material : Material.values()) {
			 if(material.toString().contains("STONE_") && !material.toString().contains("GLOW")) {
				 this.values.put(material, 1);
			 }
		 }
	}
	
	private EnumMap<Material, Integer> values = Maps.newEnumMap(Material.class);
	
	@Override
	public boolean isValid(Material material) {
		return this.values.containsKey(material);
	}

	@Override
	public int getValue(Material material) {
		return values.getOrDefault(material, 0);
	}

}

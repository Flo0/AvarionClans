package com.avarioncraft.Clans.util.enums.resourceValues;

import org.bukkit.Material;

public class SingleValueResource implements IResourceValue {

	private final Material single;
	
	public SingleValueResource(Material mat) {
		this.single = mat;
	}
	
	@Override
	public boolean isValid(Material material) {
		return material.equals(this.single);
	}

	@Override
	public int getValue(Material material) {
		return 1;
	}
}

package com.avarioncraft.Clans.util.enums.resourceValues;

import org.bukkit.Material;

public interface IResourceValue {
	
	public boolean isValid(Material material);
	public int getValue(Material material);
	
}

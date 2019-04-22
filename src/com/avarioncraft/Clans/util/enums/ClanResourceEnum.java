package com.avarioncraft.Clans.util.enums;

import org.bukkit.Material;

import com.avarioncraft.Clans.util.enums.resourceValues.DiamondValues;
import com.avarioncraft.Clans.util.enums.resourceValues.GoldValues;
import com.avarioncraft.Clans.util.enums.resourceValues.IResourceValue;
import com.avarioncraft.Clans.util.enums.resourceValues.IronValues;
import com.avarioncraft.Clans.util.enums.resourceValues.SingleValueResource;
import com.avarioncraft.Clans.util.enums.resourceValues.StoneValues;
import com.avarioncraft.Clans.util.enums.resourceValues.WoodValues;
import com.avarioncraft.Clans.util.enums.resourceValues.WoolValues;

import lombok.Getter;


public enum ClanResourceEnum {
	
	WOOD("Holz", new WoodValues(), Material.OAK_PLANKS),
	STONE("Stein", new StoneValues(), Material.COBBLESTONE),
	IRON("Eisen", new IronValues(), Material.IRON_NUGGET),
	GOLD("Gold", new GoldValues(), Material.GOLD_NUGGET),
	OBSIDIAN("Obsidian", new SingleValueResource(Material.OBSIDIAN), Material.OBSIDIAN),
	DIAMOND("Edelsteine", new DiamondValues(), Material.FLINT),
	WOOL("Wolle", new WoolValues(), Material.WHITE_WOOL),
	LEATHER("Leder", new SingleValueResource(Material.LEATHER), Material.LEATHER);
	
	@Getter
	private final String displayName;
	@Getter
	private final IResourceValue resourcetype;
	@Getter
	private final Material baseMaterial;

	
	private ClanResourceEnum(String displayName, IResourceValue resourcetype, Material baseMaterial) {
		this.displayName = displayName;
		this.resourcetype = resourcetype;
		this.baseMaterial = baseMaterial;
	}
	
	
	public static ClanResourceEnum getByMaterial(Material mat) {
		if (mat == null) return null;
		for (ClanResourceEnum e : values()) {
			if (e.resourcetype.isValid(mat)) {
				return e;
			}
		}
		return null;
	}
	
}

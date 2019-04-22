package com.avarioncraft.Clans.util.enums.upgradeSubTypes;

import lombok.Getter;

public enum GatherJobUpgradeType {
	
	HUNTER("J�ger"),
	MINER("Bergabeiter"),
	FARMER("Farmer"),
	WOODCUTTER("Holzf�ller");

	@Getter
	private String displayName;
	
	private GatherJobUpgradeType(String displayName) {
		this.displayName = displayName;
	}
	
}

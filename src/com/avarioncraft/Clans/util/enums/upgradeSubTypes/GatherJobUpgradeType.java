package com.avarioncraft.Clans.util.enums.upgradeSubTypes;

import lombok.Getter;

public enum GatherJobUpgradeType {
	
	HUNTER("Jäger"),
	MINER("Bergabeiter"),
	FARMER("Farmer"),
	WOODCUTTER("Holzfäller");

	@Getter
	private String displayName;
	
	private GatherJobUpgradeType(String displayName) {
		this.displayName = displayName;
	}
	
}

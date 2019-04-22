package com.avarioncraft.Clans.util.enums.upgradeSubTypes;

import lombok.Getter;

public enum ArmoryUpgradeType {
	
	ATTACK("Angriff"),
	DEFENSE("Verteidigung"),
	RANGE("Fernkampf"),
	PRECISION("Präzision"),
	PENETRATION("Durchdringung"),
	BRUTALITY("Brutalität"),
	MAGIC("Magie");

	@Getter
	private String displayName;
	
	private ArmoryUpgradeType(String displayName) {
		this.displayName = displayName;
	}
	
}

package com.avarioncraft.Clans.util.enums.upgradeSubTypes;

import lombok.Getter;

public enum ArmoryUpgradeType {
	
	ATTACK("Angriff"),
	DEFENSE("Verteidigung"),
	RANGE("Fernkampf"),
	PRECISION("Pr�zision"),
	PENETRATION("Durchdringung"),
	BRUTALITY("Brutalit�t"),
	MAGIC("Magie");

	@Getter
	private String displayName;
	
	private ArmoryUpgradeType(String displayName) {
		this.displayName = displayName;
	}
	
}

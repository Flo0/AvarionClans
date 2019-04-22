package com.avarioncraft.Clans.util.enums.upgradeSubTypes;

import lombok.Getter;

public enum ProducerJobUpgradeType {
	
	COOK("Koch"),
	CRAFTSMAN("Handwerksmeister"),
	SMITH("Schmied"),
	WITCHER("Hexer"),
	JEWLER("Juwelier");

	@Getter
	private String displayName;
	
	private ProducerJobUpgradeType(String displayName) {
		this.displayName = displayName;
	}

}

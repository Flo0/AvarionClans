package com.avarioncraft.Clans.util.enums;

import lombok.Getter;

public enum ArchitectureStyle {
	
	NORD("Nordisch", 100),
	ASIAN("Asiatisch", 200),
	GOTHIC("Gothisch", 300),
	MEDIEVAL("Mittelalterlich", 400);
	
	@Getter
	private String displayName;
	@Getter
	private int styleID;;
	
	private ArchitectureStyle(String displayName, int styleID) {
		this.displayName = displayName;
		this.styleID = styleID;
	}
	
}

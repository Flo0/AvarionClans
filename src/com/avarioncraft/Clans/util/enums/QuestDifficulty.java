package com.avarioncraft.Clans.util.enums;

import lombok.Getter;

public enum QuestDifficulty {
	
	EASY("Leicht"),
	NORMAL("Normal"),
	ADVANCED("Fortgeschritten"),
	HARD("Schwierig"),
	TITAN("Titan");
	
	@Getter
	private String dispalyName;
	
	private QuestDifficulty(String displayName) {
		this.dispalyName = displayName;
	}

}

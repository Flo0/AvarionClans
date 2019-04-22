package com.avarioncraft.Clans.util.enums;

import lombok.Getter;

public enum Permission{
	
	INVITE("Spieler einladen"),
	KICK("Spieler kicken"),
	BAN("Spieler bannen"),
	BROADCAST("Clan Broadcast"),
	BANK_PAY("Bank Pay?"),
	BANK_ADD("Bank Add?"),
	BANK_SUBTRACT("Bank Subtract?"),
	PROMOTE("Spieler befördern"),
	PRIVACY("Privatspäre ändern"),
	RANK_MANAGER("Ränge verwalten");
	
	@Getter
	private String displayName;
	
	private Permission(String displayName) {
		this.displayName = displayName;
	}
	
}
package com.avarioncraft.Clans.util.enums;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import lombok.Getter;

public enum ClanPrivacy {
	
	PRIVATE("�cPrivat", Arrays.asList("�fSpieler k�nnen nur mit einer", "�fEinladung beitreten"), Material.RED_WOOL),
	PROTECTED("�6Gesichert", Arrays.asList("�fSpieler m�ssen erst eine", "�fAnfrage zum Beitritt stellen"), Material.ORANGE_WOOL),
	PUBLIC("�a�ffentlich", Arrays.asList("�fJeder kann diesem Clan beitreten."), Material.GREEN_WOOL);
	
	@Getter
	private List<String> description;
	@Getter
	private String displayname;
	@Getter
	private Material material;
	
	private ClanPrivacy(String displayname, List<String> description, Material material) {
		this.displayname = displayname;
		this.description = description;
		this.material = material;
	}
}

package com.avarioncraft.Clans.data.message;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;

public class ClanRequest extends ClanMessage{

	public ClanRequest(Clan clan, Player player) {
		super("§c§f§eAnfrage", "§fIch möchte eurem Clan beitreten.%§e[LinksKlicken zum annehmen]%§e[RechtsKlicken zum ablehnen]", System.currentTimeMillis(), "§r§fSpieler: " + player.getName() + "@" + player.getUniqueId().toString());
	}
	
}

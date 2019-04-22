package com.avarioncraft.Clans.data.message;

import com.avarioncraft.Clans.data.Clan;

import net.crytec.api.util.F;

public class ClanResponse extends ClanMessage{

	public ClanResponse(Clan clan, boolean accepted) {
		super("§e§a§eAntwort", "§fAngenommen: " + F.tf(accepted), System.currentTimeMillis(), "§rClan: " + clan.getName() + "@" + clan.getIdentifier());
	}

}

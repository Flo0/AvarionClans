package com.avarioncraft.Clans.data.message;

import com.avarioncraft.Clans.data.Clan;

public class ClanInvitation extends ClanMessage{

	public ClanInvitation(Clan clan) {
		super("§e§a§eEinladung", " %§fDu wurdest in unseren Clan eingeladen.% %§e[LinksKlicken zum §aannehmen§e]% %§e[RechtsKlicken zum §cablehnen§e]", System.currentTimeMillis(), "§r§fClan: " + clan.getName() + "@" + clan.getIdentifier());
	}
	
}

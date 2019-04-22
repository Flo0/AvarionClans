package com.avarioncraft.Clans.data.message.manager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.crytec.api.util.F;

public class MessageCommandEvent implements Listener{
	
	@EventHandler
	public void broadcast(PlayerCommandPreprocessEvent event) {
		if(event.getMessage().startsWith("/rhj9843")) {
			ClanMessageManager.openManager.get(event.getPlayer()).sendPlayer(event.getPlayer());
			ClanMessageManager.openManager.remove(event.getPlayer());
			event.getPlayer().sendMessage(F.main("Nachricht", "Nachricht wurde erfolgreich abgesendet."));
			event.setCancelled(true);
		}else if(event.getMessage().startsWith("/g54wgath6")) {
			ClanMessageManager.openManager.get(event.getPlayer()).broadcast(event.getPlayer());
			ClanMessageManager.openManager.remove(event.getPlayer());
			event.getPlayer().sendMessage(F.main("Nachricht", "Nachricht wurde erfolgreich abgesendet."));
			event.setCancelled(true);
		}else if(event.getMessage().startsWith("/hgaertg45q")) {
			for(int n = 1; n <= 19 ; n++) {
				event.getPlayer().sendMessage("");
			}
			ClanMessageManager.openManager.remove(event.getPlayer());
			event.getPlayer().sendMessage(F.main("Nachricht", "Wurde abgebrochen."));
			event.setCancelled(true);
		}
		
	}

}

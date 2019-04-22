package com.avarioncraft.Clans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;

public class ClanCreateEvent extends ClanEvent {

	private static final HandlerList handlers = new HandlerList();

	public ClanCreateEvent(Clan clan, Player player) {
		super(clan, player);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
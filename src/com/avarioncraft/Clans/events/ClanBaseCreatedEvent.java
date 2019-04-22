package com.avarioncraft.Clans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;

import lombok.Getter;

public class ClanBaseCreatedEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Getter
	private Clan clan;
	@Getter
	private Player player;

	public ClanBaseCreatedEvent(Clan clan, Player player) {
		this.player = player;
		this.clan = clan;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}

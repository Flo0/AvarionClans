package com.avarioncraft.Clans.events;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;

import lombok.Getter;

public class ClanEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Getter
	private Player player;
	@Nullable
	@Getter
	private Clan clan = null;
	@Getter
	private Long time;

	public ClanEvent(Clan clan, Player player) {
		this.clan = clan;
		this.player = player;
		this.time = System.currentTimeMillis();
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}

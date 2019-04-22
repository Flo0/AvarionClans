package com.avarioncraft.Clans.events;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.Rank;

import lombok.Getter;
import lombok.Setter;

public class ClanMemberAddEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;

	@Getter
	@Setter
	private Rank defaultRank;
	@Getter
	private final Clan clan;
	@Getter
	private final UUID uniqueId;

	public ClanMemberAddEvent(Clan clan, UUID uuid, Rank rank) {
		this.clan = clan;
		this.uniqueId = uuid;
		this.defaultRank = rank;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
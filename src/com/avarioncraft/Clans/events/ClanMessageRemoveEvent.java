package com.avarioncraft.Clans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.ClanMessage;

import lombok.Getter;

public class ClanMessageRemoveEvent extends ClanEvent {

	private static final HandlerList handlers = new HandlerList();

	@Getter
	private ClanMessage message;

	public ClanMessageRemoveEvent(ClanMessage message, Clan clan, Player player) {
		super(clan, player);
		this.message = message;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}

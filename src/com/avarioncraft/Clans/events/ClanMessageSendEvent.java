package com.avarioncraft.Clans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.ClanMessage;

import lombok.Getter;
import lombok.Setter;

public class ClanMessageSendEvent extends ClanEvent {

	private static final HandlerList handlers = new HandlerList();

	@Getter
	@Setter
	private ClanMessage message;

	public ClanMessageSendEvent(ClanMessage message, Player player, Clan clan) {
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

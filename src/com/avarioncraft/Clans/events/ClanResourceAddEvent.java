package com.avarioncraft.Clans.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;

import lombok.Getter;
import lombok.Setter;

public class ClanResourceAddEvent extends ClanEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;

	@Getter
	@Setter
	private ClanResourceEnum resourcetype;
	@Getter
	private Material material;
	@Getter
	@Setter
	private int amount;
	@Getter
	private final ClanUpgrade upgrade;

	public ClanResourceAddEvent(Clan clan, Player player, ClanUpgrade upgrade, ClanResourceEnum resourcetype, Material material, int amount) {
		super(clan, player);
		this.resourcetype = resourcetype;
		this.material = material;
		this.amount = amount;
		this.upgrade = upgrade;
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

package com.avarioncraft.Clans.events;

import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;

import lombok.Getter;
import lombok.Setter;

// Aktuell nicht implementiert, da ich den Aufbau der Klassen geändert habe
@Deprecated
public class UpgradeCostCreateEvent extends Event {

	public UpgradeCostCreateEvent(Clan clan, double money, int clanPoints, HashMap<ClanResourceEnum, Integer> resources) {
		this.clan = clan;
		this.clanPoints = clanPoints;
		this.money = money;
		this.resources = resources;
	}

	private static final HandlerList handlers = new HandlerList();

	@Getter
	private final Clan clan;
	@Getter
	@Setter
	private double money;
	@Getter
	@Setter
	private int clanPoints;
	@Getter
	@Setter
	private HashMap<ClanResourceEnum, Integer> resources;

	public void modifyResources(double modifier) {
		resources.forEach((rec, val) -> {
			resources.put(rec, (int) (val * modifier));
		});
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}

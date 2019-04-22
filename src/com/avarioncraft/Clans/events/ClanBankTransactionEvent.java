package com.avarioncraft.Clans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;

import lombok.Getter;
import lombok.Setter;

public class ClanBankTransactionEvent extends ClanEvent {

	public enum BankTransactionMode {
		DEPOSIT,
		WITHDRAW
	}

	private static final HandlerList handlers = new HandlerList();

	@Getter
	@Setter
	private double amount;
	@Getter
	private final BankTransactionMode mode;
	@Getter
	private final ClanUpgrade upgrade;

	public ClanBankTransactionEvent(Clan clan, Player player, ClanUpgrade upgrade, double amount, BankTransactionMode mode) {
		super(clan, player);
		this.amount = amount;
		this.mode = mode;
		this.upgrade = upgrade;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
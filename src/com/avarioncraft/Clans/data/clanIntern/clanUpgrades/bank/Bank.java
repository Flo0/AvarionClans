package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.bank;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.events.ClanBankTransactionEvent;
import com.avarioncraft.Clans.events.ClanBankTransactionEvent.BankTransactionMode;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.avarioncraft.Clans.util.enums.UpgradeType;

import lombok.Getter;
import lombok.Setter;
import net.crytec.api.util.F;

public class Bank extends ClanUpgrade {
	
	public Bank(Clan clan, UpgradeType type, int level) {
		super(clan, type, level);
		this.maxMoney = 50000D * Math.pow(getLevel(), 5D);
		this.nextUpgradeCost(false);
	}

	// Methoden für das Bankmanagement
	public void loadFrom(FileConfiguration config) {
		int lvl = config.getInt("Upgrades.Bank.Lvl");
		while(lvl > 1) {
			this.levelUP(true);
			lvl--;
		}
		this.money = config.getDouble("Upgrades.Bank.Money");
	}
	/**
	 * Zahlt einen Betrag in die Bank ein.
	 * 
	 * @param value das einzuzahlende Geld
	 * @return the Das überschüssige, nicht einzahlbare Geld
	 */
	public double addMoney(@Nullable Player player, double value) {

		double combined = this.money + value;

		if (combined > this.maxMoney) {
			this.money = this.maxMoney;
			
			ClanBankTransactionEvent event = new ClanBankTransactionEvent(super.getClan(), player, this, value - (combined - this.maxMoney) , BankTransactionMode.DEPOSIT);
			Bukkit.getPluginManager().callEvent(event);
			
			AvarionClans.economy.depositPlayer(player, combined - this.maxMoney);
			player.sendMessage(F.main("Bank", "Schatzkammer voll -> §e" + (combined - this.maxMoney) + "§f wieder zurück."));
			
			return combined - this.maxMoney;
		} else {
			
			ClanBankTransactionEvent event = new ClanBankTransactionEvent(super.getClan(), player, this, value , BankTransactionMode.DEPOSIT);
			Bukkit.getPluginManager().callEvent(event);
			player.sendMessage(F.main("Bank", "Es wurde §e" + value + "§f eingezahlt."));
			
			AvarionClans.economy.withdrawPlayer(player, value);
			this.money += value;
			return 0D;
		}
	}
	
	/**
	 * Löscht einen Teil des Clan geldes.
	 * @param value der zu löschende Betrag
	 * @return ob der Betrag gelöscht werden kann.
	 */
	public boolean removeMoney(double value) {
		
		double combined = this.money - value;

		if(combined < 0) {
			return false;
		}else {
			this.money -= value;
			return true;
		}
		
		
	}
	
	/**
	 * Hebt einen Betrag aus der Bank ab.
	 * 
	 * @param value der abzuhebende Betrag
	 * @return der tatsächlich abgehobene betrag
	 */
	public double withdrawMoney(@Nullable Player player, double value) {

		double combined = this.money - value;
		double withdraw = 0D;

		if (combined < 0D) {
			withdraw = money;
			this.money = 0D;
		} else {
			withdraw = value;
		}
		
		ClanBankTransactionEvent event = new ClanBankTransactionEvent(super.getClan(), player, this, withdraw , BankTransactionMode.WITHDRAW);
		Bukkit.getPluginManager().callEvent(event);
		
		player.sendMessage(F.main("Bank", "Es wurde §e" + withdraw + "§f abgehoben."));
		AvarionClans.economy.depositPlayer(player, withdraw);
		
		return withdraw;
	}

	// Variablen der Bank
	@Getter
	@Setter
	private double money = 0;
	@Getter
	private double maxMoney;
	
	// Util Methoden
	@Override
	public void levelUP(boolean silent) {
		if(this.getLevel() != 0 && !silent) {
			this.getUpgradeCosts().payCosts();
		}
		setLevel(getLevel() + 1);
		this.maxMoney = 50000D * Math.pow(getLevel(), 5D);
		this.nextUpgradeCost(silent);
	}
	
	@Override
	public final UpgradeCosts getUnlockCost() {
		return new UpgradeCosts(getClan());
	}

	@Override
	public int getMaxLevel() {
		return super.getType().getMaxLvl();
	}

	@Override
	public String getDisplayname() {
		return getType().getDisplayName();
	}
	
	@Override
	public void nextUpgradeCost(boolean silent) {
		
		if(getLevel() == 0) {
			setUpgradeCosts(this.getUnlockCost());
		}
		
		double money;
		int points;
		UpgradeCosts costs = new UpgradeCosts(this.getClan());
		
		if(this.getLevel() == 1) {
			money = 18000;
			points = 10;
			
			costs.setResource(ClanResourceEnum.STONE, 2048);
			costs.setResource(ClanResourceEnum.WOOD, 512);
			costs.setResource(ClanResourceEnum.IRON, 128);
			costs.setResource(ClanResourceEnum.GOLD, 80);
			costs.setResource(ClanResourceEnum.OBSIDIAN, 48);
			costs.setResource(ClanResourceEnum.DIAMOND, 32);
			costs.setMoney(money);
			costs.setClanPoints(points);
			this.setUpgradeCosts(costs);
			return;
		}else if(this.getLevel() != 0){
			money = getUpgradeCosts().getMoney() * Math.pow(getLevel(), 3);
			points = getUpgradeCosts().getClanPoints() * getLevel() * 2;
			getUpgradeCosts().getCurrentResources().forEach( (rec, val) -> {
				costs.setResource(rec, val * (int) Math.pow(getLevel(), 1.3));
			});
			costs.setMoney(money);
			costs.setClanPoints(points);
			this.setUpgradeCosts(costs);
		}
		
	}

	@Override
	public void saveTo(FileConfiguration config) {
		
		config.set("Upgrades.Bank.Money", this.money);
		config.set("Upgrades.Bank.Lvl", super.getLevel());
		
	}

}

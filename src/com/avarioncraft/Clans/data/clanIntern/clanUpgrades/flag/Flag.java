package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.flag;

import org.bukkit.configuration.file.FileConfiguration;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.avarioncraft.Clans.util.enums.UpgradeType;

import lombok.Getter;

public class Flag extends ClanUpgrade{
	
	public Flag(Clan clan, UpgradeType type, int level) {
		super(clan, type, level);
		this.nextUpgradeCost(false);
	}
	
	@Getter
	private final int finalChunkLimit = 2500;
	@Getter
	private int maxChunks = 45;
	@Getter
	private int currentChunks = 0;
	
	@Override
	public void levelUP(boolean silent) {
		if(!silent) {
			super.getUpgradeCosts().payCosts();
		}
		super.setLevel(super.getLevel() + 1);
		this.maxChunks = 42 + 2 * ((int) Math.pow(super.getLevel(), 3));
		this.nextUpgradeCost(silent);
	}
	
	@Override
	public int getMaxLevel() {
		return super.getType().getMaxLvl();
	}
	
	@Override
	public String getDisplayname() {
		return super.getType().getDisplayName();
	}
	
	@Override
	public final UpgradeCosts getUnlockCost() {
		UpgradeCosts costs = new UpgradeCosts(this.getClan());
		costs.setResource(ClanResourceEnum.STONE, 256);
		costs.setResource(ClanResourceEnum.WOOD, 256);
		costs.setResource(ClanResourceEnum.IRON, 64);
		costs.setResource(ClanResourceEnum.GOLD, 4);
		costs.setClanPoints(5);
		costs.setMoney(5000);
		return costs;
	}

	@Override
	public void nextUpgradeCost(boolean silent) {
		
		if(super.getLevel() == 0) {
			super.setUpgradeCosts(this.getUnlockCost());
		}
		
		double money;
		int points;
		UpgradeCosts costs = new UpgradeCosts(this.getClan());
		
		if(this.getLevel() == 1) {
			money = 10000;
			points = 10;
			
			costs.setResource(ClanResourceEnum.WOOD, 2048);
			costs.setResource(ClanResourceEnum.STONE, 128);
			costs.setResource(ClanResourceEnum.WOOL, 80);
			costs.setResource(ClanResourceEnum.IRON, 32);
			costs.setResource(ClanResourceEnum.GOLD, 32);
			costs.setResource(ClanResourceEnum.LEATHER, 32);
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
	public void loadFrom(FileConfiguration config) {
		int lvl = config.getInt("Upgrades.Flag.Lvl");
		while(lvl > 0) {
			this.levelUP(true);
			lvl--;
		}
		this.currentChunks = config.getInt("Upgrades.Flag.Chunks");
	}

	@Override
	public void saveTo(FileConfiguration config) {
		config.set("Upgrades.Flag.Lvl", super.getLevel());
		config.set("Upgrades.Flag.Chunks", this.currentChunks);
	}
}

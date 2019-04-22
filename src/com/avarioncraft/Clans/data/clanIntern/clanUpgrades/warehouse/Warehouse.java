package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.storage.ChestStock;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.avarioncraft.Clans.util.enums.UpgradeType;

import lombok.Getter;

public class Warehouse extends ClanUpgrade {

	public Warehouse(Clan clan, UpgradeType type, int level) {
		super(clan, type, level);
		this.nextUpgradeCost(false);
		
		File clanFolder = new File(AvarionClans.getPlugin().getDataFolder()
				+ File.separator + "clandata" 
				+ File.separator + super.getClan().getIdentifier());
		if(!clanFolder.exists()) {
			clanFolder.mkdirs();
		}
		this.warehouseFile = new File(AvarionClans.getPlugin().getDataFolder()
				+ File.separator + "clandata" 
				+ File.separator + super.getClan().getIdentifier() 
				+ File.separator + "warehouse.yml");
		if(!this.warehouseFile.exists()) {
			try {
				this.warehouseFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.warehouseConfig = YamlConfiguration.loadConfiguration(this.warehouseFile);
			ChestStock.newConfig(this.warehouseConfig);
			try {
				this.warehouseConfig.save(this.warehouseFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.stock = new ChestStock(this.warehouseConfig);
		}else {
			this.warehouseConfig = YamlConfiguration.loadConfiguration(this.warehouseFile);
			this.stock = new ChestStock(this.warehouseConfig);
		}
	}
	
	private File warehouseFile;
	private FileConfiguration warehouseConfig;
	@Getter
	private ChestStock stock;
	
	// Util Methoden
	@Override
	public void levelUP(boolean silent) {
		if(this.getLevel() != 0 && !silent) {
			this.getUpgradeCosts().payCosts();
		}
		super.setLevel(super.getLevel() + 1);
		
		for (int i = 0; i < (9 * getLevel()); i++) {
			this.stock.getChest(i).setEnabled(true);
		}
		
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
			
			costs.setResource(ClanResourceEnum.STONE, 1024);
			costs.setResource(ClanResourceEnum.WOOD, 1024);
			costs.setResource(ClanResourceEnum.IRON, 64);
			costs.setResource(ClanResourceEnum.OBSIDIAN, 32);
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
		int lvl = config.getInt("Upgrades.Warehouse.Lvl");
		while(lvl > 0) {
			this.levelUP(true);
			lvl--;
		}
	}
	
	@Override
	public void saveTo(FileConfiguration config) {
		config.set("Upgrades.Warehouse.Lvl", super.getLevel());
		for(int chest = 0; chest < 54; chest++) {
			this.stock.getChest(chest).saveChest(this.warehouseConfig);
		}
		
		try {
			this.warehouseConfig.save(this.warehouseFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

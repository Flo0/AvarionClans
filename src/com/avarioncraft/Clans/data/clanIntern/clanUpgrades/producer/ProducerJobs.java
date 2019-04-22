package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer;

import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradeComponent;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.upgradeables.CookUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.upgradeables.CraftshipUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.upgradeables.JewlerUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.upgradeables.SmithUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.upgradeables.WitcherUpgrade;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.avarioncraft.Clans.util.enums.UpgradeType;
import com.avarioncraft.Clans.util.enums.upgradeSubTypes.ProducerJobUpgradeType;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

public class ProducerJobs extends ClanUpgrade implements UpgradePointSystem{

	public ProducerJobs(Clan clan, UpgradeType type, int level) {
		super(clan, type, level);
		this.addUpgradeComponent(new CookUpgrade(this, clan, ""), ProducerJobUpgradeType.COOK);
		this.addUpgradeComponent(new CraftshipUpgrade(this, clan, ""), ProducerJobUpgradeType.CRAFTSMAN);
		this.addUpgradeComponent(new JewlerUpgrade(this, clan, ""), ProducerJobUpgradeType.JEWLER);
		this.addUpgradeComponent(new SmithUpgrade(this, clan, ""), ProducerJobUpgradeType.SMITH);
		this.addUpgradeComponent(new WitcherUpgrade(this, clan, ""), ProducerJobUpgradeType.WITCHER);
		this.nextUpgradeCost(false);
	}
	
	// Variablen
		@Getter @Setter
		private int points;
		@Setter
		private int spendPoints;
		
		private Map<ProducerJobUpgradeType, UpgradeComponent> UpgradeMap = Maps.newHashMap();
		private Set<UpgradeComponent> Upgrades = Sets.newHashSet();
		
		private void addUpgradeComponent(UpgradeComponent component, ProducerJobUpgradeType type) {
			this.UpgradeMap.put(type, component);
			this.Upgrades.add(component);
		}
		
		// PointSystem Methoden
		@Override
		public void removeUpgradePoints(int amount) {
			if(this.points < amount) {
				this.points = 0;
			}else {
				this.points -= amount;
			}
		}

		@Override
		public void addUpgradePoints(int amount) {
			this.points += amount;
		}
		
		@Override
		public int getUpgradePoints() {
			return this.points;
		}

		@Override
		public int getSpendPoints() {
			return this.spendPoints;
		}

		@Override
		public Set<UpgradeComponent> getUpgrades() {
			return this.Upgrades;
		}
		
		
		// Util Methoden
		@Override
		public void levelUP(boolean silent) {
			if(!silent) {
				super.getUpgradeCosts().payCosts();
				this.points += 5;
			}
			super.setLevel(super.getLevel() + 1);
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
				
				costs.setResource(ClanResourceEnum.WOOD, 256);
				costs.setResource(ClanResourceEnum.STONE, 256);
				costs.setResource(ClanResourceEnum.IRON, 80);
				costs.setResource(ClanResourceEnum.GOLD, 64);
				costs.setResource(ClanResourceEnum.WOOL, 64);
				costs.setResource(ClanResourceEnum.LEATHER, 64);
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
		public ItemStack getGuiIcon() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void loadFrom(FileConfiguration config) {
			int lvl = config.getInt("Upgrades.Producerjobs.Lvl");
			while(lvl > 0) {
				this.levelUP(true);
				lvl--;
			}
			
		}

		@Override
		public void saveTo(FileConfiguration config) {
			config.set("Upgrades.Producerjobs.Lvl", super.getLevel());
			
		}

}

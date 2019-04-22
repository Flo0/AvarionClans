package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory;

import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradeComponent;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.AttackUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.BrutalityUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.DefenseUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.MagicUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.PenetrationUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.PrecisionUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.RangedUpgrade;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.avarioncraft.Clans.util.enums.UpgradeType;
import com.avarioncraft.Clans.util.enums.upgradeSubTypes.ArmoryUpgradeType;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Setter;
import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.util.RomanNumsUtil;

public class Armory extends ClanUpgrade implements UpgradePointSystem {

	public Armory(Clan clan, UpgradeType type, int level) {
		super(clan, type, level);
		this.addUpgradeComponent(new AttackUpgrade(this, clan, "Geschmiedete Klingen"), ArmoryUpgradeType.ATTACK);
		this.addUpgradeComponent(new DefenseUpgrade(this, clan, "Geschmiedete Rüstung"), ArmoryUpgradeType.DEFENSE);
		this.addUpgradeComponent(new RangedUpgrade(this, clan, "Holzfaser Schliff"), ArmoryUpgradeType.RANGE);
		this.addUpgradeComponent(new PrecisionUpgrade(this, clan, "Präzisionslehre"), ArmoryUpgradeType.PRECISION);
		this.addUpgradeComponent(new PenetrationUpgrade(this, clan, "Schwachstellenkunde"), ArmoryUpgradeType.PENETRATION);
		this.addUpgradeComponent(new BrutalityUpgrade(this, clan, "Schweres Metall"), ArmoryUpgradeType.BRUTALITY);
		this.addUpgradeComponent(new MagicUpgrade(this, clan, "Magische Lehre"), ArmoryUpgradeType.MAGIC);
		this.nextUpgradeCost(false);
	}
	
	// Variablen
	@Setter
	private int points = 0;
	@Setter
	private int spendPoints = 0;
	
	private Map<ArmoryUpgradeType, UpgradeComponent> UpgradeMap = Maps.newEnumMap(ArmoryUpgradeType.class);
	private Set<UpgradeComponent> Upgrades = Sets.newHashSet();
	
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
	private void addUpgradeComponent(UpgradeComponent component, ArmoryUpgradeType type) {
		this.UpgradeMap.put(type, component);
		this.Upgrades.add(component);
	}
	
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
	public final UpgradeCosts getUnlockCost() {
		UpgradeCosts costs = new UpgradeCosts(this.getClan());
		costs.setResource(ClanResourceEnum.STONE, 512);
		costs.setResource(ClanResourceEnum.WOOD, 256);
		costs.setResource(ClanResourceEnum.IRON, 64);
		costs.setResource(ClanResourceEnum.GOLD, 16);
		costs.setMoney(5000);
		costs.setClanPoints(5);
		return costs;
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
	public void nextUpgradeCost(boolean silent) {
		
		if(super.getLevel() == 0) {
			super.setUpgradeCosts(this.getUnlockCost());
		}
		
		double money;
		int points;
		
		UpgradeCosts costs = new UpgradeCosts(this.getClan());
		
		if(this.getLevel() == 1) {
			money = 12500;
			points = 10;
			
			costs.setResource(ClanResourceEnum.STONE, 512);
			costs.setResource(ClanResourceEnum.WOOD, 512);
			costs.setResource(ClanResourceEnum.IRON, 256);
			costs.setResource(ClanResourceEnum.LEATHER, 32);
			costs.setResource(ClanResourceEnum.GOLD, 16);
			costs.setClanPoints(points);
			costs.setMoney(money);
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
			super.setUpgradeCosts(costs);
		}
	}

	@Override
	public ItemStack getGuiIcon() {
		return new ItemBuilder(Material.IRON_SWORD)
				.name("§eWaffenkammer " + RomanNumsUtil.toRoman(this.getLevel()))
				.lore("")
				.lore("§ePunkte: §f" + this.points)
				.build();
		
	}

	@Override
	public void loadFrom(FileConfiguration config) {
		int lvl = config.getInt("Upgrades.Armory.Lvl");
		while(lvl > 0) {
			this.levelUP(true);
			lvl--;
		}
		
	}

	@Override
	public void saveTo(FileConfiguration config) {
		config.set("Upgrades.Armory.Level", super.getLevel());
	}
}
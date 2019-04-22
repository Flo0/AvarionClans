package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.resource;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.events.ClanResourceAddEvent;
import com.avarioncraft.Clans.util.MathUtil;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.avarioncraft.Clans.util.enums.UpgradeType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import net.crytec.api.itemstack.ItemBuilder;

public class ClanResources extends ClanUpgrade {

	public ClanResources(Clan clan, UpgradeType type, int level) {
		super(clan, type, level);
		this.maxResourceCount = (int) (10240 * Math.pow(super.getLevel(), 2));
		for(ClanResourceEnum res: ClanResourceEnum.values()) {
			this.content.put(res, 0);
		}
		this.nextUpgradeCost(false);
	}
	/**
	 * Zahlt Materialien in das Lager ein
	 * @param item der einzuzahlende Stack
	 * @return überschüssige Items.
	 */
	public ItemStack addRecource(@Nullable Player player, ItemStack item) {
		if(item == null) return new ItemStack(Material.AIR);
		if(ClanResourceEnum.getByMaterial(item.getType()) == null) return item;
		ClanResourceEnum rec = ClanResourceEnum.getByMaterial(item.getType());
		
		int amount = item.getAmount();
		
		ClanResourceAddEvent event = new ClanResourceAddEvent(super.getClan(), player, this, rec, item.getType(), amount);
		Bukkit.getPluginManager().callEvent(event);
		
		if (event.isCancelled()) {
			return item;
		}
		
		int value = event.getAmount() * rec.getResourcetype().getValue(item.getType());
		if(this.resourceCount + value < this.maxResourceCount) {
			this.resourceCount += value;
			this.content.put(rec, this.content.get(rec) + value);
			return new ItemStack(Material.AIR);
		}else {
			return item;
		}
	}
	
	public boolean removeRecource(ClanResourceEnum type, int amount) {
		
		if(this.content.get(type) - amount < 0) return false;
		
		this.content.put(type, this.content.get(type) - amount);
		
		return true;
	}
	
	/**
	 * Hebt Materialien von dem Lager als deren Basisbestandteile ab.
	 * @param type Art des abgehobenen Materials
	 * @param amount Menge
	 * @return Ein SimpleEntry aus baseMaterial und Menge
	 */
	public AbstractMap.SimpleEntry<Material, Integer> getRecource(ClanResourceEnum type, int amount) {
		
		AbstractMap.SimpleEntry<Material, Integer> baseMaterialPackage;
		
		if(this.content.get(type) > amount) {
			baseMaterialPackage = new AbstractMap.SimpleEntry<Material, Integer>(type.getBaseMaterial(), amount);
			this.resourceCount -= amount;
			this.content.put(type, this.content.get(type) - amount);
		}else {
			baseMaterialPackage = new AbstractMap.SimpleEntry<Material, Integer>(type.getBaseMaterial(), this.content.get(type));
			this.resourceCount -= this.content.get(type);
			this.content.put(type, 0);
		}
		return baseMaterialPackage;
	}
	
	
	// Variablen des Warenhauses
	@Getter @Setter
	private int resourceCount;
	@Getter
	private int maxResourceCount;
	@Setter
	private HashMap<ClanResourceEnum, Integer> content = Maps.newHashMap();
	
	
	public boolean has(ClanResourceEnum resource, int amount) {
		return content.get(resource) >= amount; 
	}
	
	public int getCurrentResourceAmount(ClanResourceEnum resource) {
		return content.get(resource);
	}
	
	public ItemStack getIcon() {
		
		List<String> temp = Lists.newArrayList();
		
		for (ClanResourceEnum res : ClanResourceEnum.values()) {
			temp.add("§e" + res.getDisplayName() + ": §f" + this.getCurrentResourceAmount(res));
		}
		
		return new ItemBuilder(Material.CHEST)
				.name("§eClan Resourcen")
				.lore("")
				.lore("§e" + this.getResourceCount() + " / " + this.getMaxResourceCount())
				.lore(MathUtil.ProgressBar(this.getResourceCount(), this.getMaxResourceCount(), 20, "▇"))
				.lore("")
				.lore(temp)
				.build();
	}
	
	// Util Methoden
	@Override
	public void levelUP(boolean silent) {
		if(this.getLevel() != 0 && !silent) {
			super.getUpgradeCosts().payCosts();
		}
		super.setLevel(super.getLevel() + 1);
		this.maxResourceCount = (int) (10240 * Math.pow(super.getLevel(), 2));
		this.nextUpgradeCost(silent);
	}
	
	@Override
	public final UpgradeCosts getUnlockCost() {
		return new UpgradeCosts(this.getClan());
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
			money = 10000;
			points = 10;
			
			costs.setResource(ClanResourceEnum.WOOD, 2048);
			costs.setResource(ClanResourceEnum.STONE, 2048);
			costs.setResource(ClanResourceEnum.IRON, 64);
			costs.setMoney(money);
			costs.setClanPoints(points);
			super.setUpgradeCosts(costs);
			return;
		}else if(this.getLevel() != 0){
			money = super.getUpgradeCosts().getMoney() * Math.pow(getLevel(), 3);
			points = super.getUpgradeCosts().getClanPoints() * getLevel() * 2;
			super.getUpgradeCosts().getCurrentResources().forEach( (rec, val) -> {
				costs.setResource(rec, val * (int) Math.pow(getLevel(), 1.3));
			});
			costs.setMoney(money);
			costs.setClanPoints(points);
			this.setUpgradeCosts(costs);
		}
		
	}
	@Override
	public void loadFrom(FileConfiguration config) {
		int lvl = config.getInt("Upgrades.Resources.Lvl");
		while(lvl > 1) {
			this.levelUP(true);
			lvl--;
		}
		
		this.resourceCount = config.getInt("Upgrades.Resources.Count");
		
		for(ClanResourceEnum res : ClanResourceEnum.values()) {
			this.content.put(res, config.getInt("Upgrades.Resources.Content" + res.toString()));
		}
		
	}
	@Override
	public void saveTo(FileConfiguration config) {
		config.set("Upgrades.Resources.Lvl", super.getLevel());
		config.set("Upgrades.Resources.Count", this.resourceCount);
		for(ClanResourceEnum res : ClanResourceEnum.values()) {
			config.set("Upgrades.Resources.Content" + res.toString(), this.content.get(res));
		}
	}

}

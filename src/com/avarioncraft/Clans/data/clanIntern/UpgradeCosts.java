package com.avarioncraft.Clans.data.clanIntern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.util.enums.ClanResourceEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;

public class UpgradeCosts{
	
	private final Clan clan;
	@Getter
	private double money = 0;
	@Getter
	private int clanPoints = 0;

	private HashMap<ClanResourceEnum, Integer> resources = Maps.newHashMap();
	
	public UpgradeCosts(Clan clan) {
		this.clan = clan;
		this.resources = Maps.newHashMapWithExpectedSize(ClanResourceEnum.values().length);
		
		for (ClanResourceEnum res : ClanResourceEnum.values()) {
			resources.put(res, 0);
		}
	}
	
	public Map<ClanResourceEnum, Integer> getCurrentResources() {
		return resources;
	}
	
	public void setResource(ClanResourceEnum resource, int cost) {
		this.resources.put(resource, cost);
	}
	
	public void setMoney(double money) {
		this.money = money;
	}
	
	public void setClanPoints(int points) {
		this.clanPoints = points;
	}

	/**
	 * Diese Methode zieht dem Clan die Kosten ab.
	 * Die UpgradeKosten werden dabei nicht gelöscht und der
	 * Clan bekommt auch keine Upgrade levelUPs.
	 * @param clan
	 * @return ob der Clan die recourcen abgezogen bekommen hat.
	 */
	public boolean payCosts() {
		if(!clan.getBank().removeMoney(this.money)) return false;
		if(clan.getClanPoints() - this.clanPoints < 0) return false;
		clan.removeClanPoints(this.clanPoints);
		this.resources.forEach((rec, val)->{
			clan.getClanResources().removeRecource(rec, val);
		});
		return true;
	}
	
	public boolean isAffordable() {
		
		for (ClanResourceEnum res : ClanResourceEnum.values()) {
			if (!clan.getClanResources().has(res, resources.get(res))) {
				return false;
			}
		}
		if (clan.getClanPoints() < this.clanPoints || clan.getBank().getMoney() < this.money) {
			return false;
		}
		
		return true;
	}
	
	public ArrayList<String> getCostLore() {
		ArrayList<String> list = Lists.newArrayList();
		String c1 = (clan.getBank().getMoney() >= this.money) ? "§a" : "§c";
		String c2 = (clan.getClanPoints() >= this.clanPoints) ? "§a" : "§c";
		list.add("§eGeld: " + c1 + clan.getBank().getMoney() + "/" + this.money);
		list.add("§eClan Punkte: " + c2 + clan.getClanPoints() + "/" + this.clanPoints);
		list.add("");
		this.resources.forEach((rec, val) -> {
			String color = clan.getClanResources().has(rec, val) ? "§a" : "§c";
			int current = clan.getClanResources().getCurrentResourceAmount(rec);
			
			if (val > 0) {
			list.add("§e" + rec.getDisplayName() + ": " + color + current + "/" + val);
			}
		});
		return list;
	}
}
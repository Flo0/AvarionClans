package com.avarioncraft.Clans.data;

import java.util.LinkedHashMap;
import java.util.Map;

import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.Armory;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.bank.Bank;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.barracks.Barracks;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.flag.Flag;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.gather.GatherJobs;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.ProducerJobs;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.resource.ClanResources;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.temple.Temple;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.trader.Trader;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.Warehouse;
import com.avarioncraft.Clans.util.enums.UpgradeType;
import com.google.common.collect.Maps;

public class Upgrades{

	private LinkedHashMap<UpgradeType, ClanUpgrade> upgrades = Maps.newLinkedHashMapWithExpectedSize(9);
	
	public Upgrades(Clan clan) {
		upgrades.put(UpgradeType.ARMORY, new Armory(clan, UpgradeType.ARMORY, 0));
		upgrades.put(UpgradeType.BANK, new Bank(clan, UpgradeType.BANK, 0));
		upgrades.put(UpgradeType.BARRACKS, new Barracks(clan, UpgradeType.BARRACKS, 0));
		upgrades.put(UpgradeType.FLAG, new Flag(clan, UpgradeType.FLAG, 0));
		upgrades.put(UpgradeType.GATHERER_JOBS, new GatherJobs(clan, UpgradeType.GATHERER_JOBS, 0));
		upgrades.put(UpgradeType.PRODUCER_JOBS, new ProducerJobs(clan, UpgradeType.PRODUCER_JOBS, 0));
		upgrades.put(UpgradeType.CLAN_RECOURCES, new ClanResources(clan, UpgradeType.CLAN_RECOURCES, 0));
		upgrades.put(UpgradeType.TEMPLE, new Temple(clan, UpgradeType.TEMPLE, 0));
		upgrades.put(UpgradeType.TRADING_COMPLEX, new Trader(clan, UpgradeType.TRADING_COMPLEX, 0));
		upgrades.put(UpgradeType.CLAN_WAREHOUSE, new Warehouse(clan, UpgradeType.CLAN_WAREHOUSE, 0));
	}

	public Map<UpgradeType, ClanUpgrade> getUpgrades() {
		return this.upgrades;
	}

	public Armory getArmory() {
		return (Armory) upgrades.get(UpgradeType.ARMORY);
	}

	public Bank getBank() {
		return (Bank) upgrades.get(UpgradeType.BANK);
	}

	public Barracks getBarracks() {
		return (Barracks) upgrades.get(UpgradeType.BARRACKS);
	}

	public Flag getFlag() {
		return (Flag) upgrades.get(UpgradeType.FLAG);
	}

	public GatherJobs getGatherJobs() {
		return (GatherJobs) upgrades.get(UpgradeType.GATHERER_JOBS);
	}

	public ProducerJobs getProducerJobs() {
		return (ProducerJobs) upgrades.get(UpgradeType.PRODUCER_JOBS);
	}

	public ClanResources getClanResources() {
		return (ClanResources) upgrades.get(UpgradeType.CLAN_RECOURCES);
	}

	public Temple getTemple() {
		return (Temple) upgrades.get(UpgradeType.TEMPLE);
	}

	public Trader getTrader() {
		return (Trader) upgrades.get(UpgradeType.TRADING_COMPLEX);
	}

	public Warehouse getWarehouse() {
		return (Warehouse) upgrades.get(UpgradeType.CLAN_WAREHOUSE);
	}

}
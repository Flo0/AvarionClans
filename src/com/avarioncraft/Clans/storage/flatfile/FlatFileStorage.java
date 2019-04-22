package com.avarioncraft.Clans.storage.flatfile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.events.ClanCreateEvent;
import com.google.common.collect.Maps;

public class FlatFileStorage implements Listener {
	
	private AvarionClans plugin;
	
	private HashMap<Integer, YamlConfiguration> clandata = Maps.newHashMap();
	
	private File clanFolder;
	
	public FlatFileStorage(AvarionClans instance) {
		this.plugin = instance;
		Bukkit.getPluginManager().registerEvents(this, instance);
		this.init();
	}
	
	private void init() {
		clanFolder = new File(plugin.getDataFolder(), "clandata");
		if (!clanFolder.exists()) clanFolder.mkdirs();
		
		this.loadClans();
	}
	
	public void saveClan(Clan clan) {
		if (!clandata.containsKey(clan.getIdentifier())) {
			Bukkit.getLogger().severe("Failed to save clan - No configuration file found!");
			return;
		}

		YamlConfiguration cfg = clandata.get(clan.getIdentifier());

		cfg.set("data.clan", clan);

		for (ClanUpgrade upgrade : clan.getUpgrades().getUpgrades().values()) {
			upgrade.saveTo(cfg);
			System.out.println("Speichere: " + upgrade.getDisplayname());
		}
		File clanSubFolder = new File(this.clanFolder, String.valueOf(clan.getIdentifier()));
		File clanfile = new File(clanSubFolder, "clandata.yml");
		try {
			clandata.get(clan.getIdentifier()).save(clanfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadClans() {

		File[] sub = this.clanFolder.listFiles();

		for (File dir : sub) {
			if (!dir.isDirectory())
				continue;

			File file = new File(dir, "clandata.yml");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			Clan clan = (Clan) cfg.get("data.clan");

			if (clan == null) {
				AvarionClans.getPlugin().getLogger().severe("Failed to load Clan " + file.getName() + " - Invalid configuration..skipping");
				continue;
			}

			for (ClanUpgrade upgrade : clan.getUpgrades().getUpgrades().values()) {
				upgrade.loadFrom(cfg);
			}
			this.clandata.put(clan.getIdentifier(), cfg);

		}
	}
		
	@EventHandler
	public void createClanFile(ClanCreateEvent event) {
		AvarionClans.getPlugin().getLogger().info("Creating new clan files...");

		File clanSubFolder = new File(this.clanFolder, String.valueOf(event.getClan().getIdentifier()));
		if (!clanSubFolder.exists()) {
			clanSubFolder.mkdir();
		}

		File clanfile = new File(clanSubFolder, "clandata.yml");
		try {
			clanfile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(clanfile);
		
		Clan.idClanMap.put(event.getClan().getIdentifier(), event.getClan());
		
		this.clandata.put(event.getClan().getIdentifier(), cfg);

	}
	
}
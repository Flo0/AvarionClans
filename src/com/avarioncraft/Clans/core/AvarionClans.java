package com.avarioncraft.Clans.core;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.avarioncraft.Clans.administration.FrameCommands;
import com.avarioncraft.Clans.administration.framebuilder.RawFrame;
import com.avarioncraft.Clans.commands.AdminCommands;
import com.avarioncraft.Clans.commands.UserCommands;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.Rank;
import com.avarioncraft.Clans.data.RankContainer;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.access.WarehouseAccesspoint;
import com.avarioncraft.Clans.data.message.manager.MessageCommandEvent;
import com.avarioncraft.Clans.listener.InventoryEvents;
import com.avarioncraft.Clans.listener.JoinEvents;
import com.avarioncraft.Clans.listener.LeaveEvents;
import com.avarioncraft.Clans.listener.WarehouseListener;
import com.avarioncraft.Clans.manager.DataManager;
import com.avarioncraft.Clans.storage.flatfile.FlatFileStorage;
import com.avarioncraft.Clans.threads.SyncTickableThread;
import com.avarioncraft.Clans.util.debug.DebugCommands;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import lombok.Getter;
import net.crytec.api.devin.commands.CommandRegistrar;
import net.milkbowl.vault.economy.Economy;

public class AvarionClans extends JavaPlugin {
	
	
	static {
		ConfigurationSerialization.registerClass(RawFrame.class, "RawFrame");
		ConfigurationSerialization.registerClass(Clan.class, "Clan");
		ConfigurationSerialization.registerClass(Rank.class, "Rank");
		ConfigurationSerialization.registerClass(RankContainer.class, "RankContainer");
	}

	// -------------------------- VARIABLEN -------------------------- //

	@Getter
	private ProtocolManager protocolManager;
	@Getter
	public static AvarionClans plugin;
	public static Economy economy;
	@SuppressWarnings("unused")
	private LeaveEvents leaveEvents;
	@SuppressWarnings("unused")
	private JoinEvents joinEvents;
	@SuppressWarnings("unused")
	private WarehouseListener warehouseEvents;
	@Getter
	private CommandRegistrar commandRegistrar;
	
	@Getter
	private FlatFileStorage storage;
	

	// -------------------------- ON LOAD -------------------------- //

	@Override
	public void onLoad() {

		AvarionClans.plugin = this;

	}

	// -------------------------- ON ENABLE -------------------------- //

	@Override
	public void onEnable() {
		
		// Economy
		getServer().getConsoleSender().sendMessage("[AvarionClans] Setup Economy...");
		if (!setupEconomy()) {
			System.out.println("Econ!");
		}
		
		// Instances
		getServer().getConsoleSender().sendMessage("[AvarionClans] Initialisiere Instanzen...");
		this.commandRegistrar = new CommandRegistrar(this);
		this.registerCommands();
		
		this.storage = new FlatFileStorage(this);
		
		DataManager.get();
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		
		Clan.clanCounter = getConfig().getInt("lastID", 0);

		// Methods
		
		getServer().getConsoleSender().sendMessage("[AvarionClans] Lade Dateien...");
		DataManager.get().loadRawFrames();
		DataManager.get().loadClanSchematics();
		WarehouseAccesspoint.loadPoints();

		// Events
		getServer().getConsoleSender().sendMessage("[AvarionClans] Registriere Events...");
		this.leaveEvents = new LeaveEvents();
		this.joinEvents = new JoinEvents();
		this.warehouseEvents = new WarehouseListener();
		Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new MessageCommandEvent(), this);

		// Commands
		getServer().getConsoleSender().sendMessage("[AvarionClans] Registriere Commands...");
		this.commandRegistrar.registerCommands(new DebugCommands());
		this.commandRegistrar.registerCommands(new FrameCommands());
		
		// Threads
		Bukkit.getScheduler().runTaskTimer(this, new SyncTickableThread(), 1, 1);
		
		// Messages

		getServer().getConsoleSender().sendMessage("[AvarionClans] Plugin geladen.");

		// Debug

	}

	// -------------------------- ON DISABLE -------------------------- //

	@Override
	public void onDisable() {
		
		System.out.println("Speichere " + Clan.idClanMap.size() + " Clans");
		
		for (Clan clan : Clan.idClanMap.values()) {
			this.storage.saveClan(clan);
			System.out.println("Clan Nr" + clan.getIdentifier());
		}
		
		getConfig().set("lastID", Clan.clanCounter);
		this.saveConfig();
		
		getServer().getConsoleSender().sendMessage("[AvarionClans] Speichere Dateien...");
		
//		for (Clan clan : ClanManager.get().getAllClans()) {
//			ClanSQL.get().saveClanSettings(clan);
//		}
		
		try {
//			DataManager.get().saveGlobalVariables();
			DataManager.get().saveRawFrames();
			DataManager.get().saveClanSchematics();
			WarehouseAccesspoint.savePoints();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ClanSQL.get().getPool().closePool();
		getServer().getConsoleSender().sendMessage("[AvarionClans] Plugin entladen.");
		
	}

	// -------------------------- METHODEN -------------------------- //

	// ConfigManager laden

	// Economy
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	
	private void registerCommands() {
		// Commands registrieren
		this.commandRegistrar.registerCommands(new UserCommands());
		this.commandRegistrar.registerCommands(new AdminCommands());
		this.commandRegistrar.registerHelpCommands();
	}

}

package com.avarioncraft.Clans.manager;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import com.avarioncraft.Clans.administration.framebuilder.RawFrame;
import com.avarioncraft.Clans.administration.framebuilder.handling.FrameMemory;
import com.avarioncraft.Clans.administration.schematic.SchematicMemory;
import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.util.FlatFileSupervisor;

public class DataManager extends FlatFileSupervisor {

	private static DataManager instance = null;
	public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	/**
	 * Constructor für den DataManager. Hier werden neue default Ordner
	 * hinzugefügt. super.addDefaultFolder(File);
	 */
	protected DataManager() {
		this.plugin = AvarionClans.getPlugin();
		this.addDefaultFolder(new File(plugin.getDataFolder() + File.separator));
		this.setupData();
	}

	public static DataManager get() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}

	private AvarionClans plugin;
	private final String extension = ".yml";

	/**
	 * Gibt an, ob ein Spieler ein existierendes FlatFile für seine PlayerClanInfo
	 * hat.
	 * 
	 * @param player der Spieler
	 * @return if player has a PlayerInfoFile
	 */

	public void saveRawFrames() throws IOException {
		File frameFile = new File(this.plugin.getDataFolder() + File.separator + "RawFrames" + this.extension);
		FileConfiguration config = YamlConfiguration.loadConfiguration(frameFile);
		FrameMemory.get().RawFrames.forEach((INT, RAW) -> {
			config.set(INT.toString(), RAW);
		});
		config.save(frameFile);
	}

	public void loadRawFrames() {
		File frameFile = new File(this.plugin.getDataFolder() + File.separator + "RawFrames" + this.extension);
		FileConfiguration config = YamlConfiguration.loadConfiguration(frameFile);
		config.getKeys(false).forEach((S) -> {
			RawFrame frame = config.getSerializable(S, RawFrame.class);
			int n = Integer.valueOf(S);
			FrameMemory.get().RawFrames.put(n, frame);
		});
		System.out.println("Done loading " + FrameMemory.get().RawFrames.size() + " Frames.");
	}

	private String vecSerialize(Vector vec) {
		String back = "";
		back += "" + vec.getX() + "%";
		back += "" + vec.getY() + "%";
		back += "" + vec.getZ();
		return back.replace(".", "?");
	}

	private Vector vecDeserialize(String string) {
		String[] tiles = string.replace("?", ".").split("%");
		return new Vector(Double.parseDouble(tiles[0]), Double.parseDouble(tiles[1]), Double.parseDouble(tiles[2]));
	}

	public void saveClanSchematics() throws IOException {
		File file = new File(plugin.getDataFolder() + File.separator + "ClanSchematics" + this.extension);
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		SchematicMemory.schematics.forEach((id, map) -> {

			map.forEach((vec, state) -> {

				config.set("Schematics." + id + "." + "" + vecSerialize(vec), state.getAsString());

			});

		});

		config.save(file);

	}

	public void loadClanSchematics() {
		File file = new File(plugin.getDataFolder() + File.separator + "ClanSchematics" + this.extension);
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getConfigurationSection("Schematics") == null)
			return;
		config.getConfigurationSection("Schematics").getKeys(false).forEach((id) -> {
			config.getConfigurationSection("Schematics." + id).getKeys(false).forEach((vec) -> {
				SchematicMemory.addLoadData(Integer.parseInt(id), vecDeserialize(vec),
						Bukkit.createBlockData(config.getString("Schematics." + id + "." + vec)));
			});
		});
	}
}

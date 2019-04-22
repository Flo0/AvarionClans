package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.access;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.avarioncraft.Clans.ambient.animations.WarehouseAnim;
import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.threads.SyncTickAction;
import com.avarioncraft.Clans.threads.memory.SyncMemory;
import com.avarioncraft.Clans.util.interfaces.SyncTickable;
import com.google.common.collect.Maps;

import lombok.Getter;
import net.crytec.api.util.UtilLoc;

public class WarehouseAccesspoint implements SyncTickable{
	
	public WarehouseAccesspoint(Location loc, Clan clan) {
		this.location = loc;
		this.clan = clan;
		points.put(this.location, this);
		SyncMemory.SyncTickables.add(this);
	}
	
	public static void savePoints() {
		File file = new File(AvarionClans.getPlugin().getDataFolder() + File.separator + "WarehousePoints.yml");
		if(file.exists()) {
			file.delete();
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		points.values().forEach(wh->{
			config.set(UtilLoc.locToString(wh.location), wh.clan.getIdentifier());
		});
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void loadPoints() {
		File file = new File(AvarionClans.getPlugin().getDataFolder() + File.separator + "WarehousePoints.yml");
		if(!file.exists()) return;
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.getKeys(false).forEach(key->{
			new WarehouseAccesspoint(UtilLoc.StringToLoc(key), Clan.ofID(config.getInt(key)).get());
		});
	}
	private static Map<Location, WarehouseAccesspoint> points = Maps.newHashMap();
	public static WarehouseAccesspoint ofLoc(Location loc) {
		return points.get(loc);
	}
	public static boolean isWarehouse(Location loc) {
		return points.containsKey(loc);
	}
	
	private WarehouseAnim anim = new WarehouseAnim();
	@Getter
	private final Clan clan;
	@Getter
	private final Location location;
	
	public void onRemove() {
		points.remove(this.location);
		SyncMemory.SyncTickables.remove(this);
	}
	
	@Override
	public SyncTickAction getTickAction() {
		
		return new SyncTickAction() {
			@Override
			public void action() {
				anim.playIdleAnim(location, clan, ThreadLocalRandom.current().nextFloat() > 0.6);
			}
		};
	}
	
	@Override
	public int tickDelay() {
		return 10;
	}


}

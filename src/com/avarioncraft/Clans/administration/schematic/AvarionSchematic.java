package com.avarioncraft.Clans.administration.schematic;

import java.util.ArrayDeque;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.avarioncraft.Clans.core.AvarionClans;
import com.google.common.collect.Maps;

public class AvarionSchematic {
	
	public AvarionSchematic(Player player, int schematicID) {
		double X = player.getChunk().getX() * 16D;
		double Y = player.getLocation().getY();
		double Z = player.getChunk().getZ() * 16D;
		this.offset = new Location (player.getWorld(), X, Y, Z);
		this.schematic = SchematicMemory.get(schematicID);
	}
	
	public AvarionSchematic(Location location, int schematicID) {
		double X = location.getChunk().getX() * 16D;
		double Y = location.getY();
		double Z = location.getChunk().getZ() * 16D;
		this.offset = new Location (location.getWorld(), X, Y, Z);
		this.schematic = SchematicMemory.get(schematicID);
	}
	
	private Map<Vector, BlockData> schematic = Maps.newHashMap();
	private Location offset;
	
	private ArrayDeque<BuildData> getQueue(){
		ArrayDeque<BuildData> que = new ArrayDeque<BuildData>();
		this.schematic.forEach((vec, state)->{que.add(new BuildData(offset.clone().add(vec), state));});
		return que;
	}
	
	public void build() {
		Bukkit.getScheduler().runTask(AvarionClans.getPlugin(), ()->{
			ArrayDeque<BuildData> que = this.getQueue();
			while(!que.isEmpty()) {
				que.pop().place();
			}
		});
	}
}

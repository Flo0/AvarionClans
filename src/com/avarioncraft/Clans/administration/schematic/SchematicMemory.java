package com.avarioncraft.Clans.administration.schematic;

import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

import com.avarioncraft.Clans.administration.framebuilder.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class SchematicMemory {
	
	public static void add(int ID) {
		schematics.put(ID, buildData());
	}
	
	public static void addLoadData(int ID, Vector vec, BlockData data) {
		if(schematics.containsKey(ID)) {
			schematics.get(ID).put(vec, data);
		}else {
			schematics.put(ID, Maps.newHashMap());
			schematics.get(ID).put(vec, data);
		}
	}
	
	public static Map<Integer, Map<Vector, BlockData>> schematics = Maps.newHashMap();
	
	public static Map<Vector, BlockData> get(int ID){
		return schematics.get(ID);
	}
	
	private static Map<Vector, BlockData> buildData(){
		Builder builder = new Builder();
		Map<Vector, BlockData> map = Maps.newHashMap();
		Chunk A = Bukkit.getWorld("Creation").getChunkAt(2, 2);
		Chunk B = Bukkit.getWorld("Creation").getChunkAt(-2, -2);
		Set<Chunk> chunks = builder.getChunkSet(A, B);
		Set<Block> blocks = Sets.newHashSet();
		for(Chunk ch : chunks) {
			for(int x = 0; x <= 15; x++) {
				for(int z = 0; z <= 15; z++) {
					for(int y = 4; y <= 255; y++) {
						Block block = ch.getBlock(x, y, z);
						if(block.getType() != Material.AIR) {
							blocks.add(block);
						}
					}
				}
			}
		}
		blocks.forEach((bl)->{
			Vector vec = bl.getLocation().toVector().add(new Vector(0.0, -4.0, 0.0));
			map.put(vec, bl.getBlockData());
		});
		return map;
	}
}

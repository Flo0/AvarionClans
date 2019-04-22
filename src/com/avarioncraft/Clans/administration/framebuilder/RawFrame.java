package com.avarioncraft.Clans.administration.framebuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.avarioncraft.Clans.administration.framebuilder.data.ChunkBuildInfo;
import com.avarioncraft.Clans.administration.framebuilder.data.ChunkLoc;
import com.avarioncraft.Clans.administration.framebuilder.data.CoordPair;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;

@SerializableAs("RawFrame")
public class RawFrame implements ConfigurationSerializable{
	
	public RawFrame() {
		this.builder = new Builder();
		Chunk A = Bukkit.getWorld("Creation").getChunkAt(2, 2);
		Chunk B = Bukkit.getWorld("Creation").getChunkAt(-2, -2);
		this.chunks = builder.getChunkSet(A, B);
		this.blockSet = this.getClanBuildingSet();
		this.blockPack = this.getClanBuildingMap();
	}

	private Builder builder;
	@Getter
	private Set<Chunk> chunks = Sets.newHashSet();
	@Getter
	private Set<Block> blockSet = Sets.newHashSet();
	@Getter
	private Map<CoordPair, ChunkBuildInfo> blockPack = Maps.newHashMap();

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newHashMap();
		this.blockPack.forEach((CP, CBI) -> {
			map.put(CP.getID(), CBI.getFormat());
		});
		return map;
	}
	
	public RawFrame(Map<String, Object> map) {
		map.forEach((ID, O) -> {
			if (!ID.contains("==")) {
				this.blockPack.put(new CoordPair(ID), new ChunkBuildInfo((String) O));
			}
		});
	}
	
	private Map<CoordPair, ChunkBuildInfo> getClanBuildingMap() {
		Map<CoordPair, ChunkBuildInfo> info = Maps.newHashMap();
		Map<CoordPair, Map<ChunkLoc, Material>> blocks = Maps.newHashMap();
		for (Chunk ch : chunks) {
			CoordPair co = new CoordPair(ch.getX(), ch.getZ());
			for (int x = 0; x <= 15; x++) {
				for (int z = 0; z <= 15; z++) {
					for (int y = 4; y <= 255; y++) {
						Block block = ch.getBlock(x, y, z);
						if (block.getType() != Material.AIR) {
							if (blocks.containsKey(co)) {
								blocks.get(co).put(new ChunkLoc(x, y, z), block.getType());
							} else {
								blocks.put(co, new HashMap<ChunkLoc, Material>());
								blocks.get(co).put(new ChunkLoc(x, y, z), block.getType());
							}
						}
					}
				}
			}
		}
		blocks.forEach((CP, MAP) -> {
			info.put(CP, new ChunkBuildInfo(MAP));
		});
		return info;

	}
	
	private Set<Block> getClanBuildingSet() {
		Set<Block> blocks = Sets.newHashSet();
		for (Chunk ch : chunks) {
			for (int x = 0; x <= 15; x++) {
				for (int z = 0; z <= 15; z++) {
					for (int y = 4; y <= 255; y++) {
						Block block = ch.getBlock(x, y, z);
						if (block.getType() != Material.AIR) {
							blocks.add(block);
						}
					}
				}
			}
		}

		return blocks;
	}
}

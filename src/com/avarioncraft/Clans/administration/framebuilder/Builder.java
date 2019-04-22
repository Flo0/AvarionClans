package com.avarioncraft.Clans.administration.framebuilder;

import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;

import com.google.common.collect.Sets;

public class Builder {

	public Set<Chunk> getChunkSet(Chunk A, Chunk B) {
		Set<Chunk> set = Sets.newHashSet();

		int Xlow = Math.min(A.getX(), B.getX());
		int Xhigh = Math.max(A.getX(), B.getX());
		int Zlow = Math.min(A.getZ(), B.getZ());
		int Zhigh = Math.max(A.getZ(), B.getZ());

		for (int x = Xlow; x <= Xhigh; x++) {
			for (int z = Zlow; z <= Zhigh; z++) {
				set.add(A.getWorld().getChunkAt(x, z));
			}
		}
		return set;
	}

	public Set<ChunkSnapshot> getChunkSnapSet(Chunk A, Chunk B) {
		Set<ChunkSnapshot> set = Sets.newHashSet();

		int Xlow = Math.min(A.getX(), B.getX());
		int Xhigh = Math.max(A.getX(), B.getX());
		int Zlow = Math.min(A.getZ(), B.getZ());
		int Zhigh = Math.max(A.getZ(), B.getZ());

		for (int x = Xlow; x <= Xhigh; x++) {
			for (int z = Zlow; z <= Zhigh; z++) {
				set.add(A.getWorld().getChunkAt(x, z).getChunkSnapshot());
			}
		}
		return set;
	}

}

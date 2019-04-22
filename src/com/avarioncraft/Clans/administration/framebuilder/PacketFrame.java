package com.avarioncraft.Clans.administration.framebuilder;

import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.administration.framebuilder.data.ChunkBuildInfo;
import com.avarioncraft.Clans.administration.framebuilder.data.ChunkLoc;
import com.avarioncraft.Clans.administration.framebuilder.data.CoordPair;
import com.avarioncraft.Clans.util.packetwrapper.PacketMultiBlockChange;
import com.comphenix.protocol.wrappers.ChunkCoordIntPair;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.collect.Sets;

public class PacketFrame {

	public PacketFrame(RawFrame raw, Player player) {
		this.addMutliPacket(raw, player, false);
		this.addMutliPacket(raw, player, true);
	}

	private Set<PacketMultiBlockChange> frame = Sets.newHashSet();

	private Set<PacketMultiBlockChange> emptyframe = Sets.newHashSet();

	public void sendEmpty() {
		for (PacketMultiBlockChange Pack : this.emptyframe) {
			Pack.broadcastPacket();
		}
	}

	public void sendPlayerEmpty(Player player) {
		for (PacketMultiBlockChange Pack : this.emptyframe) {
			Pack.sendPacket(player);
		}
	}

	public void send() {
		for (PacketMultiBlockChange Pack : this.frame) {
			Pack.broadcastPacket();
		}
	}

	public void sendPlayer(Player player) {
		for (PacketMultiBlockChange Pack : this.frame) {
			Pack.sendPacket(player);
		}
	}

	private void addMutliPacket(RawFrame raw, Player player, boolean empty) {
		PacketMultiBlockChange pack;
		Set<MultiBlockChangeInfo> changes;
		int PX = player.getChunk().getX();
		int PZ = player.getChunk().getZ();
		World world = player.getWorld();
		int offset = (int) player.getLocation().getY() - 4;

		for (Map.Entry<CoordPair, ChunkBuildInfo> entry : raw.getBlockPack().entrySet()) {
			pack = new PacketMultiBlockChange();
			changes = Sets.newHashSet();

			int cLocX = entry.getKey().X + PX;
			int cLocZ = entry.getKey().Z + PZ;

			Material fill;

			pack.setChunk(new ChunkCoordIntPair(cLocX, cLocZ));

			for (Map.Entry<ChunkLoc, Material> locations : entry.getValue().buildInfo.entrySet()) {
				fill = Material.AIR;
				int wLocX = locations.getKey().X + (cLocX * 16);
				int wLocY = locations.getKey().Y + offset;
				int wLocZ = locations.getKey().Z + (cLocZ * 16);
				if (!empty)
					fill = locations.getValue();
				changes.add(new MultiBlockChangeInfo(new Location(world, wLocX, wLocY, wLocZ), WrappedBlockData.createData(fill)));
			}
			pack.setRecords(changes.stream().toArray(MultiBlockChangeInfo[]::new));
			if (empty) {
				this.emptyframe.add(pack);
			} else {
				this.frame.add(pack);
			}
		}

	}

}

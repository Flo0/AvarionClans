package com.avarioncraft.Clans.administration.schematic;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class BuildData {

	public BuildData(Location loc, BlockData data) {
		this.loc = loc;
		this.data = data;
	}

	public final Location loc;
	public final BlockData data;

	public void place() {
		loc.getBlock().setBlockData(data);
	}

}

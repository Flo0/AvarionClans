package com.avarioncraft.Clans.administration.framebuilder.data;

import java.util.Map;

import org.bukkit.Material;

import com.google.common.collect.Maps;

public class ChunkBuildInfo {

	private static final String Asplit = "¥";
	private static final String Bsplit = "Ü";

	public ChunkBuildInfo(Map<ChunkLoc, Material> map) {
		this.buildInfo = map;
	}

	public Map<ChunkLoc, Material> buildInfo = Maps.newHashMap();

	public String getFormat() {
		String SP = "";
		for (Map.Entry<ChunkLoc, Material> entry : this.buildInfo.entrySet()) {
			SP += entry.getKey().getID() + Asplit + entry.getValue().toString() + Bsplit;
		}
		return SP;
	}

	public ChunkBuildInfo(String Format) {
		String[] entry = Format.split(Bsplit);
		for (String st : entry) {
			String[] flow = st.split(Asplit);
			this.buildInfo.put(new ChunkLoc(flow[0]), Material.valueOf(flow[1]));
		}
	}

}
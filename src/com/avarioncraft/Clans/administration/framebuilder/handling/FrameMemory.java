package com.avarioncraft.Clans.administration.framebuilder.handling;

import java.util.Map;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.administration.framebuilder.PacketFrame;
import com.avarioncraft.Clans.administration.framebuilder.RawFrame;
import com.google.common.collect.Maps;

public class FrameMemory {

	private static FrameMemory instance = null;

	protected FrameMemory() {
	}

	public static FrameMemory get() {
		if (instance == null) {
			instance = new FrameMemory();
		}
		return instance;
	}

	public Map<Integer, RawFrame> RawFrames = Maps.newHashMap();
	public Map<Player, PacketFrame> PacketFrames = Maps.newHashMap();

}

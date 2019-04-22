package com.avarioncraft.Clans.util.enums;

import com.sk89q.worldedit.math.Vector3;

import lombok.Getter;

public enum ClanDirection {

	NORTH(Vector3.at(0, 0, -1)),
	EAST(Vector3.at(1, 0, 0)),
	SOUTH(Vector3.at(0, 0, 1)),
	WEST(Vector3.at(-1, 0, 0));
	
	@Getter
	private Vector3 vector;
	
	private ClanDirection(Vector3 vector) {
		this.vector = vector;
	}
	
}

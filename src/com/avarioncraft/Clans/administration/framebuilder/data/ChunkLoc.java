package com.avarioncraft.Clans.administration.framebuilder.data;

public class ChunkLoc {

	private static final String Asplit = "µ";

	public ChunkLoc(int X, int Y, int Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}

	public int X;
	public int Y;
	public int Z;

	public String getID() {
		return this.X + Asplit + this.Y + Asplit + this.Z;
	}

	public ChunkLoc(String ID) {
		String[] coord = ID.split(Asplit);
		this.X = Integer.valueOf(coord[0]);
		this.Y = Integer.valueOf(coord[1]);
		this.Z = Integer.valueOf(coord[2]);
	}

}

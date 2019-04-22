package com.avarioncraft.Clans.administration.framebuilder.data;

public class CoordPair {

	private static final String Asplit = "£";

	public CoordPair(int X, int Z) {
		this.X = X;
		this.Z = Z;
	}

	public int X;
	public int Z;

	public String getID() {
		return this.X + Asplit + this.Z;
	}

	public CoordPair(String ID) {
		String[] coords = ID.split(Asplit);
		this.X = Integer.valueOf(coords[0]);
		this.Z = Integer.valueOf(coords[1]);
	}

}

package com.avarioncraft.Clans.util;

public class MathUtil {
	
	public static String ProgressBar(double current, double max, int length, String fragment) {
		String bar = "";
		String full = ("§a" + fragment);
		String empty = ("§c" + fragment);
		double percent = (100 / max) * current;
		int fullfrags = (int) Math.round(percent / (100 / length));
		int emptyfrags = length - fullfrags;
		
		while(fullfrags > 0) {
			bar += full;
			fullfrags--;
		}
		while(emptyfrags > 0) {
			bar += empty;
			emptyfrags--;
		}

		return bar;
	}

	public static double roundAvoid(double value, int places) {
	    double scale = Math.pow(10, places);
	    return Math.round(value * scale) / scale;
	}
	
}

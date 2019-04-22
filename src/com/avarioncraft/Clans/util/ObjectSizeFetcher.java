package com.avarioncraft.Clans.util;

import java.lang.instrument.Instrumentation;

public class ObjectSizeFetcher {
	
	private static Instrumentation instrumentation;
	
	public static void premain(String name, Instrumentation inst) {
		instrumentation = inst;
	}
	
	public static long getObjectSizeB(Object obj) {
		return instrumentation.getObjectSize(obj);
	}
	
	public static double getObjectSizeKB(Object obj) {
		return (getObjectSizeB(obj) / 1024D);
	}
	
	public static double getObjectSizeMB(Object obj) {
		return (getObjectSizeKB(obj) / 1024D);
	}
	
	public static double getObjectSizeGB(Object obj) {
		return (getObjectSizeMB(obj) / 1024D);
	}
	
}

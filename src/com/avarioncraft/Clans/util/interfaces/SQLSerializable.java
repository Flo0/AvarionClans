package com.avarioncraft.Clans.util.interfaces;

public interface SQLSerializable {
	
	public String deserialize();
	public void serialize(String sqlString);
	
}
